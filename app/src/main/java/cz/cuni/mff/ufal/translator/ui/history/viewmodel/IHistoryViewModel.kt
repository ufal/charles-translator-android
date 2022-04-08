package cz.cuni.mff.ufal.translator.ui.history.viewmodel

import cz.cuni.mff.ufal.translator.base.IBaseViewModel
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
import cz.cuni.mff.ufal.translator.ui.translations.models.InputTextData
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IHistoryViewModel : IBaseViewModel {

    val allItems: StateFlow<List<HistoryItem>>

    val favouritesItems: StateFlow<List<HistoryItem>>

    val isTextToSpeechAvailable: StateFlow<Boolean>

    fun deleteItem(item: HistoryItem)

    fun copyToClipBoard(text: String)

    fun textToSpeech(item: HistoryItem)

    fun updateItem(item: HistoryItem)

}