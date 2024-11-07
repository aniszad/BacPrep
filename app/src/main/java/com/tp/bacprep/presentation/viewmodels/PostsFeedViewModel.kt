package com.tp.bacprep.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.tp.bacprep.data.repositories.PostsFeedRepository
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.domain.models.Bookmark
import com.tp.bacprep.domain.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsFeedViewModel @Inject constructor(private val postsFeedRepo : PostsFeedRepository,
private val roomDb: RoomDb) : ViewModel() {

    private var lastVisible : DocumentSnapshot? = null

    private val _postsBatch = MutableLiveData<List<DocumentSnapshot>?>()
    val postsBatch : LiveData<List<DocumentSnapshot>?> get() = _postsBatch

    private val _myPosts = MutableLiveData<Pair<String?, List<Post>?>>()
    val myPosts : LiveData<Pair<String?, List<Post>?>> get() = _myPosts

    private val _queryResults = MutableLiveData<List<DocumentSnapshot>?>()
    val queryResults : LiveData<List<DocumentSnapshot>?> get() = _queryResults

    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult : LiveData<Boolean> get() = _deleteResult

    val filtersList = mutableListOf<String>()


    private fun getLastVisible(): DocumentSnapshot? {
        return lastVisible
    }

    fun setLastVisible(documentSnapshot : DocumentSnapshot?){
        lastVisible = documentSnapshot
    }

    fun addToFiltersList(filter : String){
        filtersList.add(filter)
    }
    fun removeFromFiltersList(filter:String){
        filtersList.remove(filter)
    }

    fun getPostsBatch()  = viewModelScope.launch(Dispatchers.IO) {
        val docSnapshotList = postsFeedRepo.getPostsBatch(getLastVisible())
        _postsBatch.postValue(docSnapshotList)
    }

    fun getMyPosts() = viewModelScope.launch(Dispatchers.IO){
        _myPosts.postValue(postsFeedRepo.getMyPosts())
    }

    fun updateBookmark(bookmark: Bookmark)  = viewModelScope.launch(Dispatchers.IO) {
        if (roomDb.dao.doesItemExist(bookmark.id) == 0){
            roomDb.dao.addBookmark(bookmark)
        }else{
            roomDb.dao.deleteItemById(bookmark.id)
        }
    }

    fun inMyBookmarks(postId: String, callback : (Boolean) -> Unit) = viewModelScope.launch(Dispatchers.IO){
        callback(roomDb.dao.doesItemExist(postId) != 0)
    }

    fun getAllBookmarks(callback : (List<Bookmark>) -> Unit) = viewModelScope.launch(Dispatchers.IO){
        callback(roomDb.dao.getAllBookmarks())
    }

    fun getUserRole(userId: String, callback: (String?) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        postsFeedRepo.getUserRole(userId){  role ->
            callback(role)
        }
    }

    fun performQuery(queryString : String) = viewModelScope.launch(Dispatchers.IO) {
        _queryResults.postValue(postsFeedRepo.performQuery(queryString))
    }

    fun deletePost(ownerId: String, postId: String) = viewModelScope.launch (Dispatchers.IO){
        _deleteResult.postValue(postsFeedRepo.deleteResult(ownerId, postId))
    }

}