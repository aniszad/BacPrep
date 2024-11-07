package com.tp.bacprep.data.datasource

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tp.bacprep.domain.models.Request
import com.tp.bacprep.util.Constants
import kotlinx.coroutines.tasks.await

class FbRequests {
    private val db = Firebase.firestore


    suspend fun getAllRequests() : List<Request>?{
        return try {
            val task = db.collection(Constants.REQUESTS).get().await()
            val requestsList : List<Request> = task.documents.mapNotNull { document ->
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

    suspend fun addRequest(request : Request) : Boolean{
        return try {
            val docRef = db.collection(Constants.REQUESTS).document()
            request.requestId = docRef.id
            docRef.set(request).await()
            true
        }catch (e : Exception){
            return false
        }
    }

    suspend fun getMyRequests(context : Context): List<Request>? {
        return try {
            val docRef = db.collection(Constants.REQUESTS).whereEqualTo("ownerId", FbAuth(context).getUserId()).get().await()
            val list = docRef.documents.mapNotNull {  doc->
                val data = doc.data!!
                Request(
                    data["requestId"] as String,
                    data["ownerId"] as String,
                    data["ownerName"] as String,
                    data["requestText"] as String,
                    data["answered"] as Boolean,
                    data["timestamp"] as Timestamp,
                )
            }
            list
        }catch (e : Exception){
            return listOf()
        }
    }

    suspend fun deleteRequest(requestId: String) : Boolean {
        return try {
            db.collection(Constants.REQUESTS).document(requestId).delete().await()
            true
        }catch (e : Exception){
            Log.e("Request delete error:", e.message.toString())
            false
        }
    }

    suspend fun queryRequests(
        startDate: Timestamp?,
        endDate: Timestamp?,
        lastRequestVisible: DocumentSnapshot?
    ) : List<DocumentSnapshot>? {
         return try {
             if (startDate != null && endDate != null){
                 var query = db.collection(Constants.REQUESTS)
                     .whereGreaterThanOrEqualTo("timestamp", startDate)
                     .whereLessThanOrEqualTo("timestamp", endDate)
                     .orderBy("timestamp", Query.Direction.DESCENDING)
                 if (lastRequestVisible!=null){
                     query = query.startAfter(lastRequestVisible)
                 }
                 query.limit(5)
                 val results = query.get().await()
                 results.documents
             }else{

                 var query = db.collection(Constants.REQUESTS)
                     .orderBy("timestamp", Query.Direction.DESCENDING)
                 if (lastRequestVisible!=null){
                     query = query.startAfter(lastRequestVisible)
                 }
                 query.limit(5)
                 val results = query.get().await()
                 results.documents
             }

        }catch (e: FirebaseException){
            Log.e("Firebase Error : ", e.message.toString())
             null
        }



    }

}