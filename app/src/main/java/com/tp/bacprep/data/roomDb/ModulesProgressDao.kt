package com.tp.bacprep.data.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tp.bacprep.domain.models.ModuleProgress

@Dao
interface ModulesProgressDao {

    @Insert
    fun saveModuleProgress(moduleProgress : ModuleProgress)

    @Update
    fun updateModuleProgress(moduleProgress: ModuleProgress)

    // get progress for a module
    @Query("SELECT * FROM modules_progress WHERE moduleName = :moduleToGet AND branch = :branchToGet")
    fun getModuleProgress(moduleToGet : String, branchToGet:String): ModuleProgress


    @Query("SELECT * FROM modules_progress WHERE branch = :branch")
    fun getAllModulesProgress(branch: String): List<ModuleProgress>?


}