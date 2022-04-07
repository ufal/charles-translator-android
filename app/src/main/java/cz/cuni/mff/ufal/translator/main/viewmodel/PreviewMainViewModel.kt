package cz.cuni.mff.ufal.translator.main.viewmodel

import cz.cuni.mff.ufal.translator.history.data.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewMainViewModel : IMainViewModel {
    override val inputTextData = MutableStateFlow(InputTextData("zdrojovy text"))
    override val outputTextData = MutableStateFlow(OutputTextData("hlavni text", "druhy text"))
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val state = MutableStateFlow(MainScreenState.Success)
    override val hasFinishedOnboarding = MutableStateFlow(true)
    override val isTextToSpeechAvailable = MutableStateFlow(true)
    override val isSpeechRecognizerAvailable = true

    override fun setInputText(data: InputTextData) {}
    override fun swapLanguages() {}
    override fun copyToClipBoard(label: String, text: String) {}
    override fun pasteFromClipBoard() {}
    override fun setFromHistoryItem(item: HistoryItem) {}
    override fun retry() {}
    override fun setFinishedOnboarding(agreeWithDataCollection: Boolean) {}
    override fun textToSpeech() {}
}