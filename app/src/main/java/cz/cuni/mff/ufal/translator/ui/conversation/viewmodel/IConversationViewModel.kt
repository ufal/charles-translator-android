package cz.cuni.mff.ufal.translator.ui.conversation.viewmodel

import cz.cuni.mff.ufal.translator.base.IBaseViewModel
import cz.cuni.mff.ufal.translator.ui.conversation.models.ConversationModel
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IConversationViewModel: IBaseViewModel {

    val leftLanguage: Language
    val rightLanguage: StateFlow<Language>
    val rightLanguages: List<Language>
    val activeLanguage: StateFlow<Language>

    val isSpeechRecognizerAvailable: Boolean
    val isListening: StateFlow<Boolean>
    val isOffline: StateFlow<Boolean>
    val rmsdB: StateFlow<Float>
    val conversation: StateFlow<List<ConversationModel>>

    fun setRightLanguage(language: Language)
    fun startRecognizeAudio(language: Language)
    fun stopRecognizeAudio()

}