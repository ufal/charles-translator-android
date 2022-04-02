package cz.uk.lindat.main.viewmodel

import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IMainViewModel {

    val inputText: StateFlow<String>
    val outputText: StateFlow<String>

    val inputLanguage: StateFlow<Language>
    val outputLanguage: StateFlow<Language>

    fun setInputText(text: String)
    fun setInputLanguage(language: Language)
    fun swapLanguages()
}