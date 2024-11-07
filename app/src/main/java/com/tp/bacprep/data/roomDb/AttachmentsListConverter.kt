package com.tp.bacprep.data.roomDb

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AttachmentsListConverter {
    @TypeConverter
    fun fromAttachmentsList(attachmentsList: List<Pair<String, String>>?): String? {
        return attachmentsList?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toAttachmentsList(value: String?): List<Pair<String, String>>? {
        val type = object : TypeToken<List<Pair<String, String>>>() {}.type
        return Gson().fromJson(value, type)
    }
}