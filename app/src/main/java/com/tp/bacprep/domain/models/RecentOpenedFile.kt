package com.tp.bacprep.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_opened_files")
class RecentOpenedFile(
    @PrimaryKey
    val name: String,
    val time: Long
        )