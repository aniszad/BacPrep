package com.tp.bacprep.di

import android.content.Context
import androidx.room.Room
import com.tp.bacprep.data.repositories.ProgressRepository
import com.tp.bacprep.data.roomDb.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ModulesProgressModule {

    @Provides
    fun providesProgressRepository(roomDb: RoomDb) : ProgressRepository{
        return ProgressRepository(roomDb)
    }
}