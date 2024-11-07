package com.tp.bacprep.data.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tp.bacprep.domain.models.StudyTime

@Dao
interface StudyTimeDao {

    @Insert
    fun saveStudyTime(studyTime: StudyTime)

    @Update
    fun updateStudyTime(studyTime: StudyTime)

    @Query("SELECT * FROM study_time_records WHERE day = :day AND month = :month AND year = :year")
    fun getStudyTime(day: Int, month: Int, year: Int): StudyTime?

    @Query("SELECT * FROM study_time_records WHERE month = :month AND year = :year")
    fun getMonthStudyTime(month: Int, year: Int) : List<StudyTime>?


}