package cz.cuni.mff.ufal.translator.interactors.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

/**
 * @author Tomas Krabac
 */

class UserDataStore(private val context: Context) : IUserDataStore {

    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

    companion object {
        val HAS_FINISHED_ONBOARDING = booleanPreferencesKey("HAS_FINISHED_ONBOARDING")
        val AGREE_WITH_DATA_COLLECTION = booleanPreferencesKey("AGREE_WITH_DATA_COLLECTION")
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
}
