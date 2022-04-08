package cz.cuni.mff.ufal.translator.ui.history.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.ufal.translator.R
import cz.cuni.mff.ufal.translator.interactors.db.IDb
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
) : IHistoryViewModel, AndroidViewModel(context) {

    // TODO: make some wrapper for common logic
    private var textToSpeechEngine: TextToSpeech? = null

    override val isTextToSpeechAvailable = MutableStateFlow(false)
    override val allItems = MutableStateFlow(emptyList<HistoryItem>())
    override val favouritesItems = MutableStateFlow(emptyList<HistoryItem>())

    override fun onStart() {
        super.onStart()

        textToSpeechEngine = TextToSpeech(getApplication()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isTextToSpeechAvailable.value = true
            }
            // TODO: error states
        }

        startListenHistoryItems()
    }

    override fun onStop() {
        super.onStop()

        textToSpeechEngine?.stop()
    }

    override fun onCleared() {
        super.onCleared()

        textToSpeechEngine?.shutdown()
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
        textToSpeechEngine?.language = item.outputLanguage.locale
        textToSpeechEngine?.speak(item.outputText, TextToSpeech.QUEUE_FLUSH, null, item.outputText)
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