package com.tp.bacprep.data.repositories

import com.tp.bacprep.data.datasource.GoogleDriveAudioResources
import com.tp.bacprep.domain.models.AudioFile

class AudioResourcesRepository(private val googleDriveAudioResources: GoogleDriveAudioResources) {

    suspend fun queryFilesAndGetSharingLinks(parentsFolderId: String): List<AudioFile>? {
        return googleDriveAudioResources.queryFilesAndGetSharingLinks(parentsFolderId)
    }
    suspend fun queryAudioResources(parentFolderId : String): List<AudioFile>? {
        return googleDriveAudioResources.queryAudioResources(parentFolderId)
    }
}