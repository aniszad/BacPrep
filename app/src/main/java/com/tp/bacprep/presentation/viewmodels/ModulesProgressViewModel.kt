package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.data.repositories.AuthRepository
import com.tp.bacprep.data.repositories.ProgressRepository
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModulesProgressViewModel @Inject constructor(private val progressRepo : ProgressRepository, private val authRepo: AuthRepository) : ViewModel() {
    private val _modulesProgressList = MutableLiveData<List<ModuleProgress>?>()
    val modulesProgressList : LiveData<List<ModuleProgress>?> get() = _modulesProgressList

    private val _moduleProgress = MutableLiveData<ModuleProgress>()
    val moduleProgress : LiveData<ModuleProgress> get() = _moduleProgress



    fun getModuleProgress(moduleToGet : String, branchToGet : String, callback : (ModuleProgress?) -> Unit) = viewModelScope.launch(Dispatchers.IO){
        callback(progressRepo.getModuleProgress(moduleToGet, branchToGet))
    }

    fun getMyModulesProgress(branch : String,callback: (List<ModuleProgress>?) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        callback(progressRepo.getAllModulesProgress(branch))
    }

    fun saveModuleProgress(moduleProgress: ModuleProgress) = viewModelScope.launch(Dispatchers.IO){
        progressRepo.saveModuleProgress(moduleProgress)
    }

    fun updateModuleProgress(moduleProgress: ModuleProgress) = viewModelScope.launch(Dispatchers.IO) {
        progressRepo.updateModuleProgress(moduleProgress)
    }

    fun getMyBranch(callback: (User?) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        callback(authRepo.getMyBranch())
    }

}