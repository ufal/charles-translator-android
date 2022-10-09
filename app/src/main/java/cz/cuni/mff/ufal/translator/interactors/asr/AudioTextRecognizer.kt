package cz.cuni.mff.ufal.translator.interactors.asr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import cz.cuni.mff.ufal.translator.extensions.logE
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


/**
 * @author Tomas Krabac
 */
class AudioTextRecognizer @Inject constructor(
    context: Context,
) : IAudioTextRecognizer {

    private var recognizedText = ""

    override val rmsdB = MutableStateFlow(0.0f)

    override val isListening = MutableStateFlow(false)
    override val text = MutableStateFlow("")


    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
        setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {

            }

            override fun onBeginningOfSpeech() {
                recognizedText = text.value
            }

            override fun onRmsChanged(rmsdB: Float) {
                this@AudioTextRecognizer.rmsdB.value = rmsdB
            }

            override fun onBufferReceived(buffer: ByteArray) {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(error: Int) {
                logE("ASR error ${getErrorMessage(error)}")
            }

            override fun onResults(results: Bundle) {
                val data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()

                if (data != null) {
                    text.value = data
                }
                isListening.value = false
            }

            override fun onPartialResults(partialResults: Bundle) {
                val data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()

                if (data != null && recognizedText.isBlank()) {
                    text.value = data
                } else if (data != null) {
                    text.value = "$recognizedText$data"
                }
            }

            override fun onEvent(eventType: Int, params: Bundle) {

            }

        })
    }

    override fun startRecognize(language: Language) {
        isListening.value = true
        text.value = ""

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, language.locale.language)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 50000)
        }

        speechRecognizer.startListening(speechRecognizerIntent)
    }

    override fun stopRecognize() {
        speechRecognizer.stopListening()
        isListening.value = false
    }

    override fun clear() {
        stopRecognize()
        speechRecognizer.destroy()
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "ERROR_NETWORK_TIMEOUT"
            SpeechRecognizer.ERROR_NETWORK -> "ERROR_NETWORK"
            SpeechRecognizer.ERROR_AUDIO -> "ERROR_AUDIO"
            SpeechRecognizer.ERROR_SERVER -> "ERROR_SERVER"
            SpeechRecognizer.ERROR_CLIENT -> "ERROR_CLIENT"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "ERROR_SPEECH_TIMEOUT"
            SpeechRecognizer.ERROR_NO_MATCH -> "ERROR_NO_MATCH"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "ERROR_RECOGNIZER_BUSY"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "ERROR_INSUFFICIENT_PERMISSIONS"
            SpeechRecognizer.ERROR_TOO_MANY_REQUESTS -> "ERROR_TOO_MANY_REQUESTS"
            SpeechRecognizer.ERROR_SERVER_DISCONNECTED -> "ERROR_SERVER_DISCONNECTED"
            SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED -> "ERROR_LANGUAGE_NOT_SUPPORTED"
            SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE -> "ERROR_LANGUAGE_UNAVAILABLE"
            SpeechRecognizer.ERROR_CANNOT_CHECK_SUPPORT -> "ERROR_CANNOT_CHECK_SUPPORT"
            else -> "UNKNOWN"
        }
    }

}