package com.tp.bacprep.data.repositories

import com.tp.bacprep.data.datasource.DriveStorageFiles
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.domain.models.FileDriveItem
import com.tp.bacprep.domain.models.RecentOpenedFile

class DriveStorageRepository(private val driveStorageFiles : DriveStorageFiles, private val roomDb: RoomDb) {

    suspend fun queryDriveFiles(path : String): List<FileDriveItem>? {
        return driveStorageFiles.queryDriveFiles(path)
    }

    fun addToRecentFiles(recentOpenedFile: RecentOpenedFile) {
        roomDb.recentFilesDao.addToRecentFiles(recentOpenedFile)
    }

    fun getRecentFiles(callback : (List<RecentOpenedFile>) ->Unit ) {
        callback(roomDb.recentFilesDao.getRecentFiles())
    }

    fun deleteLastSaved(name: String) {
        roomDb.recentFilesDao.deleteLastSaved(name)
    }

}