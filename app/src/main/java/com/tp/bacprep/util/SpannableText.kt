package com.tp.bacprep.util

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import com.tp.bacprep.R

class SpannableText(private val context : Context) {

    fun spanQuizScoreText(text : String) : SpannableStringBuilder{
        val spannableText = SpannableStringBuilder(text)

        val startIndex = text.indexOf(":") +1
        val endIndex = text.length

        val score = text.substring(text.indexOf(":")+2).removeSuffix("%").toInt()
        when{
            score in 0..50 -> {
                val spanColor = ForegroundColorSpan(ContextCompat.getColor(context, R.color.red))
                val spanStyle = StyleSpan(Typeface.BOLD)
                spannableText.setSpan(spanColor, startIndex, endIndex, 0)
                spannableText.setSpan(spanStyle, startIndex, endIndex, 0)
            }
            score in 51..79 -> {
                val spanColor = ForegroundColorSpan(ContextCompat.getColor(context, R.color.sea_blue))
                val spanStyle = StyleSpan(Typeface.BOLD)
                spannableText.setSpan(spanColor, startIndex, endIndex, 0)
                spannableText.setSpan(spanStyle, startIndex, endIndex, 0)
            }
            score in 80..95-> {
                val spanColor = ForegroundColorSpan(ContextCompat.getColor(context, R.color.green))
                val spanStyle = StyleSpan(Typeface.BOLD)
                spannableText.setSpan(spanColor, startIndex, endIndex, 0)
                spannableText.setSpan(spanStyle, startIndex, endIndex, 0)
            }
            score > 95 -> {
                val spanColor = ForegroundColorSpan(ContextCompat.getColor(context, R.color.gold))
                val spanStyle = StyleSpan(Typeface.BOLD)
                spannableText.setSpan(spanColor, startIndex, endIndex, 0)
                spannableText.setSpan(spanStyle, startIndex, endIndex, 0)
            }
        }

        return spannableText

    }
}