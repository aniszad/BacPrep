package com.tp.bacprep.domain.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

@Entity(tableName = "bookmarks")
class Bookmark (
        @PrimaryKey
        var bookmarkId : String = "",
        var bookmarkOwnerId : String = "",
        var bookmarkOwnerFullName : String = "",
        var bookmarkTitle : String = "",
        var bookmarkContent : String = "",
        var bookmarkRequest : String? = null,
        var savedDate : Long,
        var bookmarkAttachmentsList : List<Pair<String, String>>? = null,
        var bookmarkFlairsList: List<String> = listOf(),
    ) : Post(){
        @Ignore
        override var repliedToRequest : Boolean = false
        @Ignore
        override var request : Request? = null
        @Ignore
        override var timestamp : Timestamp = Timestamp(0,0)
        @Ignore
        override var likesNum : Int = 0

        init {
                    apply {
                            id = bookmarkId
                            ownerId = bookmarkOwnerId
                            ownerFullName = bookmarkOwnerFullName
                            title = bookmarkTitle
                            content = bookmarkContent
                            attachmentsList = bookmarkAttachmentsList
                            flairsList = bookmarkFlairsList
                    }
            }
    }