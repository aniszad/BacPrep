package com.tp.bacprep.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "modules_progress", primaryKeys = ["moduleName", "branch"])
class ModuleProgress (
    val moduleName : String,
    val branch : String,
    var progressList : List<String>,
)