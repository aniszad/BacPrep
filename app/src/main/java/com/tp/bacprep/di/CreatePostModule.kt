package com.tp.bacprep.di

import android.content.Context
import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.data.datasource.FbPosts
import com.tp.bacprep.data.repositories.CreatePostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CreatePostModule {

    @Provides
    fun provideContext(@ApplicationContext appContext: Context) : Context{
        return appContext
    }

    @Provides
    fun provideFbPosts(context : Context) : FbPosts{
        return FbPosts(context)
    }


    @Provides
    fun provideCreatePostRepository(fbPosts: FbPosts, fbAuth: FbAuth): CreatePostRepository {
        return CreatePostRepository(fbPosts, fbAuth)
    }
}