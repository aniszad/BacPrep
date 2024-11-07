package com.tp.bacprep.data.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tp.bacprep.domain.models.FileSystemItem
import com.tp.bacprep.domain.models.RecentOpenedFile

@Dao
interface RecentFilesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToRecentFiles(file : RecentOpenedFile)

    @Query("SELECT * FROM recent_opened_files")
    fun getRecentFiles() : List<RecentOpenedFile>

    @Query("DELETE FROM recent_opened_files WHERE name= :name")
    fun deleteLastSaved(name: String)


}