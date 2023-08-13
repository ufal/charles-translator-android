package cz.cuni.mff.ufal.translator.ui.conversation.viewmodel

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
import cz.cuni.mff.ufal.translator.extensions.logE
import cz.cuni.mff.ufal.translator.interactors.ContextUtils
import cz.cuni.mff.ufal.translator.interactors.analytics.IAnalytics
import cz.cuni.mff.ufal.translator.interactors.analytics.events.ConverstationEvent
import cz.cuni.mff.ufal.translator.interactors.api.IApi
import cz.cuni.mff.ufal.translator.interactors.asr.IAudioTextRecognizer
import cz.cuni.mff.ufal.translator.interactors.languages.ILanguagesManager
import cz.cuni.mff.ufal.translator.ui.conversation.models.BubblePosition
import cz.cuni.mff.ufal.translator.ui.conversation.models.ConversationModel
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.OutputTextData
import cz.cuni.mff.ufal.translator.ui.translations.models.TextSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */
@HiltViewModel
class ConversationViewModel @Inject constructor(
    context: Application,
    private val audioTextRecognizer: IAudioTextRecognizer,
    private val api: IApi,
    private val analytics: IAnalytics,
    languagesManager: ILanguagesManager,
) : IConversationViewModel, AndroidViewModel(context) {

    private var audioTextRecognizerJob: Job? = null
    private var isNewBubble = true
    private var apiJob: Job? = null

    private val connectivityManager =
        getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            isOffline.value = false
        }

        override fun onLost(network: Network) {
            isOffline.value = true
        }
    }

    override val isSpeechRecognizerAvailable: Boolean
        get() = SpeechRecognizer.isRecognitionAvailable(getApplication())

    override val isListening = audioTextRecognizer.isListening
    override val isOffline = MutableStateFlow(!ContextUtils.isNetworkConnected(getApplication()))
    override val rmsdB = audioTextRecognizer.rmsdB

    override val leftLanguage = Language.Czech
    override val rightLanguage = MutableStateFlow(languagesManager.supportedConversationLanguages.first())

    override val rightLanguages = languagesManager.supportedConversationLanguages

    override val activeLanguage = audioTextRecognizer.activeLanguage

    private val secondLanguage
        get() = if (activeLanguage.value == leftLanguage) {
            rightLanguage.value
        } else {
            leftLanguage
        }

    override val conversation = MutableStateFlow(emptyList<ConversationModel>())

    override fun setRightLanguage(language: Language) {
        rightLanguage.value = language
    }

    override fun onStart() {
        super.onStart()

        registerNetworkCallback()
    }


    override fun onStop() {
        super.onStop()

        stopRecognizeAudio()
        unregisterNetworkCallback()

        apiJob?.cancel()
        apiJob = null
    }

    override fun startRecognizeAudio(language: Language) {
        if (isListening.value) {
            stopRecognizeAudio()
            return
        }

        isNewBubble = true
        audioTextRecognizer.startRecognize(language)
        audioTextRecognizerJob = audioTextRecognizer.text.onEach {
            addConversation(it)
        }.launchIn(viewModelScope)
    }

    override fun stopRecognizeAudio() {
        audioTextRecognizer.stopRecognize()
        audioTextRecognizerJob?.cancel()
        audioTextRecognizerJob = null
    }

    private fun addConversation(inputText: String) {
        if (inputText.isBlank()) {
            return
        }

        val activeLanguage = activeLanguage.value
        val lastBubble = conversation.value.lastOrNull()
        val originalConversation = conversation.value

        when {//nova bublina

            lastBubble == null || isNewBubble -> {
                analytics.logEvent(
                    ConverstationEvent(
                        inputLanguage = activeLanguage,
                        outputLanguage = secondLanguage,
                        text = inputText,
                    )
                )

                val newBubble = ConversationModel(
                    text = MutableStateFlow(
                        OutputTextData(
                            mainText = inputText,
                            secondaryText = "",
                        )
                    ),
                    language = activeLanguage,
                    position = if (activeLanguage == leftLanguage) BubblePosition.Left else BubblePosition.Right,
                )
                translate(newBubble)

                conversation.value = originalConversation + newBubble
                isNewBubble = false
            }

            else -> { //aktualizace bubliny
                lastBubble.text.value = lastBubble.text.value.copy(mainText = inputText)
                translate(lastBubble)
                conversation.value =
                    originalConversation.subList(0, (originalConversation.size - 1).coerceAtLeast(0)) + lastBubble
            }
        }
    }

    private fun translate(
        conversationModel: ConversationModel,
    ) {
        val inputText = conversationModel.text.value.mainText
        val inputLanguage = conversationModel.language


        if (inputText.isBlank()) {
            apiJob?.cancel()
            return
        }

        apiJob?.cancel()
        apiJob = viewModelScope.launch {
            api.translate(
                inputLanguage = inputLanguage,
                outputLanguage = secondLanguage,
                text = inputText.trim(),
                textSource = TextSource.Voice,
            ).onSuccess { tranlatedText ->
                conversationModel.text.value = OutputTextData(inputText, tranlatedText)
            }.onFailure {
                logE("translate error", it)
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

}