package cz.uk.lindat.main.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewMainViewModel : IMainViewModel {
    override val inputText = MutableStateFlow("zdrojovy text")
    override val outputText = MutableStateFlow("prelozeny text")
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)

    override fun setInputText(text: String) {}
    override fun setInputLanguage(language: Language) {}
    override fun swapLanguages() {}
}