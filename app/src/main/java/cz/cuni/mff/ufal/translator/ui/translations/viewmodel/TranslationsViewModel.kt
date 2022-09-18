package cz.cuni.mff.ufal.translator.ui.translations.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.speech.SpeechRecognizer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.extensions.logE
import cz.cuni.mff.ufal.translator.interactors.ContextUtils
import cz.cuni.mff.ufal.translator.interactors.ContextUtils.isNetworkConnected
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateCyrilToLatin
import cz.cuni.mff.ufal.translator.interactors.Transliterate.transliterateLatinToCyril
import cz.cuni.mff.ufal.translator.interactors.analytics.IAnalytics
import cz.cuni.mff.ufal.translator.interactors.analytics.events.SpeechToTextEvent
import cz.cuni.mff.ufal.translator.interactors.analytics.events.TranslateEvent
import cz.cuni.mff.ufal.translator.interactors.api.IApi
import cz.cuni.mff.ufal.translator.interactors.api.UnsupportedApiException
import cz.cuni.mff.ufal.translator.interactors.asr.IAudioTextRecognizer
import cz.cuni.mff.ufal.translator.interactors.crashlytics.Screen
import cz.cuni.mff.ufal.translator.interactors.db.IDb
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.interactors.tts.ITextToSpeechWrapper
import cz.cuni.mff.ufal.translator.ui.common.widgets.BuildConfigWrapper
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.translations.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
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
    private val textToSpeech: ITextToSpeechWrapper,
    private val analytics: IAnalytics,
    private val audioTextRecognizer: IAudioTextRecognizer,
) : ITranslationsViewModel, AndroidViewModel(context) {

    private var apiJob: Job? = null
    private var textCheckerJob: Job? = null
    private var historyTimerJob: Job? = null

    private var lastInputText = ""

    private val connectivityManager =
        getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {

            state.value = if (inputTextData.value.text.isEmpty()) {
                TranslationsScreenState.Idle
            } else {
                TranslationsScreenState.Success
            }

            translate()
        }

        override fun onLost(network: Network) {
            state.value = TranslationsScreenState.Offline
        }
    }

    private val isUkUser get() = Locale.getDefault().language == "uk" || Locale.getDefault().language == "ru"

    override val inputTextData = MutableStateFlow(InputTextData())
    override val outputTextData = MutableStateFlow(OutputTextData())
    override val inputLanguage = MutableStateFlow(if (isUkUser) Language.Ukrainian else Language.Czech)
    override val outputLanguage = MutableStateFlow(if (isUkUser) Language.Czech else Language.Ukrainian)
    override val state = MutableStateFlow<TranslationsScreenState>(TranslationsScreenState.Idle)
    override val hasFinishedOnboarding = userDataStore.hasFinishedOnboarding.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        true,
    )
    override val textToSpeechErrors = textToSpeech.errors
    override val isSpeechRecognizerAvailable: Boolean
        get() = SpeechRecognizer.isRecognitionAvailable(getApplication())
    override val isListening = audioTextRecognizer.isListening

    override fun onStart() {
        super.onStart()

        viewModelScope.launch {
            textToSpeech.init()
        }

        startTextCheckerTimer()

        if (!isNetworkConnected(getApplication<Application>())) {
            state.value = TranslationsScreenState.Offline
        }
        registerNetworkCallback()

        audioTextRecognizer.text.onEach {
            if (it.isNotBlank()) {
                inputTextData.value = InputTextData(it, TextSource.Voice)
            }
        }.launchIn(viewModelScope)
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

        unregisterNetworkCallback()
    }

    override fun onCleared() {
        super.onCleared()

        textToSpeech.shutdown()
    }

    override fun setInputText(data: InputTextData) {
        if (data.text == inputTextData.value.text) { //to prevent overwrite source
            return
        }

        if (data.source == TextSource.Voice) {
            analytics.logEvent(
                SpeechToTextEvent(
                    language = inputLanguage.value,
                    text = data.text
                )
            )
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
        setInputText(InputTextData(item.inputText, TextSource.History))
    }

    override fun retry() {
        translate()
    }

    override fun setFinishedOnboarding(agreeWithDataCollection: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.setFinishedOnboarding()
            userDataStore.saveAgreementDataCollection(agreeWithDataCollection)
            if (BuildConfigWrapper.isRelease) {
                Firebase.analytics.setAnalyticsCollectionEnabled(agreeWithDataCollection)
            }
        }
    }

    override fun textToSpeech() {
        viewModelScope.launch {
            textToSpeech.speak(
                language = outputLanguage.value,
                text = outputTextData.value.mainText,
                screen = Screen.Translations,
            )
        }
    }

    override fun startRecognizeAudio() {
        setInputText(InputTextData("", TextSource.ClearVoice))
        audioTextRecognizer.startRecognize(inputLanguage.value)
    }

    override fun stopRecognizeAudio() {
        audioTextRecognizer.stopRecognize()
    }

    private fun translate() {
        if (state.value == TranslationsScreenState.Offline) {
            return
        }

        if (inputTextData.value.text.length >= MAX_CHARACTERS) {
            state.value = TranslationsScreenState.MaxCharactersLimitError
            return
        }

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

            analytics.logEvent(
                TranslateEvent(
                    inputLanguage = inputLanguage.value,
                    outputLanguage = outputLanguage.value,
                    data = inputTextData.value,
                )
            )

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

    private fun registerNetworkCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }

    private fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    companion object {
        const val MAX_CHARACTERS = 1000

        private const val MIN_INTERVAL_API_MS = 500L
        private val MIN_INTERVAL_SAVE_MS = TimeUnit.SECONDS.toMillis(2)
    }
}