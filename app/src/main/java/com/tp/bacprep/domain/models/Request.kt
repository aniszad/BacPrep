package com.tp.bacprep.domain.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Request (
    var requestId : String="",
    var ownerId : String="",
    var ownerName : String="",
    var requestText : String="",
    var answered : Boolean = false,
    var timestamp : Timestamp = Timestamp(0,0),
    ) : Parcelable