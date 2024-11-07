package com.tp.bacprep.di

import com.tp.bacprep.data.datasource.GoogleDriveAudioResources
import com.tp.bacprep.data.repositories.AudioResourcesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AudioResourcesModule {

    @Provides
    fun providesFbAudioResources() : GoogleDriveAudioResources{
        return GoogleDriveAudioResources()
    }

    @Provides
    fun providesAudioResourcesRepository(googleDriveAudioResources: GoogleDriveAudioResources) : AudioResourcesRepository{
        return AudioResourcesRepository(googleDriveAudioResources)
    }

}