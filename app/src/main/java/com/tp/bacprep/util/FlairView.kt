package com.tp.bacprep.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.tp.bacprep.R
import com.tp.bacprep.databinding.FlairItemLayoutBinding

class FlairView(context : Context, attrs : AttributeSet?) : MaterialCardView(context, attrs) {
    private var textView : TextView
    private var cardView : MaterialCardView
    init {
        val binding = FlairItemLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        radius = 30F
        setMargins(3, 0, 3, 0)
        textView = findViewById(R.id.flair_title)
        cardView = binding.flairCard

    }
    fun setText(text : String){
        textView.text = text
            when (text) {
            context.resources.getString(R.string.semester_1) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_brown))
            }
            context.resources.getString(R.string.semester_2) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_brown))
            }
            context.resources.getString(R.string.semester_3) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_brown))
            }
            context.resources.getString(R.string.science_filiere) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_green))
            }
            context.resources.getString(R.string.math_filiere) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_green))
            }
            context.resources.getString(R.string.langues_filiere) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_green))
            }
            context.resources.getString(R.string.lettre_filiere) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_green))
            }
            context.resources.getString(R.string.gestion_filiere) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_green))
            }
            context.resources.getString(R.string.cours) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_blue))
            }
            context.resources.getString(R.string.exercices) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_blue))
            }
            context.resources.getString(R.string.sujets) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_blue))
            }
            context.resources.getString(R.string.resume) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_blue))
            }
            context.resources.getString(R.string.math_tech_branch_mec) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_green))
            }
            context.resources.getString(R.string.math_tech_branch_ele) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_green))
            }
            context.resources.getString(R.string.math_tech_branch_civ) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_green))
            }
            context.resources.getString(R.string.math_tech_branch_meth) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_green))
            }
            context.resources.getString(R.string.math) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.science) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.physique) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.arabe) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.anglais) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.francais) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.s_islamique) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.histoire) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.geographie) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.philo) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.steel_gray))
            }
            context.resources.getString(R.string.conseils) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_red))
            }
            context.resources.getString(R.string.information) -> {
                setFlairBackgColor(ContextCompat.getColor(context, R.color.flair_red))
            }
            // Add more cases for other checkboxes as needed
        }

    }

    fun setFlairBackgColor(color : Int){
        cardView.setCardBackgroundColor(color)
    }


    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(left, top, right, bottom)
        this.layoutParams = layoutParams
    }

}