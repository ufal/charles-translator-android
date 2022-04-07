package cz.cuni.mff.ufal.translator.main

import android.app.Application
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.interactors.preferences.UserDataStore
import cz.cuni.mff.ufal.translator.interactors.api.Api
import cz.cuni.mff.ufal.translator.interactors.api.IApi
import cz.cuni.mff.ufal.translator.interactors.db.Db
import cz.cuni.mff.ufal.translator.interactors.db.IDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Tomas Krabac
 */
@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideApi(impl: Api): IApi = impl

    @Singleton
    @Provides
    fun provideDb(context: Application): IDb {
        return Db(context)
    }

    @Singleton
    @Provides
    fun provideUserDataStore(context: Application): IUserDataStore {
        return UserDataStore(context)
    }
}