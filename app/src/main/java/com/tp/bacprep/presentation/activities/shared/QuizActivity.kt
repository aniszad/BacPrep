package com.tp.bacprep.presentation.activities.shared

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.databinding.ActivityQuizBinding
import com.tp.bacprep.domain.models.Quiz
import com.tp.bacprep.presentation.fragments.user.quizFragments.DateQuizFragment
import com.tp.bacprep.presentation.fragments.user.quizFragments.QuizFragment
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.MultiLangStrings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : BaseActivity() {
    private var binding : ActivityQuizBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSystemBarsColors()
        // retrieving the quiz and setting the quiz format
        setQuiz()
    }

    private fun setSystemBarsColors() {
        setStatusBarColor(ContextCompat.getColor(this@QuizActivity, R.color.blackish), false)
        setNavigationBarColor(ContextCompat.getColor(this@QuizActivity, R.color.blackish))
    }

    private fun setQuiz() {
        val quiz = intent.parcelable("Quiz") ?: Quiz(listOf(), 0L, hashMapOf())
        when(quiz.type[Constants.QUIZ_TYPE]){
            MultiLangStrings(this@QuizActivity).PERSO_QUIZ->{
                val quizFrag = QuizFragment()
                quizFrag.arguments = Bundle().apply {
                    putParcelable("Quiz", quiz)
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frag_container, quizFrag)
                    .commit()
            }
            MultiLangStrings(this@QuizActivity).DATES_QUIZ->{
                val quizFrag = DateQuizFragment()
                quizFrag.arguments = Bundle().apply {
                    putParcelable("Quiz", quiz)
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frag_container, quizFrag)
                    .commit()
            }
            MultiLangStrings(this@QuizActivity).TERMES_QUIZ->{
                val quizFrag = QuizFragment()
                quizFrag.arguments = Bundle().apply {
                    putParcelable("Quiz", quiz)
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frag_container, quizFrag)
                    .commit()
            }
        }
    }


    // extension function to get parcelable
    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

}