package com.tp.bacprep.data.datasource

import android.util.Log
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import com.tp.bacprep.domain.models.FileDriveItem
import com.tp.bacprep.domain.models.ItemType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

class DriveStorageFiles {
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
            this@DriveStorageFiles::class.java.classLoader!!.getResourceAsStream(
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

    suspend fun queryDriveFiles(folderId: String): List<FileDriveItem>? {
        return try {
            val scope = CoroutineScope(Dispatchers.IO)
            val files = scope.async {
                driveService.files().list()
                    .setQ("'$folderId' in parents")
                    .setFields("files(id, name, webViewLink, size, mimeType, webContentLink, exportLinks)")
                    .execute()
            }
            val results = files.await()
            val queryResultList = mutableListOf<FileDriveItem>()

            for (file in results.files) {
                val fileId = file.id
                val fileName= file.name
                val fileType = fileOrDirectory(file.mimeType)
                val size = file.size
                var downloadUrl = ""
                if (fileType == ItemType.FILE){
                    downloadUrl = file.webContentLink
                }
                queryResultList.add(
                    FileDriveItem(
                        fileId = fileId,
                        fileName = fileName,
                        fileType = fileType,
                        size = size.toLong(),
                        downloadUrl = downloadUrl
                    )
                )
            }
            queryResultList
        } catch (e: IOException) {
            Log.e("ERROR WHILE GETTING FILES ", e.message.toString())
            null
        }
    }

    /*suspend fun getFiles(path : String): List<FileDriveItem> {

        try {
            val filesList = mutableListOf<FileDriveItem>()
            val listingResult = storage.child(path).listAll().await()
                for (item in listingResult.prefixes){
                    val fileName = item.name
                    val filePath = item.path
                    val fileType = fileOrDirectory(item.name)
                    val downloadUrl = ""
                    filesList.add(FileDriveItem(fileName, filePath, fileType, 0L, downloadUrl))
                }
                for (item in listingResult.items){
                    val metadata = item.metadata.await()
                    val fileName = metadata.name.toString()
                    val filePath = metadata.path
                    val fileType = fileOrDirectory(metadata.name.toString())
                    val sizeInBytes = metadata.sizeBytes
                    val downloadUrl = item.downloadUrl.await().toString()
                    filesList.add(FileDriveItem(fileName, filePath, fileType, sizeInBytes, downloadUrl))
                }
            return filesList
        }catch (e : Exception){
            return listOf()
        }
    }*/

    private fun fileOrDirectory(mimeType: String): ItemType {
        return if (mimeType == "application/vnd.google-apps.folder"){
            ItemType.FOLDER
        }else{
            ItemType.FILE
        }
    }

}