package com.tp.bacprep.data.repositories

import android.content.Context
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.tp.bacprep.data.datasource.FbRequests
import com.tp.bacprep.domain.models.Request

class RequestsRepository (private val fbRequests: FbRequests) {

    suspend fun addRequest(request : Request) : Boolean{
        return fbRequests.addRequest(request)
    }
    suspend fun getAllRequests() : List<Request>?{
        return fbRequests.getAllRequests()
    }

    suspend fun getMyRequests(context: Context): List<Request>? {
        return fbRequests.getMyRequests(context)
    }

    suspend fun deleteRequest(requestId: String) : Boolean {
        return fbRequests.deleteRequest(requestId)
    }

    suspend fun queryRequests(
        startDate: Timestamp?,
        endDate: Timestamp?,
        lastRequestVisible: DocumentSnapshot?
    ):  List<DocumentSnapshot>?{
        return fbRequests.queryRequests(startDate, endDate, lastRequestVisible)
    }

}