package com.tp.bacprep.di

import android.content.Context
import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.data.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginVmModule {
    @Provides
    fun provideAuthRepository(fbAuth : FbAuth) : AuthRepository{
        return AuthRepository(fbAuth)
    }
    @Provides
    fun provideFbAuth(context : Context) : FbAuth{
        return FbAuth(context)
    }

}