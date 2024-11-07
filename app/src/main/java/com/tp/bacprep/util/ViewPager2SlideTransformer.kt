package com.tp.bacprep.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ViewPager2SlideTransformer : ViewPager2.PageTransformer{
        override fun transformPage(page: View, position: Float) {
            // Modify the transformation based on the 'position' parameter
            page.translationX = -position * page.width
            page.alpha = 1 - abs(position)
        }
}