package com.tp.bacprep.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.data.repositories.CreatePostRepository
import com.tp.bacprep.domain.models.Post
import com.tp.bacprep.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(private val createPostRepo : CreatePostRepository) : ViewModel() {

    private val _publishPostResult = SingleLiveEvent<Pair<String?, Boolean>>()
    val publishPostResult : LiveData<Pair<String?, Boolean>> get() = _publishPostResult

    private val _flairsList = MutableLiveData<MutableList<String>?>()
    val flairList : LiveData<MutableList<String>?> get() = _flairsList

    private val _attachList = MutableLiveData<MutableList<Pair<Uri, String>>>()
    val attachList : LiveData<MutableList<Pair<Uri, String>>> get() = _attachList

    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> get() = _userName


    private val _uploadProgress = SingleLiveEvent<Int>()
    val uploadProgress : LiveData<Int> get() = _uploadProgress

    fun publishPost(post: Post, attachmentsUriList: MutableList<Pair<Uri, String>>) = viewModelScope.launch(Dispatchers.IO) {
        _publishPostResult.postValue(createPostRepo.publishPost(post, attachmentsUriList){progress ->
            _uploadProgress.postValue(progress)
        })
    }


    fun setFlairList(flairList : MutableList<String>)  {
        _flairsList.postValue(flairList)
    }

    fun getUserId()  : String {
        return createPostRepo.getUserId()
    }

    fun getAdminName(callback : (String) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
         createPostRepo.getAdminName(){result ->
             if (result.second != null){
                 callback(result.second!!)
                 _userName.postValue(result.second!!)
             }
         }
    }

    fun clearFlairList() {
        _flairsList.postValue(null)
    }


    fun addToAttachList(attach : Pair<Uri, String>){
        val currentList = _attachList.value ?: mutableListOf()
        currentList.add(attach)
        _attachList.value = currentList
    }
    fun removeFromAttachList(attach : String){
        _attachList.value?.removeIf { it.second == attach }
    }


}