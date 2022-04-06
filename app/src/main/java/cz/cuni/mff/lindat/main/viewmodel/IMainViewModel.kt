package cz.cuni.mff.lindat.main.viewmodel

import cz.cuni.mff.lindat.base.IBaseViewModel
import cz.cuni.mff.lindat.history.data.HistoryItem
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IMainViewModel : IBaseViewModel {

    val inputText: StateFlow<String>
    val outputTextMain: StateFlow<String>
    val outputTextSecondary: StateFlow<String>

    val inputLanguage: StateFlow<Language>
    val outputLanguage: StateFlow<Language>

    val state: StateFlow<MainScreenState>
    val hasFinishedOnboarding: StateFlow<Boolean>
    val isTextToSpeechAvailable: StateFlow<Boolean>
    val isSpeechRecognizerAvailable: Boolean

    fun setInputText(text: String)
    fun swapLanguages()
    fun copyToClipBoard(label: String, text: String)
    fun pasteFromClipBoard()
    fun setFromHistoryItem(item: HistoryItem)
    fun retry()
    fun setFinishedOnboarding(agreeWithDataCollection: Boolean)
    fun textToSpeech()

}