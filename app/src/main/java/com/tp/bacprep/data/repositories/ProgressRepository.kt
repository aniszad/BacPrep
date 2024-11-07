package com.tp.bacprep.data.repositories

import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.domain.models.ModuleProgress

class ProgressRepository(private val roomDb : RoomDb) {

    fun saveModuleProgress(moduleProgress : ModuleProgress){
        roomDb.moduleProgressDao.saveModuleProgress(moduleProgress)
    }

    fun getAllModulesProgress(branch: String): List<ModuleProgress>?{
        return roomDb.moduleProgressDao.getAllModulesProgress(branch)
    }

    fun getModuleProgress(moduleToGet: String, branchToGet: String) : ModuleProgress {
        return roomDb.moduleProgressDao.getModuleProgress(moduleToGet, branchToGet)
    }

    fun updateModuleProgress(moduleProgress: ModuleProgress) {
        roomDb.moduleProgressDao.updateModuleProgress(moduleProgress)
    }

}