package com.tp.bacprep.presentation.adapters.recyclerviewsAdapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.FileViewerActivity
import com.tp.bacprep.databinding.RecentFileItemLayoutBinding
import com.tp.bacprep.domain.models.RecentOpenedFile
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.FormattedTimestampDate
import java.io.File
import java.sql.Timestamp

class RecentFilesRvAdapter(private val context : Context, private var recentFilesList : List<RecentOpenedFile>) :  RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class RecentFileAdapterViewHolder(binding : RecentFileItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvFileName = binding.tvFileName
        val imFileType = binding.imExtensionType
        val tvTime = binding.tvTime
        val btnOpenFile = binding.btnOpen
    }
    class EmptySpaceViewHolder(view : View) :
    RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        return if(recentFilesList.isEmpty()){
            Constants.EMPTY_SPACE
        } else{
            if (position == recentFilesList.size) {
                Constants.EMPTY_SPACE
            } else {
                Constants.DISPLAY_VIEW_TYPE
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            Constants.DISPLAY_VIEW_TYPE->{RecentFileAdapterViewHolder(RecentFileItemLayoutBinding
                .inflate(LayoutInflater.from(parent.context), parent, false))
            }
            Constants.EMPTY_SPACE ->{
                // Create an empty Space view programmatically
                val emptySpaceView = Space(parent.context)
                val heightInDp = 100 // Adjust the height of the empty space as needed
                val heightInPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    heightInDp.toFloat(),
                    parent.resources.displayMetrics
                ).toInt()
                emptySpaceView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightInPx)
                EmptySpaceViewHolder(emptySpaceView)
            }
            else ->{
                // Create an empty Space view programmatically
                val emptySpaceView = Space(parent.context)
                val heightInDp = 100 // Adjust the height of the empty space as needed
                val heightInPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    heightInDp.toFloat(),
                    parent.resources.displayMetrics
                ).toInt()
                emptySpaceView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightInPx)
                EmptySpaceViewHolder(emptySpaceView)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (recentFilesList.isEmpty()){
            1
        }else{
            recentFilesList.size+1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecentFileAdapterViewHolder){
            val currentFile = recentFilesList[position]
            if (currentFile.name.endsWith(".pdf")){
                holder.tvFileName.text = currentFile.name.removeSuffix(Constants.PDF_EXTENSION)
                holder.btnOpenFile.setOnClickListener {
                    openPdf(currentFile.name)
                }
                holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_pdf))
                holder.tvTime.text = FormattedTimestampDate().formatTimestamp(context,fbTimestamp = null ,javaTimestamp = Timestamp(currentFile.time))
            }
            if (currentFile.name.endsWith(".png") || currentFile.name.endsWith(".jpg") || currentFile.name.endsWith(".jpeg")){
                holder.tvFileName.text = currentFile.name
                holder.btnOpenFile.setOnClickListener {
                    openImage(currentFile.name)
                }
                holder.imFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_extension_image))
                holder.tvTime.text = FormattedTimestampDate().formatTimestamp(context, fbTimestamp = null ,javaTimestamp = Timestamp(currentFile.time))
            }
            holder.itemView.setOnClickListener {
            }
        }


    }

    private fun openImage(imageName : String) {
        val downloadsPath = buildString {
            append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: "")
            append("/")
            append(Constants.DOWNLOADS_FOLDER_NAME)
        }

        val subjectsBankPath = buildString {
            append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: "")
            append("/")
            append(Constants.DOWNLOADS_FOLDER_NAME)
            append("/")
            append(Constants.FILES_BANK_FOLDER_NAME)
        }
        try {
            val imageFile = if (File(downloadsPath, imageName).exists()) File(downloadsPath, imageName) else File(subjectsBankPath, imageName) // subjectsBankPath should be the absolute path to your file
            if (imageFile.exists()){
                val imageUri = FileProvider.getUriForFile(context, context.getString(R.string.recent_files_file_provider_authority), imageFile)
                val intent = Intent(context, FileViewerActivity::class.java)
                intent.putExtra(Constants.FILE_VIEWER_TYPE, Constants.OPEN_IMAGE)
                intent.putExtra(Constants.IMAGE_URI, imageUri)
                context.startActivity(intent)
            }else{
                Toast.makeText(context, context.getString(R.string.t_l_chargez_le_fichier_pour_l_ouvrir), Toast.LENGTH_SHORT).show()
            }


        }catch (e : Exception){
            Log.e("open file error", e.message.toString())
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()

        }
    }

    private fun openPdf(fileName: String) {
        val downloadsPath = buildString {
            append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: "")
            append("/")
            append(Constants.DOWNLOADS_FOLDER_NAME)
        }

        val subjectsBankPath = buildString {
            append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: "")
            append("/")
            append(Constants.DOWNLOADS_FOLDER_NAME)
            append("/")
            append(Constants.FILES_BANK_FOLDER_NAME)
        }
        try {
            val file = if (File(downloadsPath, fileName).exists()) File(downloadsPath, fileName) else File(subjectsBankPath, fileName)
            if (file.exists()){
                val uri: Uri = FileProvider.getUriForFile(context, context.getString(R.string.recent_files_file_provider_authority)
                    , file)
                val intent = Intent(context, FileViewerActivity::class.java)
                intent.putExtra(Constants.FILE_VIEWER_TYPE, Constants.OPEN_PDF)
                intent.putExtra("Uri_file_top_open", uri)
                context.startActivity(intent)
            }else{
                Toast.makeText(context,context.getString(R.string.t_l_chargez_le_fichier_pour_l_ouvrir), Toast.LENGTH_SHORT).show()
            }

        }catch (e : Exception){
            Log.e("open file error", e.message.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(recentOpenedFilesList: List<RecentOpenedFile>?) {
        if (recentOpenedFilesList != null){
            this.recentFilesList = recentOpenedFilesList
            notifyDataSetChanged()
        }
    }
}