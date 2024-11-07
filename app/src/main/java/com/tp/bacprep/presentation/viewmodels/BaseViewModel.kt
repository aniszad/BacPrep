package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.tp.bacprep.data.repositories.AuthRepository
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(private val authRepository: AuthRepository,private val roomDb: RoomDb) : ViewModel() {

     fun getName(callback: (String) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        authRepository.getUserName() { userName ->
            callback(userName)
        }
    }

    fun getCurrentUser(userId : String, callback: (User?) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        callback(roomDb.currentUserDao.getCurrentUser(userId))
    }
}