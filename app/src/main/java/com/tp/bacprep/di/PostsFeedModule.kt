package com.tp.bacprep.di

import android.content.Context
import androidx.room.Room
import com.tp.bacprep.data.datasource.FbPosts
import com.tp.bacprep.data.repositories.PostsFeedRepository
import com.tp.bacprep.data.roomDb.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PostsFeedModule {
    @Provides
    fun providePostsFeedRepository(fbPosts: FbPosts) : PostsFeedRepository{
        return PostsFeedRepository(fbPosts)
    }

    @Provides
    fun provideAppDatabase(context: Context): RoomDb { // Replace with your actual Room database class
        return Room.databaseBuilder(
            context.applicationContext,
            RoomDb::class.java, // Replace with your actual Room database class
            "local_room_database" // Replace with your desired database name
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}
