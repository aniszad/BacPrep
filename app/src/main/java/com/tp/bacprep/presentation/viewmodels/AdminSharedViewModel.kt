package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.data.repositories.AuthRepository
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminSharedViewModel @Inject constructor(private val authRepository: AuthRepository, private val roomDb: RoomDb) : ViewModel() {

    private val _loggedOut = MutableLiveData<Boolean>()
    val loggedOut : LiveData<Boolean> get() = _loggedOut

    private val _resetEmailSent = MutableLiveData<Pair<String?, Boolean>>()
    val resetEmailSent : LiveData<Pair<String?, Boolean>> get() = _resetEmailSent


    fun getName(callback: (String) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        authRepository.getUserName() { userName ->
            callback(userName)
        }
    }

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        _loggedOut.postValue(authRepository.logout())
    }

    fun getCurrentUser(id : String, callback :(User?) ->Unit) = viewModelScope.launch(Dispatchers.IO) {
        callback(roomDb.currentUserDao.getCurrentUser(id))
    }

    fun sendResetPasswordEmail() = viewModelScope.launch(Dispatchers.IO) {
        _resetEmailSent.postValue(authRepository.sendPswResetEmail(null))
    }
}