package com.tp.bacprep.domain.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "current_user_table")
data class User(
    @PrimaryKey
    var id : String = "",
    var name : String = "",
    var lastName : String = "",
    var email : String = "",
    var image : Int = 1,
    var branch : String ="",
    var additionalInfo : String = "",
    @Ignore
    var role : String="",
    var emailVerified : Boolean =false,
    var blocked : Boolean = false,
)
