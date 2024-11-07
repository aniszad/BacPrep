package com.tp.bacprep.di

import com.tp.bacprep.data.datasource.DriveStorageFiles
import com.tp.bacprep.data.repositories.DriveStorageRepository
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.presentation.viewmodels.FbFileManagerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object FileManagerFirebaseModule {

    @Provides
    fun providesFbStorageFiles() : DriveStorageFiles{
        return DriveStorageFiles()
    }

    @Provides
    fun providesFbStorageRepo(driveStorageFiles: DriveStorageFiles, roomDb: RoomDb) : DriveStorageRepository{
        return DriveStorageRepository(driveStorageFiles, roomDb)
    }

    @Provides
    fun providesFbFileManagerViewModel(fbStorageFilesRepo : DriveStorageRepository) : FbFileManagerViewModel{
        return FbFileManagerViewModel(fbStorageFilesRepo)
    }



}