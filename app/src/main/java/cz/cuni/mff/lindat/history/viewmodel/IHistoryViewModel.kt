package cz.cuni.mff.lindat.history.viewmodel

import cz.cuni.mff.lindat.base.IBaseViewModel
import cz.cuni.mff.lindat.history.data.HistoryItem
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface IHistoryViewModel: IBaseViewModel {

    val historyItems: StateFlow<List<HistoryItem>>
}