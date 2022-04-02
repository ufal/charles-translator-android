package cz.cuni.mff.lindat.main

import cz.cuni.mff.lindat.api.Api
import cz.cuni.mff.lindat.api.IApi
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
}