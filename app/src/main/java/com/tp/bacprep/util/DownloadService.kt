package com.tp.bacprep.util


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tp.bacprep.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class DownloadService : Service() {
    private val channelId = "download_channel"
    private val notificationId = 1
    private var position : Int = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        position = intent?.getIntExtra("filePositionInRv", 0) ?:0
        val downloadUrl = intent?.getStringExtra("downloadUrl") ?: ""
        val fileName = intent?.getStringExtra("fileName") ?: ""
        val downloadPath = intent?.getStringExtra("downloadPath") ?: ""

        if (downloadUrl.isNotEmpty() && fileName.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val file = withContext(Dispatchers.IO) {
                        downloadFile(downloadUrl, fileName, downloadPath)
                    }
                    // Download completed, remove or update the notification
                    withContext(Dispatchers.Main) {
                        val notificationManager = NotificationManagerCompat.from(this@DownloadService)
                        notificationManager.cancel(notificationId) // Remove the notification

                        // You can also update the notification to show the download completed state
                        val notificationBuilder = createNotification()
                            .setContentTitle("Download complete")
                            .setContentText("File downloaded successfully")

                        if (ActivityCompat.checkSelfPermission(
                                this@DownloadService,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@withContext
                        }
                        notificationManager.notify(notificationId, notificationBuilder.build())

                        //sending broadcast to the fb file manager, when download is completed
                        val intent = Intent(Constants.DOWNLOAD_FILE_FROM_FIREBASE_ACTION)
                        intent.putExtra("downloadComplete", true)
                        intent.putExtra("position", position)
                        sendBroadcast(intent)
                        Toast.makeText(this@DownloadService, getString(R.string.download_completed), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@DownloadService,
                            getString(R.string.download_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } finally {
                    // Stop the service when the download is complete
                    stopSelf(startId)
                }
            }
        } else {
            // Handle missing downloadUrl or fileName
            stopSelf(startId) // Stop the service
        }

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Download Channel"
            val descriptionText = "Channel for download notifications"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            // Customize the channel as per your needs (e.g., sound, vibration, etc.)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.icon_download)
            .setContentTitle("Téléchargement")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)
    }

    private suspend fun downloadFile(downloadUrl: String, fileName: String, downloadPath : String): File {
        val url = URL(downloadUrl)
        val connection = withContext(Dispatchers.IO) {
            url.openConnection() as HttpURLConnection
        }
        connection.connectTimeout = 15000
        connection.readTimeout = 15000
        connection.requestMethod = "GET"
        withContext(Dispatchers.IO) {
            connection.connect()
        }

        return if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val directory = File(downloadPath)
            if (!directory.exists()){
                if (downloadPath.contains(Constants.FILES_BANK_FOLDER_NAME)){
                    createFolder(Constants.FILES_BANK_FOLDER_NAME,this@DownloadService.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path+"/"+Constants.DOWNLOADS_FOLDER_NAME)
                }else{
                    createFolder(Constants.DOWNLOADS_FOLDER_NAME,this@DownloadService.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: "")
                }
            }
            val file = File(
                downloadPath,
                fileName
            ) // Save file in internal storage

            val outputStream = FileOutputStream(file)

            val fileLength = connection.contentLength
            val data = ByteArray(1024)
            var total = 0
            var count: Int

            while (inputStream.read(data).also { count = it } != -1) {
                total += count
                updateProgressNotification((total * 100 / fileLength), 100) // Update progress
                outputStream.write(data, 0, count)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            file
        } else {
            throw IOException("Download failed with response code ${connection.responseCode}")
        }
    }

    private fun updateProgressNotification(progress: Int, total: Int) {
        val notificationBuilder = if (progress == 100) {
            createCompletedNotification()
        } else {
            createProgressNotification(total, progress)
        }

        if (progress == 100) {
            notificationBuilder.setProgress(0, 0, false) // Remove the progress bar
        }

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun createProgressNotification(total: Int, progress: Int): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Téléchargement...")
            .setProgress(total, progress, false)
            .setContentText("Progrès: $progress%")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)
            .setOngoing(true)
    }

    private fun createCompletedNotification(): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Download finished")
            .setContentText("File downloaded successfully")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)
            .setOngoing(false)
    }

    fun createFolder(newFolderName : String, directoryPath: String){
        val newFolderPath : String = buildString {
            append(directoryPath)
            append("/")
            append(newFolderName)
        }
        val newFolder = File(newFolderPath)
        if (!newFolder.exists()){
            if (newFolder.mkdirs()){
                Log.e("Folder created", "true")
            }else{
                Log.e("Folder created", "false")
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
