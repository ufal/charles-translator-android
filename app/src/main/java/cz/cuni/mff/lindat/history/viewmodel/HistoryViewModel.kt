package cz.cuni.mff.lindat.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.lindat.db.IDb
import cz.cuni.mff.lindat.history.data.HistoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val db: IDb,
) : IHistoryViewModel, ViewModel() {

     override fun startListenHistoryItems() {
        db.historyDao.getAll().onEach {
            historyItems.value = it.map(::HistoryItem)
        }
            .launchIn(viewModelScope)
    }

    override val historyItems = MutableStateFlow(emptyList<HistoryItem>())

}