package com.tp.bacprep.data.roomDb

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import java.util.Date

class TimestampConverter {
    @TypeConverter
    fun fromTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.toDate()?.time
    }

    @TypeConverter
    fun toTimestamp(time: Long?): Timestamp? {
        return time?.let { Timestamp(Date(it)) }
    }
}
