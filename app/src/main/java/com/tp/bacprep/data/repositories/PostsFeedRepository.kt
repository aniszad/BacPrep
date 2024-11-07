package com.tp.bacprep.data.repositories

import com.google.firebase.firestore.DocumentSnapshot
import com.tp.bacprep.data.datasource.FbPosts
import com.tp.bacprep.domain.models.Post

class PostsFeedRepository(private val fbPosts: FbPosts) {
    suspend fun getPostsBatch(lastVisiblePost : DocumentSnapshot?) : List<DocumentSnapshot>?{
        return fbPosts.getPostsBatch(lastVisiblePost)
    }

    suspend fun getLatestPosts() : Triple<String?, Boolean, List<DocumentSnapshot>?>{
        return fbPosts.getLatestPosts()
    }

    suspend fun getNextRequestAnswersBatch(lastVisiblePost : DocumentSnapshot?, requestOwnerId: String) : List<DocumentSnapshot>?{
        return fbPosts.getRequestAnswersBatch(lastVisiblePost, requestOwnerId)
    }















    suspend fun getPastMonthPosts() : Triple<String?, Boolean, List<Post>?>{
        return fbPosts.getPastMonthPosts()
    }

    suspend fun updateLike(postId: String, userId: String, postPosition : Int) : Triple<String,Int ,Int> {
         return fbPosts.updateLike(postId, userId, postPosition)
    }

    suspend fun iLikePost(postId: String, callback: (Boolean?) -> Unit) {
        fbPosts.iLikePost(postId){iLikeIt ->
            callback(iLikeIt)
        }
    }

    suspend fun getLikesNum(postId: String) : Int {
        return fbPosts.getLikesNum(postId)
    }

    suspend fun downloadFile(downloadUrl: String, fileName: String) {
        fbPosts.downloadFile(downloadUrl, fileName)
    }

    suspend fun iBookmarkedThisPost(ownerId:String, callback: (Boolean?) -> Unit) {
        fbPosts.iBookmarkedThisPost(ownerId){ iBookmarkedThisPost ->
            callback(iBookmarkedThisPost)
        }
    }

    suspend fun getUserRole(userId : String, callback: (String?) -> Unit) {
        fbPosts.getUserRole(userId){    role ->
            callback(role)
        }
    }

    suspend fun getMyPosts(): Pair<String?, List<Post>?> {
        return fbPosts.getMyPosts()
    }

    suspend fun performQuery(queryString: String): List<DocumentSnapshot> {
        return fbPosts.performQuery(queryString)
    }

    suspend fun getAnswersForRequest(requestId: String): List<DocumentSnapshot> {
        return fbPosts.getAnswersForRequest(requestId)
    }

    suspend fun deleteResult(ownerId: String, postId: String): Boolean? {
        return fbPosts.delete(ownerId, postId)
    }
}