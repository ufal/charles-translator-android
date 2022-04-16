package cz.cuni.mff.ufal.translator.interactors.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import cz.cuni.mff.ufal.translator.extensions.logD
import cz.cuni.mff.ufal.translator.extensions.logE
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */
class TextToSpeechWrapper @Inject constructor(
    private val context: Context,
    private val userDataStore: IUserDataStore
) : ITextToSpeechWrapper {

    private var initError = false
    private lateinit var textToSpeech: TextToSpeech

    override val errors = MutableSharedFlow<TextToSpeechError>(extraBufferCapacity = 10)
    override val engines = MutableStateFlow(emptyList<TextToSpeech.EngineInfo>())

    override suspend fun init() {
        val ttsInitListener = TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                engines.value = textToSpeech.engines
            } else {
                initError = true
                errors.tryEmit(TextToSpeechError.InitError)
                logE("TTS error", Throwable("TTS error ${getTextToSpeechInitError(status)}"))
            }
        }

        val ttsProgressListener = object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                logD("start TTS: $utteranceId")
            }

            override fun onDone(utteranceId: String?) {
                logD("finish TTS: $utteranceId")
            }

            override fun onError(utteranceId: String?) {
                logE("error TTS speak $utteranceId")
                errors.tryEmit(TextToSpeechError.SpeakError)
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                logE("error TTS speak ${getTextToSpeechInitError(errorCode)}")
                errors.tryEmit(TextToSpeechError.SpeakError)
            }

        }

        textToSpeech = TextToSpeech(context, ttsInitListener, userDataStore.ttsEngine.first()).apply {
            setOnUtteranceProgressListener(ttsProgressListener)
        }
    }

    override suspend fun speak(language: Language, text: String) {
        if (initError) {
            errors.tryEmit(TextToSpeechError.SpeakError)
            return
        }

        val useNetwork = userDataStore.useNetworkTTS.first()

        textToSpeech.apply {
            val voicesForLanguages = voices.filter {
                it.locale.toString().lowercase() == language.locale.toString().lowercase()
            }
            val voiceNetworkSettings = voicesForLanguages.find { it.isNetworkConnectionRequired == useNetwork }
            val selectedVoice = voiceNetworkSettings ?: voicesForLanguages.firstOrNull()
            if (selectedVoice != null) {
                voice = selectedVoice
            } else {
                logE(
                    message = "language not found",
                    throwable = Throwable("voice in engine ${userDataStore.ttsEngine.first()} for ${language.locale} not found")
                )
                this.language = language.locale
            }

            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, text)
        }
    }

    override fun stop() {
        textToSpeech.stop()
    }

    override fun shutdown() {
        textToSpeech.shutdown()
    }

    private fun getTextToSpeechInitError(status: Int): String {
        return when (status) {
            TextToSpeech.ERROR_SYNTHESIS -> "ERROR_SYNTHESIS"
            TextToSpeech.ERROR_SERVICE -> "ERROR_SERVICE"
            TextToSpeech.ERROR_OUTPUT -> "ERROR_OUTPUT"
            TextToSpeech.ERROR_NETWORK -> "ERROR_NETWORK"
            TextToSpeech.ERROR_NETWORK_TIMEOUT -> "ERROR_NETWORK_TIMEOUT"
            TextToSpeech.ERROR_INVALID_REQUEST -> "ERROR_INVALID_REQUEST"
            TextToSpeech.ERROR_NOT_INSTALLED_YET -> "ERROR_NOT_INSTALLED_YET"
            TextToSpeech.ERROR -> "ERROR"
            else -> "unknown error $status"
        }
    }

    companion object {
        const val DEFAULT_TTS_ENGINE = "com.google.android.tts"
    }

}