package cz.cuni.mff.ufal.translator.ui.translations.viewmodel

import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.translations.models.TranslationsScreenState
import cz.cuni.mff.ufal.translator.ui.translations.models.InputTextData
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.OutputTextData
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewTranslationsViewModel : ITranslationsViewModel {
    override val inputTextData = MutableStateFlow(InputTextData("zdrojovy text"))
    override val outputTextData = MutableStateFlow(OutputTextData("hlavni text", "druhy text"))
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val state = MutableStateFlow(TranslationsScreenState.Success)
    override val hasFinishedOnboarding = MutableStateFlow(true)
    override val isTextToSpeechAvailable = MutableStateFlow(true)
    override val isSpeechRecognizerAvailable = true

    override fun setInputText(data: InputTextData) {}
    override fun swapLanguages() {}
    override fun copyToClipBoard(text: String) {}
    override fun pasteFromClipBoard() {}
    override fun setFromHistoryItem(item: HistoryItem) {}
    override fun retry() {}
    override fun setFinishedOnboarding(agreeWithDataCollection: Boolean) {}
    override fun textToSpeech() {}
}