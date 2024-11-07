package com.tp.bacprep.util

import android.content.Context
import com.google.firebase.Timestamp
import com.tp.bacprep.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

class FormattedTimestampDate {

    val todayTsDate : Timestamp get() = getTodaysDate()
    val oneMonthAgoTsDate : Timestamp get() = getOneMonthAgoDate()

    private fun getOneMonthAgoDate(): Timestamp {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val oneMonthAgo = calendar.time
        return Timestamp(oneMonthAgo)
    }

    private fun getTodaysDate(): Timestamp {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.FRANCE)
        val currentDate = Date()
        val formattedDate = dateFormat.format(currentDate)
        val parsedDate = dateFormat.parse(formattedDate)
        return Timestamp(parsedDate)
    }

    fun formatTimestamp(context: Context, fbTimestamp: Timestamp?, javaTimestamp: java.sql.Timestamp?): String {
        if (fbTimestamp != null){
            val currentDate = Calendar.getInstance().time
            val date = fbTimestamp.toDate()
            val timeDifference = abs(currentDate.time - date.time)
            val hours = timeDifference / (60 * 60 * 1000)
            val minutes = timeDifference / (60 * 1000)
            return if (timeDifference < 24 * 60 * 60 * 1000) {
                if (hours > 0) {
                    buildString {
                        append(context.getString(R.string.il_y_a))
                        append(" ")
                        append("$hours")
                        append(" ")
                        append(context.getString(R.string.heures))
                    }
                } else {
                    buildString {
                        append(context.getString(R.string.il_y_a))
                        append(" ")
                        append("$minutes")
                        append(" ")
                        append(context.getString(R.string.minutes))
                    }
                }
            } else {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy",  Locale.FRANCE)
                dateFormat.format(date)
            }
        }else{
            val currentDate = Date()
            val date = Date(javaTimestamp!!.time)
            val timeDifference = abs(currentDate.time - date.time)
            val hours = timeDifference / (60 * 60 * 1000)
            val minutes = timeDifference / (60 * 1000)

            return if (timeDifference < 24 * 60 * 60 * 1000) {
                if (hours > 0) {
                    buildString {
                        append(context.getString(R.string.il_y_a))
                        append("$hours")
                        append(context.getString(R.string.heures))
                    }
                } else {
                    buildString {
                        append(context.getString(R.string.il_y_a))
                        append("$minutes")
                        append(context.getString(R.string.minutes))
                    }

                }
            } else {
                val dateFormat = SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault())
                dateFormat.format(date)
            }
        }
    }


}