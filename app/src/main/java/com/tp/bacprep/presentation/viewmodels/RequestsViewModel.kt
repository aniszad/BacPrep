package com.tp.bacprep.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.tp.bacprep.data.repositories.AuthRepository
import com.tp.bacprep.data.repositories.PostsFeedRepository
import com.tp.bacprep.data.repositories.RequestsRepository
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.domain.models.Bookmark
import com.tp.bacprep.domain.models.Request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(private val requestsRepository: RequestsRepository, authRepository: AuthRepository,
                                            private val postsFeedRepository: PostsFeedRepository, private val roomDb: RoomDb
): BaseViewModel(authRepository, roomDb) {

    private var lastAnswerPostVisible : DocumentSnapshot? = null

    private var lastRequestVisible : DocumentSnapshot? = null

    private val _requestAnswersBath = MutableLiveData<List<DocumentSnapshot>?>()
    val requestAnswersBath : LiveData<List<DocumentSnapshot>?> get() = _requestAnswersBath

    private val _requestsDateQueryResults = MutableLiveData<List<DocumentSnapshot>?>()
    val requestsDateQueryResults : LiveData<List<DocumentSnapshot>?> get() = _requestsDateQueryResults

    private val _allRequestRetrievedCurrently = MutableLiveData<List<Request>?>()
    val allRequestRetrievedCurrently : LiveData<List<Request>?> get() = _allRequestRetrievedCurrently

    private val _addRequestStatus = MutableLiveData<Pair<Boolean, Request>>()
    val addRequestStatus : LiveData<Pair<Boolean, Request>>get() = _addRequestStatus

    private val _allRequestsList = MutableLiveData<List<Request>?>()
    val allRequestsList : LiveData<List<Request>?>  get() = _allRequestsList

    private val _myRequestsList = MutableLiveData<List<Request>?>()
    val myRequestsList : LiveData<List<Request>?>  get() = _myRequestsList

    private val _deleteSuccessful = MutableLiveData<Boolean>()
    val deleteSuccessful : LiveData<Boolean>  get() = _deleteSuccessful


    fun addRequest(request : Request) = viewModelScope.launch {
        _addRequestStatus.postValue(Pair(requestsRepository.addRequest(request), request))
    }

    fun getAllRequest() = viewModelScope.launch {
        _allRequestsList.postValue(requestsRepository.getAllRequests())
    }

    fun getMyRequests(context : Context)  = viewModelScope.launch(Dispatchers.IO){
        _myRequestsList.postValue(requestsRepository.getMyRequests(context))
    }

    fun deleteRequest(requestId: String) = viewModelScope.launch(Dispatchers.IO) {
        _deleteSuccessful.postValue(requestsRepository.deleteRequest(requestId))
    }


    fun inMyBookmarks(postId: String, callback : (Boolean) -> Unit) = viewModelScope.launch(Dispatchers.IO){
        callback(roomDb.dao.doesItemExist(postId) != 0)
    }

    fun updateBookmark(bookmark: Bookmark)  = viewModelScope.launch(Dispatchers.IO) {
        if (roomDb.dao.doesItemExist(bookmark.id) == 0){
            roomDb.dao.addBookmark(bookmark)
        }else{
            roomDb.dao.deleteItemById(bookmark.id)
        }
    }

    fun getNextRequestAnswersBatch(requestOwnerId : String)  = viewModelScope.launch(Dispatchers.IO){
        val docSnapshotList = postsFeedRepository.getNextRequestAnswersBatch(getLastPostVisible(), requestOwnerId)
        _requestAnswersBath.postValue(docSnapshotList)
        if (!docSnapshotList.isNullOrEmpty()){
            setLastPostVisible(docSnapshotList.lastOrNull())
        }
    }
    private fun getLastPostVisible(): DocumentSnapshot? {
        return lastAnswerPostVisible
    }

    fun setLastPostVisible(documentSnapshot : DocumentSnapshot?){
        lastAnswerPostVisible = documentSnapshot
    }
    private fun getLastRequestVisible(): DocumentSnapshot? {
        return lastRequestVisible
    }

    fun setLastRequestVisible(documentSnapshot : DocumentSnapshot?){
        lastRequestVisible = documentSnapshot
    }

    fun queryRequest(startDate: Timestamp?, endDate: Timestamp?)  = viewModelScope.launch(Dispatchers.IO){
        _requestsDateQueryResults.postValue(requestsRepository.queryRequests(startDate, endDate, getLastRequestVisible()))
    }

    fun newRequest(startDate: Timestamp?, endDate: Timestamp?) = viewModelScope.launch(Dispatchers.IO){
        _requestsDateQueryResults.postValue(requestsRepository.queryRequests(startDate, endDate, getLastRequestVisible()))
    }

    fun setAllRequestsRetrievedCurrently(allRequestsRetrievedCurrently : List<Request>){
        _allRequestRetrievedCurrently.postValue(allRequestsRetrievedCurrently)
    }

}