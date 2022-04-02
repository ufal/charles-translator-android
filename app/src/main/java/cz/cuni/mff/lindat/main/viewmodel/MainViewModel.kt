package cz.cuni.mff.lindat.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltroger.latintocyrillic.Alphabet
import com.michaeltroger.latintocyrillic.LatinCyrillicFactory
import cz.cuni.mff.lindat.api.Api
import cz.cuni.mff.lindat.api.IApi
import cz.cuni.mff.lindat.extensions.logE
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * @author Tomas Krabac
 */
class MainViewModel : IMainViewModel, ViewModel() {

    private val api: IApi = Api()
    private var job: Job? = null

    private var lastRequestMs = 0L
    private var minIntervalMS = 500

    private val latinCyrillic = LatinCyrillicFactory.create(Alphabet.UkrainianIso9)

    override val inputText = MutableStateFlow("")
    override val outputTextCyrillic = MutableStateFlow("")
    override val outputTextLatin = MutableStateFlow("")
    override val inputLanguage = MutableStateFlow(Language.Czech)
    override val outputLanguage = MutableStateFlow(Language.Ukrainian)
    override val showCyrillic = MutableStateFlow(true)

    override fun setInputText(text: String) {
        inputText.value = text
        translate()
    }

    override fun setInputLanguage(language: Language) {
        inputLanguage.value = language
    }

    override fun swapLanguages() {
        val tmpInputLanguage = inputLanguage.value
        inputLanguage.value = outputLanguage.value
        outputLanguage.value = tmpInputLanguage

        outputTextCyrillic.value = ""
        outputTextLatin.value = ""
        translate()
    }

    override fun setShowCyrillic(showCyrilic: Boolean) {
        this.showCyrillic.value = showCyrilic
    }

    private fun translate() {
        if (inputText.value.isBlank()) {
            job?.cancel()
            outputTextCyrillic.value = ""
            outputTextLatin.value = ""
            return
        }

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastRequestMs < minIntervalMS) {
            return
        }

        job?.cancel()
        job = viewModelScope.launch {
            api.translate(
                inputLanguage = inputLanguage.value,
                outputLanguage = outputLanguage.value,
                text = inputText.value.trim()
            ).onSuccess {
                lastRequestMs = System.currentTimeMillis()
                when (outputLanguage.value) {
                    Language.Czech -> {
                        outputTextCyrillic.value = latinCyrillic.latinToCyrillic(it)
                        outputTextLatin.value = it
                    }
                    Language.Ukrainian -> {
                        outputTextCyrillic.value = it
                        outputTextLatin.value = latinCyrillic.cyrillicToLatin(it)
                    }
                }
            }.onFailure {
                logE("error $it")
            }
        }
    }
}