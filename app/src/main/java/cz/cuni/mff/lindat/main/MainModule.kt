package cz.cuni.mff.lindat.main

import android.app.Application

import cz.cuni.mff.lindat.api.Api
import cz.cuni.mff.lindat.api.IApi
import cz.cuni.mff.lindat.db.Db
import cz.cuni.mff.lindat.db.IDb
import cz.cuni.mff.lindat.preferences.IUserDataStore
import cz.cuni.mff.lindat.preferences.UserDataStore
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