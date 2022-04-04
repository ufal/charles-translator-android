package cz.cuni.mff.lindat.main.viewmodel

import android.content.Context
import cz.cuni.mff.lindat.history.data.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
class PreviewMainViewModel : IMainViewModel {
    override val inputText = MutableStateFlow("zdrojovy text")
    override val outputTextCyrillic = MutableStateFlow("prelozeny text cyrillic")
    override val outputTextLatin = MutableStateFlow("prelozeny text latin")
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val state = MutableStateFlow(MainScreenState.Success)
    override fun setInputText(text: String) {}
    override fun setInputLanguage(language: Language) {}
    override fun swapLanguages() {}
    override fun isTextToSpeechAvailable(context: Context): Boolean = true
    override fun copyToClipBoard(context: Context, label: String, text: String) {}
    override fun pasteFromClipBoard(context: Context) {}
    override fun setFromHistoryItem(item: HistoryItem) {}
    override fun retry() {}
}