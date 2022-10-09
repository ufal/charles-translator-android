package cz.cuni.mff.ufal.translator.ui.conversation.viewmodel

import cz.cuni.mff.ufal.translator.base.IBaseViewModel
import cz.cuni.mff.ufal.translator.ui.conversation.models.ConversationModel
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IConversationViewModel: IBaseViewModel {

    val isSpeechRecognizerAvailable: Boolean
    val isListening: StateFlow<Boolean>
    val isOffline: StateFlow<Boolean>
    val rmsdB: StateFlow<Float>
    val activeLanguage: StateFlow<Language>
    val conversation: StateFlow<List<ConversationModel>>
    fun startRecognizeAudio(language: Language)
    fun stopRecognizeAudio()

}