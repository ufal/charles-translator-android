package cz.cuni.mff.ufal.translator.ui.settings.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author Tomas Krabac
 */
class PreviewSettingsViewModel : ISettingsViewModel {

    override val agreeWithDataCollection = MutableStateFlow(true)
    override val useNetworkTTS = MutableStateFlow(true)

    override fun saveAgreementDataCollection(agree: Boolean) {}
    override fun saveUseNetworkTTS(useOnlineVersion: Boolean) {}
}