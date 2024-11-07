package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.domain.models.AudioFile
import com.tp.bacprep.data.repositories.AudioResourcesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioResourcesViewModel @Inject constructor(private val fbResourcesRepository: AudioResourcesRepository) : ViewModel() {

    private val _audioQueryResult = MutableLiveData<List<AudioFile>?>()
    val audioQueryResult : LiveData<List<AudioFile>?> get() = _audioQueryResult

    fun queryAudioResources(parentFolderId : String)  = viewModelScope.launch(Dispatchers.IO){
       _audioQueryResult.postValue(fbResourcesRepository.queryAudioResources(parentFolderId))
    }
}