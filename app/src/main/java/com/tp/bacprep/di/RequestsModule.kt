package com.tp.bacprep.di

import com.tp.bacprep.data.datasource.FbRequests
import com.tp.bacprep.data.repositories.RequestsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RequestsModule {

    @Provides
    fun provideFbRequests() : FbRequests{
        return FbRequests()
    }

    @Provides
    fun provideRequestsRepository(fbRequests : FbRequests) : RequestsRepository{
        return RequestsRepository(fbRequests)
    }
}