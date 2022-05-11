package cz.cuni.mff.ufal.translator.ui.history.viewmodel

import cz.cuni.mff.ufal.translator.base.IBaseViewModel
import cz.cuni.mff.ufal.translator.interactors.crashlytics.Screen
import cz.cuni.mff.ufal.translator.interactors.tts.TextToSpeechError
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.translations.models.InputTextData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IHistoryViewModel : IBaseViewModel {

    val allItems: StateFlow<List<HistoryItem>>

    val favouritesItems: StateFlow<List<HistoryItem>>

    val textToSpeechErrors: Flow<TextToSpeechError>

    fun deleteItem(item: HistoryItem)

    fun copyToClipBoard(text: String)

    fun textToSpeech(item: HistoryItem, screen: Screen)

    fun updateItem(item: HistoryItem)

}