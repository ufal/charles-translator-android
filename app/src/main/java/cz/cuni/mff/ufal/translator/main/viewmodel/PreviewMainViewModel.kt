package cz.cuni.mff.ufal.translator.main.viewmodel

import cz.cuni.mff.ufal.translator.history.data.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewMainViewModel : IMainViewModel {
    override val inputText = MutableStateFlow("zdrojovy text")
    override val outputTextMain = MutableStateFlow("hlavni text")
    override val outputTextSecondary = MutableStateFlow("druhy text")
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val state = MutableStateFlow(MainScreenState.Success)
    override val hasFinishedOnboarding = MutableStateFlow(true)
    override val isTextToSpeechAvailable = MutableStateFlow(true)
    override val isSpeechRecognizerAvailable = true

    override fun setInputText(text: String) {}
    override fun swapLanguages() {}
    override fun copyToClipBoard(label: String, text: String) {}
    override fun pasteFromClipBoard() {}
    override fun setFromHistoryItem(item: HistoryItem) {}
    override fun retry() {}
    override fun setFinishedOnboarding(agreeWithDataCollection: Boolean) {}
    override fun textToSpeech() {}
}