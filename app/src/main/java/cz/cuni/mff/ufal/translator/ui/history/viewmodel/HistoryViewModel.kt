package cz.cuni.mff.ufal.translator.ui.history.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.interactors.db.IDb
import cz.cuni.mff.ufal.translator.interactors.tts.ITextToSpeechWrapper
import cz.cuni.mff.ufal.translator.ui.common.ContextUtils
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
    context: Application,
    private val db: IDb,
    private val textToSpeech: ITextToSpeechWrapper
) : IHistoryViewModel, AndroidViewModel(context) {

    override val isTextToSpeechAvailable = textToSpeech.isTextToSpeechAvailable
    override val allItems = MutableStateFlow(emptyList<HistoryItem>())
    override val favouritesItems = MutableStateFlow(emptyList<HistoryItem>())

    override fun onStart() {
        super.onStart()

        textToSpeech.init()
        startListenHistoryItems()
    }

    override fun onStop() {
        super.onStop()

        textToSpeech.stop()
    }

    override fun onCleared() {
        super.onCleared()

        textToSpeech.shutdown()
    }

    override fun deleteItem(item: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            db.historyDao.delete(item)
        }
    }

    override fun copyToClipBoard(text: String) {
        val label = getApplication<Application>().resources.getString(R.string.copy_to_clipboard_label)
        ContextUtils.copyToClipBoard(getApplication(), label, text)
    }

    override fun textToSpeech(item: HistoryItem) {
        viewModelScope.launch {
            textToSpeech.speak(item.outputLanguage, item.outputText)
        }
    }

    override fun updateItem(item: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            db.historyDao.update(item)
        }
    }

    private fun startListenHistoryItems() {
        db.historyDao.getAll().onEach {
            allItems.value = it
        }.launchIn(viewModelScope)

        db.historyDao.getFavourites().onEach {
            favouritesItems.value = it
        }.launchIn(viewModelScope)
    }

}