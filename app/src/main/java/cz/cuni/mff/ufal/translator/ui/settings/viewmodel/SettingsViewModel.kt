package cz.cuni.mff.ufal.translator.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.interactors.tts.ITextToSpeechWrapper
import cz.cuni.mff.ufal.translator.interactors.tts.TextToSpeechWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataStore: IUserDataStore,
    private val textToSpeech: ITextToSpeechWrapper,
) : ISettingsViewModel, ViewModel() {

    override fun onStart() {
        super.onStart()

        viewModelScope.launch {
            textToSpeech.init()
        }
    }

    override fun onStop() {
        super.onStop()

        textToSpeech.stop()
    }

    override fun onCleared() {
        super.onCleared()

        textToSpeech.shutdown()
    }

    override val agreeWithDataCollection = userDataStore.agreeWithDataCollection().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        true,
    )

    override fun saveAgreementDataCollection(agree: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.saveAgreementDataCollection(agree)
        }
    }

    override val useNetworkTTS = userDataStore.useNetworkTTS.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        true,
    )

    override fun saveUseNetworkTTS(useOnlineVersion: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.saveUseNetworkTTS(useOnlineVersion)
        }
    }

    override val engines = textToSpeech.engines

    override val selectedTtsEngine = userDataStore.ttsEngine.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        TextToSpeechWrapper.DEFAULT_TTS_ENGINE,
    )

    override fun saveTTSengine(engine: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.saveTTSengine(engine)
        }
    }

    override val organizationName = userDataStore.organizationName.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        "",
    )

    override fun saveOrganizationName(organizationName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.saveOrganizationName(organizationName)
        }
    }
}