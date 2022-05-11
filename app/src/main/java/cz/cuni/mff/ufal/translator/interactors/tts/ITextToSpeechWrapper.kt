package cz.cuni.mff.ufal.translator.interactors.tts

import android.speech.tts.TextToSpeech
import cz.cuni.mff.ufal.translator.interactors.crashlytics.Screen
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface ITextToSpeechWrapper {

    val errors: Flow<TextToSpeechError>

    val engines: StateFlow<List<TextToSpeech.EngineInfo>>

    suspend fun init()

    suspend fun speak(language: Language, text: String, screen: Screen)

    fun stop()

    fun shutdown()
}

enum class TextToSpeechError{
    InitError,
    SpeakError,
}