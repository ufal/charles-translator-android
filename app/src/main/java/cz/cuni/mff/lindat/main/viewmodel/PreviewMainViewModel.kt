package cz.cuni.mff.lindat.main.viewmodel

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewMainViewModel : IMainViewModel {
    override val inputText = MutableStateFlow("zdrojovy text")
    override val outputTextCyrillic = MutableStateFlow("prelozeny text")
    override val outputTextLatin = MutableStateFlow("prelozeny text v latince")
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val showCyrillic = MutableStateFlow(true)

    override fun setInputText(text: String) {}
    override fun setInputLanguage(language: Language) {}
    override fun swapLanguages() {}
    override fun setShowCyrillic(showCyrilic: Boolean) {}
    override fun isTextToSpeechAvailable(context: Context): Boolean = true
    override fun startSaveTimer() {}
}