package cz.cuni.mff.ufal.translator.ui.translations.viewmodel

import android.app.Application
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.extensions.logE
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateCyrilToLatin
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateLatinToCyril
import cz.cuni.mff.ufal.translator.interactors.api.IApi
import cz.cuni.mff.ufal.translator.interactors.db.IDb
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.ui.common.ContextUtils
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
) : ITranslationsViewModel, AndroidViewModel(context) {

    private var apiJob: Job? = null
    private var timerJob: Job? = null

    private var lastRequestMs = 0L
    private val minIntervalApiMS = 500
    private val minIntervalSaveMS = TimeUnit.SECONDS.toMillis(2)

    private var textToSpeechEngine: TextToSpeech? = null

    override val inputTextData = MutableStateFlow(InputTextData())
    override val outputTextData = MutableStateFlow(OutputTextData())
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val state = MutableStateFlow(TranslationsScreenState.Idle)
    override val hasFinishedOnboarding = userDataStore.hasFinishedOnboarding().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        true,
    )
    override val isTextToSpeechAvailable = MutableStateFlow(false)
    override val isSpeechRecognizerAvailable: Boolean
        get() = SpeechRecognizer.isRecognitionAvailable(getApplication())

    override fun onStart() {
        super.onStart()

        textToSpeechEngine = TextToSpeech(getApplication()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine?.language = outputLanguage.value.locale
                isTextToSpeechAvailable.value = true
            }
            // TODO: error states
        }
    }

    override fun onStop() {
        super.onStop()

        apiJob?.cancel()
        apiJob = null

        timerJob?.cancel()
        timerJob = null

        textToSpeechEngine?.stop()
    }

    override fun onCleared() {
        super.onCleared()

        textToSpeechEngine?.shutdown()
    }

    override fun setInputText(data: InputTextData) {
        inputTextData.value = data
        translate()
        startSaveTimer()
    }

    override fun swapLanguages() {
        val tmpInputLanguage = inputLanguage.value
        inputLanguage.value = outputLanguage.value
        outputLanguage.value = tmpInputLanguage

        translate()

        textToSpeechEngine?.language = outputLanguage.value.locale
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
        val text = outputTextData.value.mainText
        textToSpeechEngine?.speak(text, TextToSpeech.QUEUE_FLUSH, null, text)
    }

    private fun translate() {
        if (inputTextData.value.text.isBlank()) {
            apiJob?.cancel()
            outputTextData.value = OutputTextData()
            state.value = TranslationsScreenState.Idle
            return
        }

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastRequestMs < minIntervalApiMS) {
            return
        }

        apiJob?.cancel()
        apiJob = viewModelScope.launch {
            state.value = TranslationsScreenState.Loading

            api.translate(
                inputLanguage = inputLanguage.value,
                outputLanguage = outputLanguage.value,
                text = inputTextData.value.text.trim()
            ).onSuccess {
                lastRequestMs = System.currentTimeMillis()
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
                state.value = TranslationsScreenState.Error
                logE("translate error", it)
            }
        }
    }

    private fun startSaveTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            delay(minIntervalSaveMS)
            saveItem()
        }
    }

    private suspend fun saveItem() {
        if (inputTextData.value.text.isEmpty()) {
            return
        }

        withContext(Dispatchers.IO) {
            val item = HistoryItem(
                inputText = inputTextData.value.text,
                outputText = outputTextData.value.mainText,
                inputLanguage = inputLanguage.value,
                outputLanguage = outputLanguage.value,
            )
            db.historyDao.insert(item)
        }
    }
}