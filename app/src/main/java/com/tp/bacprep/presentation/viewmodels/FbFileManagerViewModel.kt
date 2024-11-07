package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.data.repositories.DriveStorageRepository
import com.tp.bacprep.domain.models.FileDriveItem
import com.tp.bacprep.domain.models.RecentOpenedFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FbFileManagerViewModel @Inject constructor(private var fbStorageRepo: DriveStorageRepository) : ViewModel(){

    private val _driveFilesList = MutableLiveData<List<FileDriveItem>>()
    val driveFilesList : LiveData<List<FileDriveItem>> get() = _driveFilesList

    fun queryDriveFiles(path : String)  = viewModelScope.launch(Dispatchers.IO){
        _driveFilesList.postValue(fbStorageRepo.queryDriveFiles(path))
    }

    fun addToRecentFiles(file:RecentOpenedFile) = viewModelScope.launch(Dispatchers.IO) {
        fbStorageRepo.addToRecentFiles(file)
    }
}