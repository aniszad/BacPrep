package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.data.repositories.AuthRepository
import com.tp.bacprep.data.repositories.RegisterRepository
import com.tp.bacprep.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository,
 private val authRepository: AuthRepository) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> get() = _email

    private val _name = MutableLiveData<String>()
    val name : LiveData<String> get() = _name

    private val _registerUserResult = MutableLiveData<Pair<String?, Boolean>>()
    val registerUserResult: LiveData<Pair<String?, Boolean>> get() = _registerUserResult

    private val _registerAdminResult = MutableLiveData<Pair<String?, Boolean>>()
    val registerAdminResult: LiveData<Pair<String?, Boolean>> get() = _registerAdminResult

    private val _verifyEmailSentResult = MutableLiveData<Pair<String?, Boolean>>()
    val verifyEmailSentResult: LiveData<Pair<String?, Boolean>> get() = _verifyEmailSentResult

    private val _isCurrentUserVerified = MutableLiveData<Pair<String?, Boolean>>()
    val isCurrentUserVerified: LiveData<Pair<String?, Boolean>> get() = _isCurrentUserVerified


    fun registerUser(user : User, psw : String) = viewModelScope.launch(Dispatchers.IO){
        _registerUserResult.postValue(repository.registerUser(user, psw))
    }
    fun registerAdmin(user : User, psw : String) = viewModelScope.launch(Dispatchers.IO){
        _registerAdminResult.postValue(repository.registerAdmin(user, psw))
    }

    fun sendVerificationEmail() = viewModelScope.launch (Dispatchers.IO){
        _verifyEmailSentResult.postValue(repository.sendVerificationEmail())
    }

    fun isCurrentUserVerified() = viewModelScope.launch (Dispatchers.IO){
        _isCurrentUserVerified.postValue(repository.isCurrentUserVerified())
    }

    fun setEmail(email : String){
        _email.postValue(email)
    }

    fun signOut() = viewModelScope.launch(Dispatchers.IO){
        authRepository.logout()
    }
}