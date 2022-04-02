package cz.cuni.mff.lindat.history.viewmodel

import cz.cuni.mff.lindat.history.data.HistoryItem
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IHistoryViewModel {

    fun startListenHistoryItems()

    val historyItems: StateFlow<List<HistoryItem>>
}