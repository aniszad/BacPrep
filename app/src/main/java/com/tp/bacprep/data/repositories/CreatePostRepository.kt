package com.tp.bacprep.data.repositories

import android.net.Uri
import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.data.datasource.FbPosts
import com.tp.bacprep.domain.models.Post

class CreatePostRepository(private val fbPosts: FbPosts, private val fbAuth: FbAuth) {

    fun getUserId(): String{
        return fbAuth.getUserId()
    }

    suspend fun publishPost(
        post: Post,
        attachmentsUriList: MutableList<Pair<Uri, String>>,
        progressCallback: (Int) -> Unit
    ): Pair<String?, Boolean> {
        return fbPosts.publishPost(post, attachmentsUriList,progressCallback)
    }

    suspend fun getUserName(callback : (Pair<Boolean, String?> ) -> Unit){
        return fbAuth.getUserName(){result ->
            callback(result)
        }
    }

    suspend fun getAdminName(callback : (Pair<Boolean, String?> ) -> Unit){
        return fbAuth.getAdminName(){result ->
            callback(result)
        }
    }
}