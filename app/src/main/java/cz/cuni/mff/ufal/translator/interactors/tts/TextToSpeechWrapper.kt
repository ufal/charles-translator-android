package cz.cuni.mff.ufal.translator.interactors.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import cz.cuni.mff.ufal.translator.extensions.logE
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
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

    private lateinit var textToSpeech: TextToSpeech

    override val isTextToSpeechAvailable = MutableStateFlow(false)
    override val engines = MutableStateFlow(emptyList<TextToSpeech.EngineInfo>())

    override suspend fun init() {
        val ttsListener = TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                isTextToSpeechAvailable.value = true
                engines.value = textToSpeech.engines
            } else {
                logE("TTS error ${getTextToSpeechInitError(status)}")
            }
        }

        textToSpeech = TextToSpeech(context, ttsListener, userDataStore.ttsEngine.first())
    }

    override suspend fun speak(language: Language, text: String) {
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
                logE("voice for ${language.locale} not found")
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