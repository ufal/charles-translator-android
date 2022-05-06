package cz.cuni.mff.ufal.translator.ui.translations.viewmodel

import cz.cuni.mff.ufal.translator.base.IBaseViewModel
import cz.cuni.mff.ufal.translator.interactors.tts.TextToSpeechError
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.translations.models.InputTextData
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.OutputTextData
import cz.cuni.mff.ufal.translator.ui.translations.models.TranslationsScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface ITranslationsViewModel : IBaseViewModel {

    val inputTextData: StateFlow<InputTextData>
    val outputTextData: StateFlow<OutputTextData>

    val inputLanguage: StateFlow<Language>
    val outputLanguage: StateFlow<Language>

    val state: StateFlow<TranslationsScreenState>
    val hasFinishedOnboarding: StateFlow<Boolean>
    val textToSpeechErrors: Flow<TextToSpeechError>
    val isSpeechRecognizerAvailable: Boolean

    fun setInputText(data: InputTextData)
    fun swapLanguages()
    fun copyToClipBoard(text: String)
    fun pasteFromClipBoard()
    fun setFromHistoryItem(item: HistoryItem)
    fun retry()
    fun setFinishedOnboarding(agreeWithDataCollection: Boolean)
    fun textToSpeech()

}