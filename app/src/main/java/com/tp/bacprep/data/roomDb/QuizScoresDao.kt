package com.tp.bacprep.data.roomDb

import androidx.room.*
import com.tp.bacprep.domain.models.QuizScore

@Dao
interface QuizScoresDao {

    @Insert
    fun addScore(score : QuizScore)

    @Query("SELECT * FROM my_quiz_scores WHERE module = :module AND semester = :semester AND type= :type")
    fun getScores(module:String, semester:String, type:String) : List<QuizScore>

    @Query("SELECT * FROM my_quiz_scores")
    fun getAllScores() : List<QuizScore>
}