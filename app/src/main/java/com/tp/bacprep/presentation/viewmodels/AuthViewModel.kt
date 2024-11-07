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
class AuthViewModel @Inject constructor(private val authRepo : AuthRepository,private val roomDb: RoomDb) : ViewModel() {

    private val _pswResetEmailResult = MutableLiveData<Pair<String?, Boolean>>()
    val pswResetEmailResult : LiveData<Pair<String?, Boolean>> get() = _pswResetEmailResult

    private val _signInResult = MutableLiveData<Pair<String?, User?>>()
    val signInResult : LiveData<Pair<String?, User?>> get() = _signInResult

     fun sendPswResetEmail(email : String) = viewModelScope.launch(Dispatchers.IO){
         _pswResetEmailResult.postValue(authRepo.sendPswResetEmail(email))
    }

    fun signIn(email : String, psw : String) = viewModelScope.launch(Dispatchers.IO){
         _signInResult.postValue(authRepo.signIn(email, psw))
    }
    fun getUserId()  : String {
        return authRepo.getUserId()
    }

    fun isUserSignedIn(callback: (Pair<String, Boolean>?) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        callback(authRepo.isUserSignedIn())
    }

    fun saveUserInRoomDb(user:User) = viewModelScope.launch(Dispatchers.IO){
        roomDb.currentUserDao.saveCurrentUser(user)
    }

    fun sendEmailVerification() = viewModelScope.launch(Dispatchers.IO) {
        authRepo.sendVerificationEmail()
    }
}