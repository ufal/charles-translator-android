package cz.cuni.mff.ufal.translator.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
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
) : ISettingsViewModel, ViewModel() {

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

    override val useNetworkTTS= userDataStore.useNetworkTTS.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        true,
    )

    override fun saveUseNetworkTTS(useOnlineVersion: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userDataStore.saveUseNetworkTTS(useOnlineVersion)
        }
    }
}