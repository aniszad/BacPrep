package com.tp.bacprep.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

open class RobotoTextView(context : Context, attrs : AttributeSet?) : AppCompatTextView(context, attrs) {
    init {
        typeface = Typeface.createFromAsset(context.assets, "RobotoRegular.ttf")
    }
}