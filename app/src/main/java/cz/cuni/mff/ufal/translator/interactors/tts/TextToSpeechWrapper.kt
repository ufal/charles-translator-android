package cz.cuni.mff.ufal.translator.interactors.tts

import android.content.Context
import android.os.Bundle
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

    override fun init() {
        val ttsListener = TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                isTextToSpeechAvailable.value = true
            } else {
                logE("TTS error ${getTextToSpeechInitError(status)}")
            }
        }

        textToSpeech = TextToSpeech(context, ttsListener, GOOGLE_TTS_ENGINE)
    }

    override suspend fun speak(language: Language, text: String) {
        val settings = Bundle().apply {
            putBoolean(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, userDataStore.useNetworkTTS.first())
        }

        textToSpeech.language = language.locale
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, settings, text)
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
        private const val GOOGLE_TTS_ENGINE = "com.google.android.tts"
    }

}