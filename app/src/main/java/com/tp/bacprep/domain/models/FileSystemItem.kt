package com.tp.bacprep.domain.models

import androidx.room.PrimaryKey
import java.sql.Timestamp


open class FileSystemItem(
    val name: String,
    val path: String,
    val type: ItemType,
    val fileSize : Long,
    var timestamp: Timestamp = Timestamp(0L),
)

