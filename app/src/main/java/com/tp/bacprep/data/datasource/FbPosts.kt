package com.tp.bacprep.data.datasource

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tp.bacprep.R
import com.tp.bacprep.domain.models.Post
import com.tp.bacprep.domain.models.Request
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.FormattedTimestampDate
import com.tp.bacprep.util.InternetConnectivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

@Suppress("UNCHECKED_CAST")
class FbPosts(private val context: Context) {
    private val db = Firebase.firestore
    private val batchSize = 10
    private val storage = Firebase.storage.reference
    private val internetConnectivity = InternetConnectivity(context)


    // Publish post functions
    suspend fun publishPost(
        post: Post,
        attachmentsUriList: MutableList<Pair<Uri, String>>,
        progressCallback: (Int) -> Unit
    ): Pair<String?, Boolean> {
        return if (internetConnectivity.isConnectedToInternet) {
            try {
                // Step 1: Generate a new ID for the document
                val documentRef = db.collection(Constants.PUBLISHED_POSTS).document()
                // Step 2: Assign the generated ID to the "id" field of the "post" object
                post.id = documentRef.id
                // Save the document with the updated "post" object
                val postAttachmentList: MutableList<Pair<String, String>> = mutableListOf()
                // Calculate the total size of all attachments
                val totalSize = attachmentsUriList.sumOf {
                    getFileSizeFromContentUri(it.first)
                }

                var uploadedSize: Long = 0
                var lastProgressUpdate = 0

                for (pair in attachmentsUriList) {
                    val fileRef =
                        storage.child("Posts documents").child(post.ownerId).child(post.id)
                            .child(pair.second)
                    // Upload file to Firebase Storage
                    val uploadTask = fileRef.putFile(pair.first)
                    // Observe the upload task to get progress updates
                    uploadTask.addOnProgressListener { taskSnapshot ->


                        // Calculate the cumulative progress based on the overall size of all files
                        val cumulativeProgress =
                            (((uploadedSize + taskSnapshot.bytesTransferred) * 100) / totalSize).toInt()

                        // Check if enough progress has been made since the last update (e.g., 5% or more)
                        if (cumulativeProgress - lastProgressUpdate >= 1) {
                            progressCallback(cumulativeProgress)
                            lastProgressUpdate = cumulativeProgress
                        }
                    }
                    // Wait for the upload to complete
                    uploadTask.await()
                    val downloadUrl = fileRef.downloadUrl.await().toString()
                    // Update the cumulative uploaded size
                    uploadedSize += getFileSizeFromContentUri(pair.first)
                    postAttachmentList.add(Pair(downloadUrl, pair.second))
                }
                post.attachmentsList = postAttachmentList
                documentRef.set(post, SetOptions.merge()).await()
                // Report 100% progress at the end
                progressCallback(100)
                deleteRequest(post.request?.requestId)
                Pair(null, true)
            } catch (e: Exception) {
                Pair(e.message.toString(), false)
            }
        } else {
            Pair("Pas de connexion internet", false)
        }
    }

    private suspend fun updateRequest(requestId: String?) {
        try {
            db.collection(Constants.REQUESTS).document(requestId!!).update(mapOf("answered" to true)).await()
        }catch (e: FirebaseException){
            Log.e("Deleting request error", e.message.toString())
        }
    }

    private suspend fun deleteRequest(requestId: String?) {
        try {
            db.collection(Constants.REQUESTS).document(requestId!!).delete().await()
        }catch (e: Exception){
            Log.e("Deleting request error", e.message.toString())
        }
    }

