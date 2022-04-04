package cz.cuni.mff.lindat.main.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.lindat.R
import cz.cuni.mff.lindat.api.IApi
import cz.cuni.mff.lindat.db.IDb
import cz.cuni.mff.lindat.extensions.logE
import cz.cuni.mff.lindat.history.data.HistoryItem
import cz.cuni.mff.lindat.utils.transliterate.Transliterate.transliterateCyrilToLatin
import cz.cuni.mff.lindat.utils.transliterate.Transliterate.transliterateLatinToCyril
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: IApi,
    private val db: IDb,
) : IMainViewModel, ViewModel() {

    private var apiJob: Job? = null
    private var timerJob: Job? = null

    private var lastRequestMs = 0L
    private val minIntervalApiMS = 500
    private val minIntervalSaveMS = TimeUnit.SECONDS.toMillis(2)

    override val inputText = MutableStateFlow("")
    override val outputTextCyrillic = MutableStateFlow("")
    override val outputTextLatin = MutableStateFlow("")
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val state = MutableStateFlow(MainScreenState.Idle)

    override fun onStop() {
        super.onStop()

        apiJob?.cancel()
        apiJob = null

        timerJob?.cancel()
        timerJob = null
    }

    override fun setInputText(text: String) {
        inputText.value = text
        translate()
        startSaveTimer()
    }

    override fun setInputLanguage(language: Language) {
        inputLanguage.value = language
    }

    override fun swapLanguages() {
        val tmpInputLanguage = inputLanguage.value
        inputLanguage.value = outputLanguage.value
        outputLanguage.value = tmpInputLanguage

        //outputTextCyrillic.value = ""
        //outputTextLatin.value = ""
        translate()
    }

    override fun isTextToSpeechAvailable(context: Context): Boolean {
        return SpeechRecognizer.isRecognitionAvailable(context)
    }

    override fun copyToClipBoard(context: Context, label: String, text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, text))

        Toast.makeText(context, R.string.toast_copied_to_clipboard, Toast.LENGTH_SHORT).show()
    }

    override fun setFromHistoryItem(item: HistoryItem) {
        inputLanguage.value = item.inputLanguage
        outputLanguage.value = item.outputLanguage
        //outputTextCyrillic.value = ""
        //outputTextLatin.value = ""
        setInputText(item.text)
    }

    override fun retry() {
        translate()
    }

    private fun translate() {
        if (inputText.value.isBlank()) {
            apiJob?.cancel()
            outputTextCyrillic.value = ""
            outputTextLatin.value = ""
            return
        }

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastRequestMs < minIntervalApiMS) {
            return
        }

        apiJob?.cancel()
        apiJob = viewModelScope.launch {
            state.value = MainScreenState.Loading

            api.translate(
                inputLanguage = inputLanguage.value,
                outputLanguage = outputLanguage.value,
                text = inputText.value.trim()
            ).onSuccess {
                lastRequestMs = System.currentTimeMillis()
                when (outputLanguage.value) {
                    Language.Czech -> {
                        outputTextCyrillic.value = transliterateLatinToCyril(it)
                        outputTextLatin.value = it
                    }
                    Language.Ukrainian -> {
                        outputTextCyrillic.value = it
                        outputTextLatin.value = transliterateCyrilToLatin(it)
                    }
                }
                state.value = MainScreenState.Success
            }.onFailure {
                state.value = MainScreenState.Error
                logE("error $it")
            }
        }
    }

    private fun startSaveTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            delay(minIntervalSaveMS)
            saveItem()
        }
    }

    private suspend fun saveItem() {
        if (inputText.value.isEmpty()) {
            return
        }

        withContext(Dispatchers.IO) {
            val item = HistoryItem(
                text = inputText.value,
                inputLanguage = inputLanguage.value,
                outputLanguage = outputLanguage.value,
            )
            db.historyDao.insert(item)
        }
    }
}