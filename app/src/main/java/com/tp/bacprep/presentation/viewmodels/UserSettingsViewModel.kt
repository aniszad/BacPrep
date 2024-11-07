package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.data.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingsViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _signOutResult = MutableLiveData<Boolean>()
    val signOutResult : LiveData<Boolean> get() = _signOutResult

    private val _resetEmailSent = MutableLiveData<Pair<String?, Boolean>>()
    val resetEmailSent : LiveData<Pair<String?, Boolean>> get() = _resetEmailSent

    fun signOut()  = viewModelScope.launch(Dispatchers.IO) {
        _signOutResult.postValue(authRepository.logout())
    }

    fun sendResetPasswordEmail() = viewModelScope.launch(Dispatchers.IO) {
        _resetEmailSent.postValue(authRepository.sendPswResetEmail(null))
    }
}