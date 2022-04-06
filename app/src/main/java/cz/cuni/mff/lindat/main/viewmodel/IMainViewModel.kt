package cz.cuni.mff.lindat.main.viewmodel

import android.content.Context
import cz.cuni.mff.lindat.base.IBaseViewModel
import cz.cuni.mff.lindat.history.data.HistoryItem
import kotlinx.coroutines.flow.Flow
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

    val state: StateFlow<MainScreenState>
    val hasFinishedOnboarding: StateFlow<Boolean>

    fun setInputText(text: String)
    fun setInputLanguage(language: Language)
    fun swapLanguages()
    fun isTextToSpeechAvailable(context: Context): Boolean
    fun copyToClipBoard(context: Context, label: String, text: String)
    fun pasteFromClipBoard(context: Context)
    fun setFromHistoryItem(item: HistoryItem)
    fun retry()
    fun setFinishedOnboarding(agreeWithDataCollection: Boolean)

}