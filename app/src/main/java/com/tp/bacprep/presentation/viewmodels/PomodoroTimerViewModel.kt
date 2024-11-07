package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.data.repositories.PomodoroTimerRepository
import com.tp.bacprep.domain.models.StudyTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PomodoroTimerViewModel @Inject constructor(private val pomodoroTimerRepository: PomodoroTimerRepository) : ViewModel() {
    fun saveTimeInRoomDb(studyTime: StudyTime) = viewModelScope.launch(Dispatchers.IO){
        pomodoroTimerRepository.saveTimeInRoomDb(studyTime)
    }

    fun getStudyTime(day: Int, month: Int, year: Int, callback : (StudyTime?) -> Unit)  = viewModelScope.launch(Dispatchers.IO){
        callback(pomodoroTimerRepository.getStudyTime(day, month, year))
    }

    fun updateStudyTimeInRoomDb(studyTime: StudyTime) = viewModelScope.launch(Dispatchers.IO){
        pomodoroTimerRepository.updateStudyTime(studyTime)
    }
}