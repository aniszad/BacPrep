package com.tp.bacprep.util

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.tp.bacprep.domain.models.Post
import com.tp.bacprep.domain.models.Request

class DocumentSnapshotConverter {

    fun mapDocSnapshotsToPostList(docSnapshotsList : List<DocumentSnapshot>?) : List<Post> {
        return if (docSnapshotsList != null) {
            val postsList: List<Post> = docSnapshotsList.map { document ->
                val data = document.data!!
                val id: String = data["id"] as String
                val ownerId: String = data["ownerId"] as String
                val ownerName: String = data["ownerFullName"] as String
                val title: String = data["title"] as String
                val content: String = data["content"] as String
                val repliedToRequest = data["repliedToRequest"] as Boolean
                val timestamp: Timestamp = data["timestamp"] as Timestamp
                val request: Request? = (data["request"] as? HashMap<*, *>)?.run {
                    val requestId = get("requestId") as String
                    val reqOwnerId = get("ownerId") as String
                    val reqOwnerName = get("ownerName") as String
                    val requestText = get("requestText") as String
                    val answered = get("answered") as Boolean
                    val reqTimestamp = get("timestamp") as Timestamp
                    Request(
                        requestId = requestId,
                        ownerId = reqOwnerId,
                        ownerName = reqOwnerName,
                        requestText = requestText,
                        answered = answered,
                        timestamp = reqTimestamp
                    )
                }
                val attachmentsList: List<Pair<String, String>> =
                    convertAttachmentsList(data["attachmentsList"])
                val flairsList: List<String> = data["flairsList"] as List<String>
                val likesNum: Int = (data["likesNum"] as Long).toInt()


                Post(
                    id = id,
                    ownerId = ownerId,
                    ownerFullName = ownerName,
                    title = title,
                    content = content,
                    repliedToRequest = repliedToRequest,
                    request = request,
                    timestamp = timestamp,
                    attachmentsList = attachmentsList,
                    flairsList = flairsList,
                    likesNum = likesNum
                )
            }
            val sortedPostList = postsList.sortedByDescending { post ->
                post.timestamp
            }
            return sortedPostList
        }else{
            listOf()
        }
    }
    private fun convertAttachmentsList(data: Any?): List<Pair<String, String>> {
        return if (data is List<*>) {
            data.filterIsInstance<HashMap<String, String>>().map { hashMap ->
                Pair(hashMap["first"].orEmpty(), hashMap["second"].orEmpty())
            }
        } else {
            emptyList()
        }
    }

    fun mapDocSnapshotsToRequestList(docSnapshotsList: List<DocumentSnapshot>): List<Request>? {
        return try {
            val requestsList : List<Request> = docSnapshotsList.map { document ->
                val data = document.data!!
                val requestId = data["requestId"] as String
                val ownerId = data["ownerId"] as String
                val ownerName = data["ownerName"] as String
                val requestText = data["requestText"] as String
                val answered = data["answered"] as Boolean
                val timestamp = data["timestamp"] as Timestamp
                Request(requestId,ownerId ,ownerName, requestText, answered, timestamp)
            }
            val sortedRequestList = requestsList.sortedByDescending {  post ->
                post.timestamp
            }
            sortedRequestList
        }catch (e : Exception){
            null
        }
    }

}