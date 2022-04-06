package cz.cuni.mff.lindat.preferences

import kotlinx.coroutines.flow.Flow

/**
 * @author Tomas Krabac
 */
interface IUserDataStore {

    suspend fun setFinishedOnboarding()

    fun hasFinishedOnboarding(): Flow<Boolean>

    suspend fun saveAgreementDataCollection(agree: Boolean)

    fun agreeWithDataCollection(): Flow<Boolean>
}