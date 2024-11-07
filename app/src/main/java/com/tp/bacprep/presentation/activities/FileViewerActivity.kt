package com.tp.bacprep.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.bumptech.glide.Glide
import com.tp.bacprep.databinding.ActivityPdfViewerBinding
import com.tp.bacprep.util.Constants

class FileViewerActivity : AppCompatActivity() {
    private var binding : ActivityPdfViewerBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.apply {
        if (intent != null){
            val viewType = intent.getStringExtra(Constants.FILE_VIEWER_TYPE)
            if (viewType == Constants.OPEN_PDF){
                pdfView.visibility = View.VISIBLE
                imageView.visibility = View.GONE
                val fileUri = intent.parcelable<Uri>("Uri_file_top_open")
                if (fileUri!=null){
                    binding?.pdfView?.fromUri(fileUri)?.load()
                }
            }
            if (viewType==Constants.OPEN_IMAGE){
                pdfView.visibility = View.GONE
                imageView.visibility = View.VISIBLE
                val imageUri = intent.parcelable<Uri>(Constants.IMAGE_URI)
                Glide.with(this@FileViewerActivity)
                    .load(imageUri)
                    .into(imageView)
            }
            }
        }

    }
    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

}
