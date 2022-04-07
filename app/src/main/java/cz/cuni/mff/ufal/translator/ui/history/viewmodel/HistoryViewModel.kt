package cz.cuni.mff.ufal.translator.ui.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.ufal.translator.interactors.db.IDb
import cz.cuni.mff.ufal.translator.ui.history.model.HistoryItem
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
            db.historyDao.delete(item)
        }
    }

    override fun updateItem(item: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            db.historyDao.update(item)
        }
    }

    override val historyItems = MutableStateFlow(emptyList<HistoryItem>())

    private fun startListenHistoryItems() {
        db.historyDao.getAll().onEach {
            historyItems.value = it
        }.launchIn(viewModelScope)
    }

}