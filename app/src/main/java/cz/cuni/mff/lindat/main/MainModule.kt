package cz.cuni.mff.lindat.main

import android.app.Application
import cz.cuni.mff.lindat.api.Api
import cz.cuni.mff.lindat.api.IApi
import cz.cuni.mff.lindat.db.Db
import cz.cuni.mff.lindat.db.IDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Tomas Krabac
 */
@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun provideApi(): IApi {
        return Api()
    }

    @Provides
    fun provideDb(context: Application): IDb {
        return Db(context)
    }
}