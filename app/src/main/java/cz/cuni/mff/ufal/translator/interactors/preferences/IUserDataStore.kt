package cz.cuni.mff.ufal.translator.interactors.preferences

import cz.cuni.mff.ufal.translator.interactors.preferences.data.DarkModeSetting
import kotlinx.coroutines.flow.Flow

/**
 * @author Tomas Krabac
 */
interface IUserDataStore {

    suspend fun setFinishedOnboarding()

    fun hasFinishedOnboarding(): Flow<Boolean>

    suspend fun saveAgreementDataCollection(agree: Boolean)

    fun agreeWithDataCollection(): Flow<Boolean>

    val useNetworkTTS: Flow<Boolean>

    suspend fun saveUseNetworkTTS(useOnlineVersion: Boolean)

    val ttsEngine: Flow<String>

    suspend fun saveTTSengine(engine: String)

    val organizationName: Flow<String>

    suspend fun saveOrganizationName(organizationName: String)

    val darkModeSetting: Flow<DarkModeSetting>

    suspend fun saveDarkModeSetting(darkModeSetting: DarkModeSetting)
}