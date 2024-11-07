package com.tp.bacprep.util

import android.Manifest
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.FileViewerActivity
import com.tp.bacprep.databinding.DownloadItemLayoutBinding
import com.tp.bacprep.databinding.LoadingItemLayoutBindingBinding
import com.tp.bacprep.databinding.NoDataFirebaseFileManagerBinding
import com.tp.bacprep.databinding.WarningDialogLayoutBinding
import com.tp.bacprep.domain.models.FileDriveItem
import com.tp.bacprep.domain.models.ItemType
import com.tp.bacprep.domain.models.RecentOpenedFile
import java.io.File
import java.lang.ref.WeakReference
import java.sql.Timestamp

class FirebaseFileManager(
    private val contextReference: Context,
    private val requestPermissionLauncher: ActivityResultLauncher<String>
) {
    private val contextRef: WeakReference<Context> = WeakReference(contextReference)
    private val context: Context
        get() = contextRef.get() ?: throw IllegalStateException("Context is null")
    private var currentPath : String = Constants.FILES_BANK_DRIVE_ID
    private var previousPath : String = ""
    private lateinit var mDeleteWarningDialog: Dialog
    private lateinit var fbFilesRvAdapter : FirebaseFileManager.FbFilesManagerRvAdapter
    private lateinit var fbFileManagerVmFunctions : FbFileManagerViewModelFunctions
    private lateinit var finishActivityListener : FinishActivityListener
    private val localSubjectsBankPath : String = buildString {
        append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: "")
        append("/")
        append(Constants.DOWNLOADS_FOLDER_NAME)
        append("/")
        append(Constants.FILES_BANK_FOLDER_NAME)
    }

    // broadcast receiver from service when download is ended
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val data = intent?.getBooleanExtra("downloadComplete", false)
            if (data != null) {
                // Handle the received data
                if (data==true){
                    fbFilesRvAdapter.notifyDataSetChanged()
                    unregister(this@FirebaseFileManager.context)
                }
            }
        }
    }

    // interface to implement viewModel functions
    interface FbFileManagerViewModelFunctions{
        fun getFbFiles(path : String)
        fun setFilesListObserver(callback : (List<FileDriveItem>) -> Unit)
        fun addToRecentFiles(file : RecentOpenedFile)
    }
    interface FinishActivityListener{
        fun finishActivity()
    }

    // initialzing - creating the subjects bank folder if not existant in the app's package folder
    init {
        createFolder(Constants.FILES_BANK,
            (context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path + "/" + Constants.DOWNLOADS_FOLDER_NAME)
        )
    }

    // register broadcast receiver
    fun register(context: Context) {
        val filter = IntentFilter(Constants.DOWNLOAD_FILE_FROM_FIREBASE_ACTION)
        context.registerReceiver(receiver, filter)
    }

    // unregister broadcast receiver
    fun unregister(context: Context) {
        context.unregisterReceiver(receiver)
    }

    // function to set the viewModel interface functions
    fun setFbManagerVmInterface(fbFileManagerViewModelFunctions : FbFileManagerViewModelFunctions){
        fbFileManagerVmFunctions = fbFileManagerViewModelFunctions
        fbFileManagerVmFunctions.getFbFiles(currentPath)
        fbFileManagerVmFunctions.setFilesListObserver { filesList ->
            fbFilesRvAdapter.submitList(filesList)
        }
    }

    fun setFinishActivityListener(finishActivityListener: FinishActivityListener){
        this.finishActivityListener = finishActivityListener
    }

    // setting the recycler view
    fun setRecyclerView(recyclerView: RecyclerView) {
        fbFilesRvAdapter = FbFilesManagerRvAdapter(context, mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = fbFilesRvAdapter
    }

    // function to create a folder in a certain path with a certain name
    fun createFolder(newFolderName : String, directoryPath: String){
        val newFolderPath : String = buildString {
            append(directoryPath)
            append("/")
            append(newFolderName)
        }
        try {
            val newFolder = File(newFolderPath)
            if (!newFolder.exists()){
                if (newFolder.mkdirs()){
                    Toast.makeText(context, "le dossier a été crée", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "le fichier n'a pas été crée", Toast.LENGTH_SHORT).show()
                }
            }
        }catch (e: Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
        
    }

    // function to delete a clicked file
    private fun onDeleteBtnClicked(name: String, position: Int) {
        showDeleteWarningDialog(context, "Êtes-vous sûr(e) de vouloir supprimer ce fichier"){deleteFile(File(localSubjectsBankPath, name), position)}
    }

    // fun to delete a file
    private fun deleteFile(fileToDelete: File, position: Int) {
        try {
            if (fileToDelete.exists()) {
                val isDeleted = fileToDelete.delete()
                if (isDeleted) {
                    hideDeleteWarningDialog()
                    fbFilesRvAdapter.notifyItemChanged(position)
                    Toast.makeText(context, "fichier supprimé", Toast.LENGTH_SHORT).show()
                }
            }
        }catch (e : Exception){
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // fun to download a clicked file
    fun onDownloadBtnClicked(file : FileDriveItem, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.FOREGROUND_SERVICE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {

                val intent = Intent(context, DownloadService::class.java).apply {
                    Toast.makeText(context, file.downloadUrl, Toast.LENGTH_SHORT).show()
                    putExtra("downloadUrl", file.downloadUrl)
                    putExtra("fileName", file.name)
                    putExtra("downloadPath", localSubjectsBankPath)
                    putExtra("filePositionInRv", position)

                }
                // registering the broadcast receiver to receive download completed data
                register(context)
                context.startService(intent)

            } else {
                requestPermissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE)
            }
        } else {
            val intent = Intent(context, DownloadService::class.java).apply {
                putExtra("downloadUrl", file.downloadUrl)
                putExtra("fileName", file.name)
                putExtra("downloadPath", localSubjectsBankPath)
                putExtra("filePositionInRv", position)
            }
            // registering the broadcast receiver to receive download completed data
            register(context)
            context.startService(intent)
        }

    }


    // function to navigate to the provided path
    private fun navigateToFolder(newPath : String) {
        // showing the loading anim
        showLoading()
        fbFileManagerVmFunctions.getFbFiles(newPath)
        previousPath = currentPath
        currentPath = newPath
    }

    // function to show loading anim
    private fun showLoading() {
        fbFilesRvAdapter.isLoading = true
        fbFilesRvAdapter.notifyDataSetChanged()
    }
    // function to hide loading anim
    private fun hideLoading() {
        fbFilesRvAdapter.isLoading = false
        fbFilesRvAdapter.notifyDataSetChanged()
    }

    // Navigating back by one level
    fun navigateBackOneLevel() {
        // Split the path into segments
        if (currentPath != Constants.FILES_BANK_DRIVE_ID) {
            // Remove the last segment (folder3) and join the remaining segments
            navigateToFolder(previousPath)
        }else{
            finishActivityListener.finishActivity()
        }

    }

    // function to show a delete dialog
    fun showDeleteWarningDialog(context : Context, warningMessage : String, onConfirmed : () -> Unit) {
        mDeleteWarningDialog = Dialog(context)
        mDeleteWarningDialog.setContentView(R.layout.warning_dialog_layout)
        mDeleteWarningDialog.setCancelable(true)
        mDeleteWarningDialog.setCanceledOnTouchOutside(true)
        mDeleteWarningDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bindingDialog = WarningDialogLayoutBinding.inflate(LayoutInflater.from(context))
        mDeleteWarningDialog.setContentView(bindingDialog.root)
        bindingDialog.tvTitle.visibility = View.GONE
        bindingDialog.tvContent.text = warningMessage
        bindingDialog.imWarningExclamation.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
        bindingDialog.btnConfirm.setOnClickListener {
            onConfirmed.invoke()
        }
        bindingDialog.btnCancel.setOnClickListener {
            hideDeleteWarningDialog()
        }
        mDeleteWarningDialog.show()
    }

    // fun to hide the delete dialog
    fun hideDeleteWarningDialog() {
        if (mDeleteWarningDialog.isShowing){
            mDeleteWarningDialog.dismiss()
        }
    }

    // file manager recycler view adapter
    inner class FbFilesManagerRvAdapter(
        private val context: Context,
        private var firebaseFilesList : MutableList<FileDriveItem>)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        var isLoading : Boolean = true

        inner class FirebaseFilesRvViewHolder(binding : DownloadItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
            val fileName = binding.tvFileName
            val fileSize = binding.tvFileSize
            val fileDate = binding.tvDownloadDate
            val imFileType = binding.imExtensionType
            val llFileInfo = binding.llFileInfo
            val btnDelete = binding.btnDeleteFile
            val btnDownload = binding.btnDownloadFile

        }
        inner class NoDataViewHolder(binding: NoDataFirebaseFileManagerBinding) :
            RecyclerView.ViewHolder(binding.root){
        }
        inner class LoadingViewHolder(binding: LoadingItemLayoutBindingBinding) :
            RecyclerView.ViewHolder(binding.root){
        }

        override fun getItemViewType(position: Int): Int {
            return when(isLoading){
                    true->{Constants.LOADING_VIEW_TYPE}
                    false->{
                        if(firebaseFilesList.size == 0){
                            Constants.NO_DATA_VIEW_TYPE
                        }else{
                            Constants.DISPLAY_VIEW_TYPE
                        }
                    }
                }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when(viewType){
                Constants.DISPLAY_VIEW_TYPE->{
                    FirebaseFilesRvViewHolder(DownloadItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
                Constants.NO_DATA_VIEW_TYPE ->{
                    NoDataViewHolder(NoDataFirebaseFileManagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
                Constants.LOADING_VIEW_TYPE->{
                    LoadingViewHolder(LoadingItemLayoutBindingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
                else->{
                    FirebaseFilesRvViewHolder(DownloadItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
                }
            }
        }

        override fun getItemCount(): Int {
            return if (firebaseFilesList.isEmpty()){
                1
            }else{
                return firebaseFilesList.size
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when(holder){
                is FirebaseFilesRvViewHolder ->{
                    val currentFile = firebaseFilesList[position]
                    // Checking if the file is a folder(directory) or a file.
                    if (currentFile.type == ItemType.FOLDER){
                        // hiding delete button because folders can't be deleted
                        holder.btnDelete.visibility = View.GONE
                        holder.btnDownload.visibility = View.GONE
                        //setting folder size to nothing
                        holder.fileSize.text=""
                        holder.fileDate.text=Constants.FOLDER_WATERMARK
                        // setting the folder extension image and name
                        holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_folder))
                        // setting folder name
                        holder.fileName.text = currentFile.name.removeSuffix(Constants.FOLDER_SUFFIX)
                        // setting the folder on click listener to navigate to that folder
                        holder.imFileType.setOnClickListener {
                            navigateToFolder(currentFile.fileId)
                        }
                        holder.llFileInfo.setOnClickListener {
                            navigateToFolder(currentFile.fileId)
                        }
                    }else{
                        // setting the file extension image and name
                        if (currentFile.name.endsWith(Constants.PDF_EXTENSION)){
                            // file is pdf
                            holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_pdf))
                        }else{
                            // file is image
                            holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_image))
                        }
                        holder.fileName.text = currentFile.name
                        // checking if the file exists locally
                        if (fileExistsLocally(currentFile.name)){
                            // getting the date of the download/last modified
                            holder.fileDate.text = getFileDate(currentFile.name)
                            // hiding download button and showing delete button because the file exists locally
                            holder.btnDownload.visibility = View.GONE
                            // hide delete button
                            holder.btnDelete.visibility = View.VISIBLE
                            holder.btnDelete.setOnClickListener {
                                showDeleteWarningDialog(context,
                                    "Etes vous sur de vouloir supprimer ce fichier"
                                ) { deleteFile(getFileFromLocalStorage(currentFile), position) }
                            }
                            // setting the size of the file
                            holder.fileSize.text = getFileSize(currentFile.name)
                            // setting two views on click listener to open the pdf Viewer
                            if (currentFile.name.contains(Constants.PDF_EXTENSION)){
                                // file is pdf
                                holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_pdf))
                                holder.imFileType.setOnClickListener {
                                    openPdf(currentFile)

                                }
                                holder.llFileInfo.setOnClickListener {
                                    openPdf(currentFile)
                                }
                            }else{
                                // file is image
                                holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_image))
                                holder.imFileType.setOnClickListener {
                                    openImage(currentFile)
                                }
                                holder.llFileInfo.setOnClickListener {
                                    openImage(currentFile)
                                }
                            }

                        }else{
                            // showing download button and hiding delete button because the file doesn't exists locally
                            holder.btnDownload.visibility = View.VISIBLE
                            holder.btnDelete.visibility = View.GONE
                            holder.btnDownload.setOnClickListener {
                                Log.e("ehe", currentFile.downloadUrl.toString())
                                onDownloadBtnClicked(currentFile, position)
                            }
                            holder.llFileInfo.setOnClickListener {
                                Toast.makeText(context, "Télécharger le fichier pour l'ouvrir", Toast.LENGTH_SHORT).show()
                            }
                            holder.imFileType.setOnClickListener {
                                Toast.makeText(context, "Télécharger le fichier pour l'ouvrir", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                is NoDataViewHolder ->{
                }
                is LoadingViewHolder ->{
                }
            }

        }

        private fun getFileFromLocalStorage(currentFile: FileDriveItem): File {
            return File(localSubjectsBankPath, currentFile.name)
        }


        // getting the date of the last modification of the file saved locally
        private fun getFileDate(fileName: String): String {
            val file = File(localSubjectsBankPath, fileName)
            return FormattedTimestampDate().formatTimestamp(context, fbTimestamp = null ,javaTimestamp = Timestamp(file.lastModified()))
        }

        // submitting a list to thae adapter and displaying changes
        fun submitList(filesList : List<FileDriveItem>){

            firebaseFilesList = filesList.toMutableList()
            hideLoading()
        }

        // calculating the file size of a file
        private fun getFileSize(fileName: String): String {
            val file = File(localSubjectsBankPath, fileName)
            val fileSize = file.length()
            if (fileSize <= 0) {
                return "0 B"
            }

            val units = arrayOf("B", "KB", "MB", "GB", "TB")
            val digitGroups = (Math.log10(fileSize.toDouble()) / Math.log10(1024.0)).toInt()
            val formattedSize = "%.1f %s".format(fileSize / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
            return formattedSize
        }

        // checking if the file exists locally
        private fun fileExistsLocally(fileName:String) : Boolean{
            // converting the path from firebase path to its equivalent in the local package
            val file = File(localSubjectsBankPath, fileName)
            return file.exists()
        }

        private fun openPdf(currentFile: FileDriveItem) {
            fbFileManagerVmFunctions.addToRecentFiles(
                RecentOpenedFile(currentFile.name, System.currentTimeMillis())
            )
            val file: File = File(localSubjectsBankPath, currentFile.name)
            val uri: Uri = FileProvider.getUriForFile(context, "com.tp.bacprep.fileprovider", file)
            val intent = Intent(context, FileViewerActivity::class.java)
            intent.putExtra(Constants.FILE_VIEWER_TYPE, Constants.OPEN_PDF)
            intent.putExtra("Uri_file_top_open", uri)
            context.startActivity(intent)
        }
        private fun openImage(currentFile : FileDriveItem) {
            fbFileManagerVmFunctions.addToRecentFiles(
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
