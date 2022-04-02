package cz.cuni.mff.lindat.main.viewmodel

import android.content.Context
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IMainViewModel {

    val inputText: StateFlow<String>
    val outputTextCyrillic: StateFlow<String>
    val outputTextLatin: StateFlow<String>

    val inputLanguage: StateFlow<Language>
    val outputLanguage: StateFlow<Language>

    val showCyrillic: StateFlow<Boolean>

    fun setInputText(text: String)
    fun setInputLanguage(language: Language)
    fun swapLanguages()
    fun setShowCyrillic(showCyrilic: Boolean)
    fun isTextToSpeechAvailable(context: Context): Boolean
}