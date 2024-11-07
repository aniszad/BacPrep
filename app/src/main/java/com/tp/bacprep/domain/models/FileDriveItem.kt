package com.tp.bacprep.domain.models

data class FileDriveItem(
    val fileId: String,
    val fileName: String,
    val fileType: ItemType,
    val size : Long,
    val downloadUrl : String,
) : FileSystemItem(fileName, "", fileType, size)

