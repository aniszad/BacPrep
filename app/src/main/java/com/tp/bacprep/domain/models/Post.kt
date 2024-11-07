package com.tp.bacprep.domain.models

import com.google.firebase.Timestamp

open class Post (
    var id : String = "",
    var ownerId : String = "",
    var ownerFullName : String = "",
    var title : String = "",
    var content : String = "",
    open var repliedToRequest : Boolean = false,
    open var request : Request? = null,
    open var timestamp : Timestamp = Timestamp(0,0),
    var attachmentsList : List<Pair<String, String>>? = null,
    var flairsList: List<String> = listOf(),
    open var likesNum : Int = 0
)