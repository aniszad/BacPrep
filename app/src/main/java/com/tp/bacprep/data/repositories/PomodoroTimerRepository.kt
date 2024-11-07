package com.tp.bacprep.data.repositories

import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.domain.models.StudyTime

class PomodoroTimerRepository(private val roomDb: RoomDb) {
    fun saveTimeInRoomDb(studyTime: StudyTime){
        roomDb.studyTimeDao.saveStudyTime(studyTime)
    }

    fun getStudyTime(day: Int, month: Int, year: Int): StudyTime? {
        return roomDb.studyTimeDao.getStudyTime(day, month, year)
    }

    fun updateStudyTime(studyTime: StudyTime) {
        roomDb.studyTimeDao.updateStudyTime(studyTime)
    }

    fun getMonthStudyTime(month: Int, year: Int): List<StudyTime>? {
        return roomDb.studyTimeDao.getMonthStudyTime(month, year)
    }
}