    private fun getFileSizeFromContentUri(contentUri: Uri): Long {
        context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
            val tempFile = File(context.cacheDir, "tempFile")
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            return tempFile.length()
        }
        return 0L
    }


    // Retrieve posts in batches///////////////////////////////////////////////////////////////////////////////
    suspend fun getPostsBatch(lastVisiblePost: DocumentSnapshot?): List<DocumentSnapshot>? {
        return try {
            val postsCollection = db.collection(Constants.PUBLISHED_POSTS)
            // Construct the query to retrieve posts
            var query = postsCollection
                .orderBy("timestamp", Query.Direction.DESCENDING) // Order by timestamp, for example

            // If there's a last visible post, start the query after it
            if (lastVisiblePost != null) {
                query = query.startAfter(lastVisiblePost)
            }
            // Limit the number of posts to retrieve
            query = query.limit(batchSize.toLong())
            val task = query.get().await()
            val postsList: List<DocumentSnapshot> = task.documents
            postsList

        } catch (e: Exception) {
            Log.e("POSTS RETRIEVAL ERROR/", e.message.toString())
            null
        }
    }
    suspend fun getLatestPosts(): Triple<String?, Boolean, List<DocumentSnapshot>?> {
        return try {
            val postsCollection = db.collection(Constants.PUBLISHED_POSTS)
            // Construct the query to retrieve posts
            var query = postsCollection
                .orderBy("timestamp", Query.Direction.DESCENDING) // Order by timestamp, for example

            // Limit the number of posts to retrieve
            query = query.limit(batchSize.toLong())
            val task = query.get().await()
            val postsList: List<DocumentSnapshot> = task.documents
            Triple(null, true, postsList)

        } catch (e: Exception) {
            
            Log.e("POSTS RETRIEVAL ERROR/", e.message.toString())
            Triple(e.message.toString(), false, null)
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

    suspend fun getMyPosts(): Pair<String?, List<Post>?> {
        return try {
            val task = db.collection(Constants.PUBLISHED_POSTS)
                .whereEqualTo("ownerId", FbAuth(context).getUserId()).get().await()
            val postsList: List<Post> = task.documents.mapNotNull { document ->
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
                @Suppress("UNCHECKED_CAST")
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
            Pair(null, sortedPostList)
        } catch (e: Exception) {
            Pair(e.message.toString(), null)
        }
    }

    suspend fun getPastMonthPosts(): Triple<String?, Boolean, List<Post>?> {
        val oneMonthAgo = FormattedTimestampDate().oneMonthAgoTsDate
        return try {
            val task =
                db.collection(Constants.PUBLISHED_POSTS).whereGreaterThan("timestamp", oneMonthAgo)
                    .get().await()
            val postsList: List<Post> = task.documents.map { document ->
                val data = document.data!!
                val id: String = data["id"] as String
                val ownerId: String = data["ownerId"] as String
                val ownerName: String = data["ownerName"] as String
                val title: String = data["title"] as String
                val content: String = data["content"] as String
                val timestamp: Timestamp = data["timestamp"] as Timestamp
                val attachmentsList: List<Pair<String, String>> =
                    data["attachmentList"] as List<Pair<String, String>>
                val flairsList: List<String> = data["flairsList"] as List<String>
                val likesNum: Int = data["likesNum"] as Int
                Post(
                    id = id,
                    ownerId = ownerId,
                    ownerFullName = ownerName,
                    title = title,
                    content = content,
                    timestamp = timestamp,
                    attachmentsList = attachmentsList,
                    flairsList = flairsList,
                    likesNum = likesNum
                )
            }
            Triple(null, true, postsList)
        } catch (e: Exception) {
            Triple(e.message.toString(), false, null)
        }
    }

    // Download files from posts
    suspend fun downloadFile(downloadUrl: String, fileName: String) {
        withContext(Dispatchers.IO) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val url = URL(downloadUrl)
                    val connection = withContext(Dispatchers.IO) {
                        url.openConnection()
                    } as HttpURLConnection
                    connection.connectTimeout = 15000
                    connection.readTimeout = 15000
                    connection.requestMethod = "GET"
                    withContext(Dispatchers.IO) {
                        connection.connect()
                    }

                    if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                        val inputStream = connection.inputStream
                        val file = File(
                            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                            fileName
                        ) // Save file in internal storage
                        withContext(Dispatchers.IO) {
                            val outputStream = FileOutputStream(file)
                            inputStream.copyTo(outputStream)
                        }
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, context.getString(R.string.t_l_chargement_fini), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Download failed: ${e.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }

    suspend fun updateLike(
        postId: String,
        userId: String,
        postPosition: Int
    ): Triple<String, Int, Int> {
        return try {
            val docRef = db.collection(Constants.PUBLISHED_POSTS).document(postId)
                .collection(Constants.LIKES_SUB_COLLECTION).document(userId)
            val document = docRef.get().await()
            if (document.exists()) {
                docRef.delete().await()
                db.collection(Constants.PUBLISHED_POSTS).document(postId)
                    .update("likesNum", FieldValue.increment(-1)).await()
                val likesNum = db.collection(Constants.PUBLISHED_POSTS).document(postId).get()
                    .await()["likesNum"].toString().toInt()
                Triple(Constants.UNLIKED, likesNum, postPosition)
            } else {
                val data = hashMapOf("likes" to true)
                docRef.set(data).await()
                db.collection(Constants.PUBLISHED_POSTS).document(postId)
                    .update("likesNum", FieldValue.increment(1)).await()
                val likesNum = db.collection(Constants.PUBLISHED_POSTS).document(postId).get()
                    .await()["likesNum"].toString().toInt()
                Triple(Constants.LIKED, likesNum, postPosition)
            }
        } catch (e: Exception) {
            Triple(e.message.toString(), 0, postPosition)
        }
    }

    suspend fun iLikePost(postId: String, callback: (Boolean?) -> Unit) {
        try {
            val docRef = db.collection(Constants.PUBLISHED_POSTS).document(postId)
                .collection(Constants.LIKES_SUB_COLLECTION).document(FbAuth(context).getUserId())
            val document = docRef.get().await()
            if (document.exists()) {
                callback(true)
            } else {
                callback(false)
            }
        } catch (e: Exception) {
            callback(false)
            Log.e("App Error", e.message.toString())
        }
    }

    suspend fun getLikesNum(postId: String): Int {
        val doc = db.collection(Constants.PUBLISHED_POSTS).document(postId).get().await()
        val likesNum = doc.get("likesNum") as Long
        return likesNum.toInt()
    }

    suspend fun iBookmarkedThisPost(ownerId: String, callback: (Boolean?) -> Unit) {
        try {
            val docRef = db.collection(Constants.BOOKMARKED_POSTS).document(ownerId)
            val document = docRef.get().await()
            if (document.exists()) {
                callback(true)
            } else {
                callback(false)
            }
        } catch (e: Exception) {
            callback(null)
        }
    }

    suspend fun getUserRole(userId: String, callback: (String?) -> Unit) {
        try {
            val role = db.collection(Constants.USERS).document(userId).get()
                .await()[Constants.ROLE] as String
            callback(role)
        } catch (e: Exception) {
            callback(null)
        }
    }

    suspend fun performQuery(queryString : String): List<DocumentSnapshot> {
        return try {
            val query = db.collection(Constants.PUBLISHED_POSTS)
                .whereEqualTo("ownerName", queryString)
                .orderBy("timestamp")
                .limit(10L)
            val queryResults = query.get().await()
            queryResults.documents
        }catch (e : Exception){
            listOf()
        }

    }

    suspend fun getAnswersForRequest(requestId: String): List<DocumentSnapshot> {
        return try {
            val query = db.collection(Constants.PUBLISHED_POSTS)
                .whereEqualTo("request.ownerId", requestId)
                .orderBy("timestamp")
                .limit(5L)
            val queryResults = query.get().await()
            queryResults.documents
        }catch (e : Exception){
            listOf()
        }
    }

    suspend fun getRequestAnswersBatch(lastVisiblePost: DocumentSnapshot?, requestOwnerId : String): List<DocumentSnapshot>?  {
        return try {
            val postsCollection = db.collection(Constants.PUBLISHED_POSTS)
            // Construct the query to retrieve posts
            var query = postsCollection
                .whereEqualTo("request.ownerId", requestOwnerId)
                .orderBy("timestamp", Query.Direction.DESCENDING) // Order by timestamp, for example

            // If there's a last visible post, start the query after it
            if (lastVisiblePost != null) {
                query = query.startAfter(lastVisiblePost)
            }

            // Limit the number of posts to retrieve
            query = query.limit(5L)
            val task = query.get().await()
            val postsList: List<DocumentSnapshot> = task.documents
           postsList

        } catch (e: Exception) {
            Log.e("POSTS RETRIEVAL ERROR/", e.message.toString())
            null
        }
    }

    suspend fun delete(ownerId: String, postId: String): Boolean? {
        return try {
            db.collection(Constants.PUBLISHED_POSTS).document(postId).delete().await()
            storage.child(Constants.POSTS_DOCS).child(ownerId).child(postId).listAll().await().items[0].delete().await()
            true
        } catch (e: FirebaseException) {
            Log.e("POSTS RETRIEVAL ERROR/", e.message.toString())
            false
        } catch (e: Exception) {
            Log.e("POSTS RETRIEVAL ERROR/", e.message.toString())
            false
        }
    }


}

