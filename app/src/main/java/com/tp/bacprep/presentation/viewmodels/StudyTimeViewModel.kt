package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.data.repositories.PomodoroTimerRepository
import com.tp.bacprep.domain.models.StudyTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyTimeViewModel @Inject constructor(private val pomodoroTimerRepo: PomodoroTimerRepository) : ViewModel() {

    fun getGetMonthTimeData(month : Int, year : Int, callback : (List<StudyTime>?) -> Unit) = viewModelScope.launch(Dispatchers.IO){
        callback(pomodoroTimerRepo.getMonthStudyTime(month, year))
    }
}