package cz.cuni.mff.ufal.translator.ui.settings.viewmodel

import android.speech.tts.TextToSpeech
import cz.cuni.mff.ufal.translator.base.IBaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Tomas Krabac
 */
interface ISettingsViewModel: IBaseViewModel {

    val agreeWithDataCollection: StateFlow<Boolean>

    fun saveAgreementDataCollection(agree: Boolean)

    val useNetworkTTS: StateFlow<Boolean>

    fun saveUseNetworkTTS(useOnlineVersion: Boolean)

    val engines: StateFlow<List<TextToSpeech.EngineInfo>>

    val selectedTtsEngine: StateFlow<String>

    fun saveTTSengine(engine: String)

    val organizationName: StateFlow<String>

    fun saveOrganizationName(organizationName: String)

    val isExperimentalDarkMode: StateFlow<Boolean>

    fun saveExperimentalDarkMode(isDarkModeExperimental: Boolean)

}