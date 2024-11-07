package com.tp.bacprep.presentation.adapters.recyclerviewsAdapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tp.bacprep.R
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.databinding.LayoutComingSoonBinding
import com.tp.bacprep.databinding.QuizItemLayoutBinding
import com.tp.bacprep.domain.models.Quiz
import com.tp.bacprep.domain.models.QuizScore
import com.tp.bacprep.presentation.fragments.user.quizFragments.QuizzesFragment
import com.tp.bacprep.presentation.viewmodels.BaseViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.MultiLangStrings
import com.tp.bacprep.util.SpannableText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizzesRvAdapter(private var context: Context,
                       private val quizzesFragment : QuizzesFragment,
                       private val quizzesList : List<Quiz>,
                       private val sharedViewModel: BaseViewModel,
                       private val db : RoomDb) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var onStartQuizClickedListener : OnStartQuizClickedListener

    companion object{
        const val REGULAR_VIEW_TYPE = 0
        const val EMPTY_VIEW_TYPE = 1
    }

    class QuizViewHolder(binding : QuizItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val imQuiz = binding.imQuizImage
        val tvQuizType = binding.quizType
        val tvSemester = binding.tvSemester
        val btnStartQuiz = binding.btnStartQuiz
        val tvQstCount = binding.tvQuestionCount
        val tvHighestScore = binding.tvHighestScore
    }

    interface OnStartQuizClickedListener{
        fun onStartQuizClicked(quiz: Quiz)
    }

    fun setOnStartQuizClickedListener(onStartQuizClickedListener : OnStartQuizClickedListener){
        this.onStartQuizClickedListener = onStartQuizClickedListener
    }

    class EmptyViewHolder(binding : LayoutComingSoonBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         return when(viewType){
            REGULAR_VIEW_TYPE ->{
                QuizViewHolder(QuizItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            EMPTY_VIEW_TYPE ->{
                EmptyViewHolder(LayoutComingSoonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
             else ->{
                 EmptyViewHolder(LayoutComingSoonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
             }
        }
    }

    override fun getItemCount(): Int {
        return quizzesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (quizzesList.isEmpty()){
            EMPTY_VIEW_TYPE
        }else{
            REGULAR_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when{
            holder is QuizViewHolder ->{
                val currentQuiz = quizzesList[position]
                val module = currentQuiz.type[Constants.MODULE] ?: ""
                val semester =currentQuiz.type[Constants.QUIZ_SEMESTER] ?: ""
                val type =currentQuiz.type[Constants.QUIZ_TYPE] ?: ""
                holder.imQuiz.setImageDrawable(getCorrespondingQuizImage(module, type))
                holder.tvQuizType.text = type
                holder.tvSemester.text = semester
                holder.tvQstCount.text = buildString {
                    append(currentQuiz.questions.size.toString())
                    append(" questions")
                }
                sharedViewModel.viewModelScope.launch(Dispatchers.IO) {
                    val highestScore = db.scoresDao.getScores(module, semester, type).maxByOrNull { it.score } ?: QuizScore(0, 0, "", "", "")
                    quizzesFragment.requireActivity().runOnUiThread {
                        val spannableText = SpannableText(context)

                        holder.tvHighestScore.text = spannableText.spanQuizScoreText(buildString {
                            append(context.resources.getString(R.string.meilleur_score))
                            append(" ")
                            append(highestScore.score.toString())
                            append("%")
                        })
                    }
                }
                holder.btnStartQuiz.setOnClickListener {
                    if (::onStartQuizClickedListener.isInitialized){
                        onStartQuizClickedListener.onStartQuizClicked(currentQuiz)
                    }
                }
            }
        }


    }

    private fun getCorrespondingQuizImage(module: String, type: String): Drawable? {
        return when(module){
            MultiLangStrings(context).HISTOIRE ->{
                when(type){
                    MultiLangStrings(context).PERSO_QUIZ ->{
                        ContextCompat.getDrawable(quizzesFragment.requireContext(), R.drawable.im_personalities_quiz)
                    }
                    MultiLangStrings(context).DATES_QUIZ ->{
                        ContextCompat.getDrawable(quizzesFragment.requireContext(), R.drawable.im_dates_quiz)

                    }
                    MultiLangStrings(context).TERMES_QUIZ ->{
                        ContextCompat.getDrawable(quizzesFragment.requireContext(), R.drawable.im_his_termes_quiz)

                    }
                    else ->{
                        ContextCompat.getDrawable(quizzesFragment.requireContext(), R.drawable.im_personalities_quiz)
                    }
                }
            }
            MultiLangStrings(context).GEOGRAPHIE ->{
                when(type){
                    MultiLangStrings(context).TERMES_QUIZ ->{
                        ContextCompat.getDrawable(quizzesFragment.requireContext(), R.drawable.im_geo_termes_quiz)

                    }
                    else ->{
                        ContextCompat.getDrawable(quizzesFragment.requireContext(), R.drawable.im_personalities_quiz)
                    }
                }
            }
            else ->{
                ContextCompat.getDrawable(quizzesFragment.requireContext(), R.drawable.im_personalities_quiz)
            }
        }
    }
}