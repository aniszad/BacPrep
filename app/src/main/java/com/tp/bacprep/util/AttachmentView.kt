package com.tp.bacprep.util

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.tp.bacprep.R

class AttachmentView(context : Context, attrs : AttributeSet?) : MaterialCardView(context, attrs) {

    private var textView : TextView
    var downloadAttachBtn : ImageButton
    private var fileTypeButton : ImageView
    var onRemoveClickListener : RemoveButtonClickListener? = null
    interface RemoveButtonClickListener{
        fun onRemoveButtonClickListener(position: Int, textView: TextView)
    }
    init {
        LayoutInflater.from(context).inflate(R.layout.attachment_item_layout, this, true)
        textView = findViewById(R.id.attachment_title)
        downloadAttachBtn = findViewById(R.id.btn_download_attachment)
        fileTypeButton = findViewById(R.id.im_file_type)
        downloadAttachBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_close))
        downloadAttachBtn.setOnClickListener {
            onRemoveClickListener?.onRemoveButtonClickListener(getPosition(), textView)
        }
        setMargins(0,0,0,0)
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun setText(text : String){
        this.textView.text = text
        if (text.endsWith(".pdf")){
            fileTypeButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_pdf))
        }else{
            if (text.endsWith(".jpg") || text.endsWith(".png") || text.endsWith("jpeg")){
                fileTypeButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_image))
            }
        }
    }
    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(left, top, right, bottom)
        this.layoutParams = layoutParams
    }

    private fun getPosition(): Int {
        val parent = parent as? ViewGroup
        return parent?.indexOfChild(this) ?: -1
    }

}