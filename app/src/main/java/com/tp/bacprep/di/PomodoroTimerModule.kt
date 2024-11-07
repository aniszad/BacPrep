package com.tp.bacprep.di

import com.tp.bacprep.data.repositories.PomodoroTimerRepository
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.presentation.viewmodels.PomodoroTimerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PomodoroTimerModule {

    @Provides
    fun providesTimerRepo(roomDb: RoomDb) : PomodoroTimerRepository{
        return PomodoroTimerRepository(roomDb)
    }

    @Provides
    fun pomodoroTimerViewModel(pomodoroTimerRepo: PomodoroTimerRepository) : PomodoroTimerViewModel{
        return PomodoroTimerViewModel(pomodoroTimerRepo)
    }
}