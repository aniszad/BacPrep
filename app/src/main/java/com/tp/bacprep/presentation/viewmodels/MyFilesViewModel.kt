package com.tp.bacprep.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.data.repositories.DriveStorageRepository
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.domain.models.RecentOpenedFile
import com.tp.bacprep.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MyFilesViewModel @Inject constructor(private val driveStorageRepository: DriveStorageRepository,
 private val roomDb: RoomDb) : ViewModel() {

    private val _downloadsCountAndSize = MutableLiveData<Pair<Int, Long>>()
    val downloadsCountAndSize : LiveData<Pair<Int, Long>> get() = _downloadsCountAndSize

    private val _bookmarksCount = MutableLiveData<Int>()
    val bookmarksCount : LiveData<Int> get() = _bookmarksCount

    private val _subjectsBankCountAndSize = MutableLiveData<Pair<Int, Long>>()
    val subjectsBankCountAndSize : LiveData<Pair<Int, Long>> get() = _subjectsBankCountAndSize


    fun deleteLastSaved(name: String) = viewModelScope.launch(Dispatchers.IO) {
        driveStorageRepository.deleteLastSaved(name)
    }

    fun getMyRecentFiles(callback: (MutableList<RecentOpenedFile>) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            driveStorageRepository.getRecentFiles { recentFilesList ->
                callback(recentFilesList.toMutableList())
            }
        }

    fun getDownloadedFileCountAndSize(localDirectoryPath : String) {
        try {
            val directoriesPaths = listOf(
                buildString {
                append(localDirectoryPath)
                append("/")
                append(Constants.DOWNLOADS_FOLDER_NAME)
            },
            buildString {
                append(localDirectoryPath)
                append("/")
                append(Constants.DOWNLOADS_FOLDER_NAME)
                append("/")
                append(Constants.FILES_BANK)
            }
            )


            var overAllSize = 0L
            var filesNum = 0
            for (directoryPath in directoriesPaths){
                val directory = File(directoryPath)
                if (directory.exists() && directory.isDirectory) {
                    val files = directory.listFiles()
                    files?.forEach { file ->
                        if (file.isFile) {
                            filesNum++
                            overAllSize+=file.length()
                        }
                    }
                }
            }

            _downloadsCountAndSize.postValue(Pair(filesNum, overAllSize))
        }catch (e : Exception){
            Log.e("listing file error", e.message.toString())
        }
    }
    fun getSubjectsBankCountAndSize(localDirectoryPath : String) {
        try {
            val directoryPath = buildString {
                append(localDirectoryPath)
                append("/")
                append(Constants.DOWNLOADS_FOLDER_NAME)
                append("/")
                append("subjects bank")
            }
            var overAllSize = 0L
            var filesNum = 0
            val directory = File(directoryPath)
            if (directory.exists() && directory.isDirectory) {
                val files = directory.listFiles()
                files?.forEach { file ->
                    if (file.isFile) {
                        filesNum++
                        overAllSize+=file.length()
                    }
                }
            }
            _subjectsBankCountAndSize.postValue(Pair(filesNum, overAllSize))
        }catch (e : Exception){
            Log.e("listing file error", e.message.toString())
        }
    }

    fun getBookmarksCount() = viewModelScope.launch(Dispatchers.IO) {
        _bookmarksCount.postValue(roomDb.dao.getAllBookmarks().size)
    }

}