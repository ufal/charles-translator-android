package cz.cuni.mff.lindat.main.viewmodel

import android.content.Context
import cz.cuni.mff.lindat.base.IBaseViewModel
import cz.cuni.mff.lindat.history.data.HistoryItem
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IMainViewModel : IBaseViewModel {

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
    fun copyToClipBoard(context: Context, label: String, text: String)
    fun setFromHistoryItem(item: HistoryItem)
}