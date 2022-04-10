package cz.cuni.mff.ufal.translator.interactors.tts

import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface ITextToSpeechWrapper {

    val isTextToSpeechAvailable: StateFlow<Boolean>

    fun init()

    suspend fun speak(language: Language, text: String)

    fun stop()

    fun shutdown()
}