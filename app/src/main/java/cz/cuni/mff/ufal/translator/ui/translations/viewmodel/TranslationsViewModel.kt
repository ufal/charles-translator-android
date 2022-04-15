package cz.cuni.mff.ufal.translator.ui.translations.viewmodel

import android.app.Application
import android.speech.SpeechRecognizer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.extensions.logE
import cz.cuni.mff.ufal.translator.interactors.ContextUtils
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateCyrilToLatin
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateLatinToCyril
import cz.cuni.mff.ufal.translator.interactors.api.IApi
import cz.cuni.mff.ufal.translator.interactors.api.UnsupportedApiException
import cz.cuni.mff.ufal.translator.interactors.db.IDb
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.interactors.tts.ITextToSpeechWrapper
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.translations.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */
@HiltViewModel
class TranslationsViewModel @Inject constructor(
    context: Application,
    private val api: IApi,
    private val db: IDb,
    private val userDataStore: IUserDataStore,
    private val textToSpeech: ITextToSpeechWrapper
) : ITranslationsViewModel, AndroidViewModel(context) {

    private var apiJob: Job? = null
    private var textCheckerJob: Job? = null
    private var historyTimerJob: Job? = null

    private var lastInputText = ""

    override val inputTextData = MutableStateFlow(InputTextData())
    override val outputTextData = MutableStateFlow(OutputTextData())
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val state = MutableStateFlow<TranslationsScreenState>(TranslationsScreenState.Idle)
    override val hasFinishedOnboarding = userDataStore.hasFinishedOnboarding().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        true,
    )
    override val textToSpeechErrors = textToSpeech.errors
    override val isSpeechRecognizerAvailable: Boolean
        get() = SpeechRecognizer.isRecognitionAvailable(getApplication())

    override fun onStart() {
        super.onStart()

        viewModelScope.launch {
            textToSpeech.init()
        }

        startTextCheckerTimer()
    }

    override fun onStop() {
        super.onStop()

        apiJob?.cancel()
        apiJob = null

        historyTimerJob?.cancel()
        historyTimerJob = null

        textCheckerJob?.cancel()
        textCheckerJob = null

        textToSpeech.stop()
    }

    override fun onCleared() {
        super.onCleared()

        textToSpeech.shutdown()
    }

    override fun setInputText(data: InputTextData) {
        if (data.text == inputTextData.value.text) { //to prevent overwrite source
            return
        }

        inputTextData.value = data
        startSaveTimer()
    }

    override fun swapLanguages() {
        val tmpInputLanguage = inputLanguage.value
        inputLanguage.value = outputLanguage.value
        outputLanguage.value = tmpInputLanguage

        val newMainText = inputTextData.value.text
        inputTextData.value = InputTextData(text = outputTextData.value.mainText, TextSource.SwapLanguages)
        outputTextData.value = OutputTextData(mainText = newMainText, secondaryText = "")

        translate()
    }

    override fun copyToClipBoard(text: String) {
        val label = getApplication<Application>().resources.getString(R.string.copy_to_clipboard_label)
        ContextUtils.copyToClipBoard(getApplication(), label, text)
    }

    override fun pasteFromClipBoard() {
        val text = ContextUtils.pasteFromClipBoard(getApplication())
        setInputText(InputTextData(text, TextSource.Clipboard))
    }

    override fun setFromHistoryItem(item: HistoryItem) {
        inputLanguage.value = item.inputLanguage
        outputLanguage.value = item.outputLanguage
        setInputText(InputTextData(item.outputText, TextSource.History))
    }

    override fun retry() {
        translate()
    }

    override fun setFinishedOnboarding(agreeWithDataCollection: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.setFinishedOnboarding()
            userDataStore.saveAgreementDataCollection(agreeWithDataCollection)
        }
    }

    override fun textToSpeech() {
        viewModelScope.launch {
            textToSpeech.speak(
                language = outputLanguage.value,
                text = outputTextData.value.mainText
            )
        }
    }

    private fun translate() {
        if (inputTextData.value.text.isBlank()) {
            apiJob?.cancel()
            outputTextData.value = OutputTextData()
            lastInputText = ""
            state.value = TranslationsScreenState.Idle
            return
        }

        apiJob?.cancel()
        apiJob = viewModelScope.launch {
            lastInputText = inputTextData.value.text
            state.value = TranslationsScreenState.Loading

            api.translate(
                inputLanguage = inputLanguage.value,
                outputLanguage = outputLanguage.value,
                text = inputTextData.value.text.trim(),
                textSource = inputTextData.value.source,
            ).onSuccess {
                outputTextData.value = when (outputLanguage.value) {
                    Language.Czech -> {
                        OutputTextData(
                            mainText = it,
                            secondaryText = transliterateLatinToCyril(it)
                        )
                    }
                    Language.Ukrainian -> {
                        OutputTextData(
                            mainText = it,
                            secondaryText = transliterateCyrilToLatin(it)
                        )
                    }
                }
                state.value = TranslationsScreenState.Success
            }.onFailure {
                if (it is UnsupportedApiException) {
                    state.value = TranslationsScreenState.UnSupportedApiError(it.data)
                } else {
                    state.value = TranslationsScreenState.Error
                }

                logE("translate error", it)
            }
        }
    }

    private fun startSaveTimer() {
        historyTimerJob?.cancel()
        historyTimerJob = viewModelScope.launch {
            delay(MIN_INTERVAL_SAVE_MS)
            saveItem()
        }
    }

    private fun startTextCheckerTimer() {
        textCheckerJob?.cancel()
        textCheckerJob = viewModelScope.launch {
            while (true) {
                delay(MIN_INTERVAL_API_MS)
                if (lastInputText != inputTextData.value.text) {
                    translate()
                }
            }
        }
    }

    private suspend fun saveItem() {
        if (inputTextData.value.text.isEmpty()) {
            return
        }

        withContext(Dispatchers.IO) {
            val item = HistoryItem(
                inputText = inputTextData.value.text.trim(),
                outputText = outputTextData.value.mainText,
                inputLanguage = inputLanguage.value,
                outputLanguage = outputLanguage.value,
            )
            val updated = db.historyDao.update(
                inputText = item.inputText,
                outputText = item.outputText,
                inputLanguage = item.inputLanguage.code,
                outputLanguage = item.outputLanguage.code,
                insertedMS = item.insertedMS,
            )

            if (updated == 0) {
                db.historyDao.insert(item)
            }
        }
    }

    companion object {
        private const val MIN_INTERVAL_API_MS = 500L
        private val MIN_INTERVAL_SAVE_MS = TimeUnit.SECONDS.toMillis(2)
    }
}