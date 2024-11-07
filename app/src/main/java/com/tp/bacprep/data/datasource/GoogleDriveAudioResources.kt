package com.tp.bacprep.data.datasource

import android.util.Log
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import com.tp.bacprep.domain.models.AudioFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

class GoogleDriveAudioResources {

    private var driveService: Drive

    companion object {
        private const val APPLICATION_NAME = "Bac Prep"
        private val JSON_FACTORY: JsonFactory =
            GsonFactory.getDefaultInstance()
        private val SCOPES = listOf(DriveScopes.DRIVE)
        private const val CREDENTIALS_FILE_PATH = "res/raw/credentials.json"
    }

    init {
        // Load client secrets from your credentials file
        val `in`: InputStream =
            this@GoogleDriveAudioResources::class.java.classLoader!!.getResourceAsStream(
                CREDENTIALS_FILE_PATH
            )
                ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
        val googleCredentials: GoogleCredentials = GoogleCredentials.fromStream(`in`).createScoped(
            SCOPES
        )

        driveService = Drive.Builder(
            NetHttpTransport(),
            JSON_FACTORY,
            HttpCredentialsAdapter(googleCredentials)
        )
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    suspend fun queryAudioResources(parentFolderId : String): List<AudioFile>? {
        return try {
            val scope = CoroutineScope(Dispatchers.IO)
            val files = scope.async {
                driveService.files().list()
                    .setQ("mimeType contains 'audio/' and '$parentFolderId' in parents")
                    .setFields("files(id, name, webContentLink)")
                    .execute()
            }
            val results = files.await()
            val filesWithSharingLinks = mutableListOf<AudioFile>()
            for (file in results.files) {
                // Retrieve permissions for the file
                val sharingLink = file.webContentLink
                val fileName = file.name
                val fileId = file.id
                filesWithSharingLinks.add(
                    AudioFile(
                        id = fileId,
                        title = fileName,
                        type = "audio",
                        downloadUrl = sharingLink,
                    )
                )
            }
            filesWithSharingLinks
        } catch (e: IOException) {
            Log.e("ERROR WHILE GETTING FILES ", e.message.toString())
            null
        }
    }

    suspend fun queryFilesAndGetSharingLinks(parentFolderId : String): List<AudioFile>? {
        return try {
            val scope = CoroutineScope(Dispatchers.IO)
            val files = scope.async {
                driveService.files().list()
                    .setQ("mimeType contains 'audio/' and '$parentFolderId' in parents")
                    .setFields("files(id, name, webContentLink)")
                    .execute()
            }
            val results = files.await()
            val filesWithSharingLinks = mutableListOf<AudioFile>()
            for (file in results.files) {
                // Retrieve permissions for the file
                val sharingLink = file.webContentLink
                val fileName = file.name
                val fileId = file.id
                Log.e("SHARING LINK :: ", file.webContentLink)
                filesWithSharingLinks.add(
                    AudioFile(
                        fileId,
                        fileName,
                        "audio",
                        sharingLink,
                    )
                )
            }
            filesWithSharingLinks
        } catch (e: IOException) {
            Log.e("ERROR WHILE GETTING FILES ", e.message.toString())
            null
        }
    }

    suspend fun listAllFiles() {
        try {
            val scope = CoroutineScope(Dispatchers.IO)
            val files = scope.async {
                driveService.files().list()
                    .execute()
            }
            val filesList = files.await().files
            Log.e("Files list size : ", "flies list size/${filesList.size}/${filesList}")

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    /* private var db = Firebase.firestore
     private var storage = Firebase.storage.reference

     suspend fun getAllAudioResources() : List<AudioFile>?{
         return try {
             val documentsSnapshots = db.collection("Audio Resources").get().await()
             val audioFilesList : List<AudioFile> = documentsSnapshots.documents.mapNotNull {document ->
                 val data = document.data!!
                 AudioFile(title = data["title"] as String, type = data["type"] as String, path = data["path"] as String, timestamp = data["timestamp"] as Timestamp)
             }
             audioFilesList
         }catch (e : Exception){
             Log.e("audio resources retrieving error", e.message.toString())
             null
         }
     }

     suspend fun getAudioDownloadUrl(audioStoragePath: String): String? {
         return try {
             val downloadUrl = storage.child(audioStoragePath).downloadUrl.await()
             downloadUrl.toString()
         }catch (e : Exception){
             Log.e("retrieving audio download Url", e.message.toString())
             null
         }

     }
     */
}