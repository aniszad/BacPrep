package com.tp.bacprep.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_quiz_scores")
data class QuizScore (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val score : Int,
    val module : String,
    val semester : String,
    val type : String,
    )