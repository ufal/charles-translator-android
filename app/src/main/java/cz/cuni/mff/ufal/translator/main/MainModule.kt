package cz.cuni.mff.ufal.translator.main

import android.app.Application
import cz.cuni.mff.ufal.translator.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.preferences.UserDataStore
import cz.cuni.mff.ufal.translator.api.Api
import cz.cuni.mff.ufal.translator.api.IApi
import cz.cuni.mff.ufal.translator.db.Db
import cz.cuni.mff.ufal.translator.db.IDb
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