package cz.cuni.mff.ufal.translator.history.viewmodel

import cz.cuni.mff.ufal.translator.base.IBaseViewModel
import cz.cuni.mff.ufal.translator.history.data.HistoryItem
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IHistoryViewModel : IBaseViewModel {

    fun deleteItem(item: HistoryItem)

    fun updateItem(item: HistoryItem)

    val historyItems: StateFlow<List<HistoryItem>>
}