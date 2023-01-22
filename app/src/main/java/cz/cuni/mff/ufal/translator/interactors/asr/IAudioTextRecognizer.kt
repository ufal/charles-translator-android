package cz.cuni.mff.ufal.translator.interactors.asr

import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IAudioTextRecognizer {

    val rmsdB: StateFlow<Float>

    val isListening: StateFlow<Boolean>

    val text: StateFlow<String>

    val activeLanguage: StateFlow<Language>

    fun startRecognize(language: Language)

    fun stopRecognize()

    fun clear()

}