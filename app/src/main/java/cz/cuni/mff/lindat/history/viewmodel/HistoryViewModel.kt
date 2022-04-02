package cz.cuni.mff.lindat.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.lindat.db.IDb
import cz.cuni.mff.lindat.db.history.HistoryItemDB
import cz.cuni.mff.lindat.history.data.HistoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val db: IDb,
) : IHistoryViewModel, ViewModel() {

    override fun onStart() {
        super.onStart()

        startListenHistoryItems()
    }

    override fun deleteItem(item: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            db.historyDao.delete(HistoryItemDB(item))
        }
    }

    override fun updateItem(item: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            db.historyDao.update(HistoryItemDB(item))
        }
    }

    override val historyItems = MutableStateFlow(emptyList<HistoryItem>())

    private fun startListenHistoryItems() {
        db.historyDao.getAll().onEach {
            historyItems.value = it.map(::HistoryItem)
        }.launchIn(viewModelScope)
    }

}