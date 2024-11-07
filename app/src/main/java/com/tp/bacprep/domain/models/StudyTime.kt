package com.tp.bacprep.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "study_time_records")
class StudyTime (
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var day : Int,
    var month : Int,
    var year : Int,
    var duration : Long
        )