package com.tp.bacprep.di

import android.content.Context
import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.data.repositories.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterVmModule {

    @Provides
    @ApplicationContext
    fun provideFbAuth(context : Context) : FbAuth{
        return FbAuth(context)
    }

    @Provides
    fun provideRegisterRepository(fbAuth: FbAuth) : RegisterRepository{
        return RegisterRepository(fbAuth)
    }
}