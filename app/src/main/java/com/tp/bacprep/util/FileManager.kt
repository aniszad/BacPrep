package com.tp.bacprep.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.FileViewerActivity
import com.tp.bacprep.databinding.CreateFolderDialogLayoutBinding
import com.tp.bacprep.databinding.DownloadItemLayoutBinding
import com.tp.bacprep.databinding.WarningDialogLayoutBinding
import com.tp.bacprep.domain.models.FileSystemItem
import com.tp.bacprep.domain.models.ItemType
import com.tp.bacprep.domain.models.RecentOpenedFile
import java.io.File
import java.sql.Timestamp

class FileManager(private val context: Context) {


    private lateinit var downloadsRvAdapter: DownloadsRvAdapter
    private var currentDirectoryPath: String
    private var myDownloadsFolderPath: String
    private lateinit var mCreateFolderDialog: Dialog
    private lateinit var mDeleteWarningDialog: Dialog
    private var packagePath: String
    private lateinit var fileManagerVmFunctions: FileManagerViewModelFunctions


    init {
        currentDirectoryPath = buildString {
            append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path)
            append("/")
            append(Constants.DOWNLOADS_FOLDER_NAME)
        }
        myDownloadsFolderPath = buildString {
            append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path)
            append("/")
            append(Constants.DOWNLOADS_FOLDER_NAME)
        }
        packagePath = buildString {
            append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path)
            append("/")
        }
        createFolder(Constants.DOWNLOADS_FOLDER_NAME, packagePath)
    }

    interface FileManagerViewModelFunctions{
        fun addToRecentFiles(file : RecentOpenedFile)
    }

    fun setFileManagerVmInterface(fileManagerViewModelFunctions : FileManagerViewModelFunctions){
        fileManagerVmFunctions = fileManagerViewModelFunctions
    }

    // function to set the downloads recycler view (it displays the files inside the app's package)
    fun setRecyclerView(recyclerView: RecyclerView) {
        val fileList = getFilesAndFolders(currentDirectoryPath)
        val filesList  = mutableListOf<FileSystemItem>()
        for (file in fileList){
            filesList.add(file)
        }
        downloadsRvAdapter = DownloadsRvAdapter(context, filesList)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = downloadsRvAdapter
    }

    // function to get the file and folders from a path
    private fun getFilesAndFolders(directoryPath: String): List<FileSystemItem> {
        val directory = File(directoryPath)
        val fileList = mutableListOf<FileSystemItem>()
        directory.listFiles()?.forEach { file ->
            val itemType = if (file.isDirectory) ItemType.FOLDER else ItemType.FILE
            val fileSize = if (file.isFile) file.length() else file.listFiles()?.size?.toLong() ?: 0
            fileList.add(
                FileSystemItem(name = file.name, path = file.absolutePath, type = itemType,
                fileSize = fileSize, timestamp = Timestamp(file.lastModified())
                )
            )
        }
        return fileList
    }

    fun onFileClickListener(fileSystemItem: FileSystemItem) {
        Toast.makeText(context, fileSystemItem.path, Toast.LENGTH_SHORT).show()
        navigateToFolder(fileSystemItem.path)
    }

    // function to navigate to the path provided
    private fun navigateToFolder(directoryPath: String) {
        val newFilesAndFolders = getFilesAndFolders(directoryPath)
        downloadsRvAdapter.submitList(newFilesAndFolders)
        currentDirectoryPath = directoryPath
    }

    // navigating back by one level
    fun navigateBackOneLevel() {
        val segments = currentDirectoryPath.split("/")  // Split the path into segments
        if (segments.size > 1) {
            if (currentDirectoryPath != myDownloadsFolderPath){
                // Remove the last segment (folder3) and join the remaining segments
                val newPath = segments.dropLast(1).joinToString("/")
                navigateToFolder(newPath)
            }else{
                if (context is Activity){
                    context.finish()
                }
            }

        }
    }

    // implementing onDeleteFileClickListener interface defined in the DownloadsRvAdapter
    fun onDeleteFileClickListener(fileName: String, position: Int) {
        showDeleteWarningDialog(context, context.getString(R.string.etes_vous_sur_de_supp)) { deleteFile(File(currentDirectoryPath, fileName), position) }
    }

    // Function to delete a file
    private fun deleteFile(fileToDelete: File, position: Int) {
        if (fileToDelete.exists()) {
            if (fileToDelete.isDirectory) {
                deleteFolder(fileToDelete)  // Call the function to delete the folder
                hideDeleteWarningDialog()
                downloadsRvAdapter.notifyItemDeleted(position)
                Toast.makeText(context, "dossier supprimé", Toast.LENGTH_SHORT).show()
            } else {
                val isDeleted = fileToDelete.delete()
                if (isDeleted) {
                    hideDeleteWarningDialog()
                    downloadsRvAdapter.notifyItemDeleted(position)
                    Toast.makeText(context, "fichier supprimé", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // function used in 'fun deleteFile' to delete sub-folders and sub-files
    private fun deleteFolder(folderToDelete: File) {
        val files = folderToDelete.listFiles() ?: return
        for (file in files) {
            if (file.isDirectory) {
                deleteFolder(file)  // Recursively delete subfolders
            } else {
                file.delete()  // Delete individual files
            }
        }
        folderToDelete.delete()  // Finally, delete the empty folder itself
    }

    // searching function
    fun searchFileAndFolders(query: String) {
        val queryResults = getFilesAndFolders(currentDirectoryPath).filter { it.name.contains(query) }
        downloadsRvAdapter.submitList(queryResults)
    }

    fun showCreateFolderDialog(context:Context){
        mCreateFolderDialog = Dialog(context)
        mCreateFolderDialog.setCancelable(true)
        mCreateFolderDialog.setCanceledOnTouchOutside(true)
        mCreateFolderDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bindingCfDialog = CreateFolderDialogLayoutBinding.inflate(LayoutInflater.from(context))
        mCreateFolderDialog.setContentView(bindingCfDialog.root)
        bindingCfDialog.apply {
            btnCreateFolder.setOnClickListener {
                Toast.makeText(context, "hehehe", Toast.LENGTH_SHORT).show()
                val newFolderName = bindingCfDialog.etNewFolderName.text.toString()
                if (newFolderName.isNotBlank()) {
                    createFolder(newFolderName, currentDirectoryPath)
                }

                }
            btnCancel.setOnClickListener {
                hideCreateFolderDialog()
            }
        }
        mCreateFolderDialog.show()
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
                    Toast.makeText(context, "le dossier a été crée", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "le fichier n'a pas été crée", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun hideCreateFolderDialog() {
        mCreateFolderDialog.dismiss()
    }

    fun showDeleteWarningDialog(context : Context, warningMessage : String, onConfirmed : () -> Unit) {
        mDeleteWarningDialog = Dialog(context)
        mDeleteWarningDialog.setContentView(R.layout.warning_dialog_layout)
        mDeleteWarningDialog.setCancelable(true)
        mDeleteWarningDialog.setCanceledOnTouchOutside(true)
        mDeleteWarningDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bindingDialog = WarningDialogLayoutBinding.inflate(LayoutInflater.from(context))
        mDeleteWarningDialog.setContentView(bindingDialog.root)
        bindingDialog.tvTitle.visibility = View.GONE
        bindingDialog.imWarningExclamation.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
        bindingDialog.tvContent.text = warningMessage
        bindingDialog.btnConfirm.setOnClickListener {
            onConfirmed.invoke()
        }
        bindingDialog.btnCancel.setOnClickListener {
            hideDeleteWarningDialog()
        }
        mDeleteWarningDialog.show()
    }

    fun hideDeleteWarningDialog() {
        if (mDeleteWarningDialog.isShowing){
            mDeleteWarningDialog.dismiss()
        }
    }

    inner class DownloadsRvAdapter(private var context : Context, private var downloadedFilesList : MutableList<FileSystemItem>) : RecyclerView.Adapter<DownloadsRvAdapter.DownloadsRvViewHolder>() {
        var fileSystemItem : FileSystemItem? = null

        inner class DownloadsRvViewHolder(binding : DownloadItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
            val fileName = binding.tvFileName
            val fileSize = binding.tvFileSize
            val fileDate = binding.tvDownloadDate
            val imFileType = binding.imExtensionType
            val llFileInfo = binding.llFileInfo
            val btnDelete = binding.btnDeleteFile
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadsRvViewHolder {
            return DownloadsRvViewHolder(DownloadItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun getItemCount(): Int {
            return downloadedFilesList.size
        }

        override fun onBindViewHolder(holder: DownloadsRvViewHolder, position: Int) {
            val currentFile = downloadedFilesList[position]
            holder.fileName.text = currentFile.name
            holder.fileDate.text = FormattedTimestampDate().formatTimestamp(context, fbTimestamp=null, javaTimestamp = currentFile.timestamp)
            if (currentFile.type == ItemType.FILE){
                holder.fileSize.text = getFileSize(currentFile.fileSize)

                if (currentFile.name.endsWith("pdf")){
                    holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_pdf))
                    holder.imFileType.setOnClickListener {
                        openPdf(currentFile)
                    }
                    holder.llFileInfo.setOnClickListener {
                        openPdf(currentFile)
                    }
                }else{
                    holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_image))
                    holder.imFileType.setOnClickListener {
                        openImage(currentFile)
                    }
                    holder.llFileInfo.setOnClickListener {
                        openImage(currentFile)
                    }
                }
            }else{
                holder.fileSize.text = buildString {
                    append(currentFile.fileSize.toString())
                    append(" ")
                    append("Fichiers")
                }

                holder.llFileInfo.setOnClickListener {
                    onFileClickListener(currentFile)
                }
                holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_folder))
            }

            holder.btnDelete.setOnClickListener {
                onDeleteFileClickListener(currentFile.name, position)
            }

        }

        private fun getFileSize(fileSize: Long): String {
            if (fileSize <= 0) {
                return "0 B"
            }

            val units = arrayOf("B", "KB", "MB", "GB", "TB")
            val digitGroups = (Math.log10(fileSize.toDouble()) / Math.log10(1024.0)).toInt()
            val formattedSize = "%.1f %s".format(fileSize / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
            return formattedSize
        }

        fun submitList(filesList : List<FileSystemItem>){
            this.downloadedFilesList = filesList.toMutableList()
            notifyDataSetChanged()
        }

        fun notifyItemDeleted(position: Int) {
            this.downloadedFilesList.removeAt(position)
            notifyItemRemoved(position)
        }

        private fun openPdf(currentFile: FileSystemItem) {
            fileManagerVmFunctions.addToRecentFiles(
                RecentOpenedFile(currentFile.name, System.currentTimeMillis())
            )
            val file: File = File(currentDirectoryPath, currentFile.name)
            val uri: Uri = FileProvider.getUriForFile(context, "com.tp.bacprep.fileprovider", file)
            val intent = Intent(context, FileViewerActivity::class.java)
            intent.putExtra(Constants.FILE_VIEWER_TYPE, Constants.OPEN_PDF)
            intent.putExtra("Uri_file_top_open", uri)
            context.startActivity(intent)
        }
        private fun openImage(currentFile : FileSystemItem) {
            fileManagerVmFunctions.addToRecentFiles(
                RecentOpenedFile(currentFile.name, System.currentTimeMillis())
            )
            val subjectsBankPath = buildString {
                append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: "")
                append("/")
                append(Constants.FILES_BANK_FOLDER_NAME)
            }
            try {
                val imageFile = File(subjectsBankPath, currentFile.name) // subjectsBankPath should be the absolute path to your file
                val imageUri = FileProvider.getUriForFile(context, "com.tp.bacprep.fileprovider", imageFile)
                val intent = Intent(context, FileViewerActivity::class.java)
                intent.putExtra(Constants.FILE_VIEWER_TYPE, Constants.OPEN_IMAGE)
                intent.putExtra(Constants.IMAGE_URI, imageUri)
                context.startActivity(intent)

            }catch (e : Exception){
                Log.e("open file error", e.message.toString())
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()

            }
        }

    }

}