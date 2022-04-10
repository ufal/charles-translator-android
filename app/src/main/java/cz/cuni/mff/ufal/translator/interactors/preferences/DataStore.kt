package cz.cuni.mff.ufal.translator.interactors.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import cz.cuni.mff.ufal.translator.interactors.tts.TextToSpeechWrapper.Companion.DEFAULT_TTS_ENGINE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author Tomas Krabac
 */

class UserDataStore(private val context: Context) : IUserDataStore {

    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

    companion object {
        val HAS_FINISHED_ONBOARDING = booleanPreferencesKey("HAS_FINISHED_ONBOARDING")
        val AGREE_WITH_DATA_COLLECTION = booleanPreferencesKey("AGREE_WITH_DATA_COLLECTION")
        val USE_NETWORK_TTS = booleanPreferencesKey("USE_NETWORK_TTS")
        val TTS_ENGINE = stringPreferencesKey("TTS_ENGINE")
    }

    override suspend fun setFinishedOnboarding() {
        context.userDataStore.edit {
            it[HAS_FINISHED_ONBOARDING] = true
        }
    }

    override fun hasFinishedOnboarding() = context.userDataStore.data.map {
        it[HAS_FINISHED_ONBOARDING] ?: false
    }

    override suspend fun saveAgreementDataCollection(agree: Boolean) {
        context.userDataStore.edit {
            it[AGREE_WITH_DATA_COLLECTION] = agree
        }
    }

    override fun agreeWithDataCollection() = context.userDataStore.data.map {
        it[AGREE_WITH_DATA_COLLECTION] ?: false
    }

    override val useNetworkTTS = context.userDataStore.data.map {
        it[USE_NETWORK_TTS] ?: true
    }

    override suspend fun saveUseNetworkTTS(useOnlineVersion: Boolean) {
        context.userDataStore.edit {
            it[USE_NETWORK_TTS] = useOnlineVersion
        }
    }

    override val ttsEngine = context.userDataStore.data.map {
        it[TTS_ENGINE] ?: DEFAULT_TTS_ENGINE
    }

    override suspend fun saveTTSengine(engine: String) {
        context.userDataStore.edit {
            it[TTS_ENGINE] = engine
        }
    }
}
