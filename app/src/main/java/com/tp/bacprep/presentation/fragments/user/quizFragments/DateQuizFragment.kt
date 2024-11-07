package com.tp.bacprep.presentation.fragments.user.quizFragments

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.room.Room
import com.tp.bacprep.R
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.databinding.FragmentDateQuizBinding
import com.tp.bacprep.domain.models.Quiz
import com.tp.bacprep.domain.models.QuizScore
import com.tp.bacprep.presentation.viewmodels.QuizViewModel
import com.tp.bacprep.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DateQuizFragment : Fragment() {
    private val quizViewModel  : QuizViewModel by activityViewModels()
    private lateinit var quiz : Quiz
    private lateinit var currentQst : Triple<String, List<String>?, String>
    private var binding : FragmentDateQuizBinding? = null
    private lateinit var answer : String
    private var score = 0
    private var incorrectAnswersNum = 0
    private var correctAnswersNum = 0
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDateQuizBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setting UI quiz detail
        retrieveAndSetQuiz()
        // set Edit Texts functionality
        setEditTextsFunctionality()

        binding?.apply {
            btnSkip.setOnClickListener {
                setCurrentQst(quizViewModel.currentQstIndex, skipped=true, answeredCorrectly = false)
            }
            btnSubmit.setOnClickListener {
                setSubmitBtnFunctionality()
            }
        }
    }

    private fun setSubmitBtnFunctionality() {
        binding?.apply {
            val userAnswer = when(etAnswerEnd.visibility){
                View.VISIBLE->{
                    buildString {
                        append(etAnswer.text.toString())
                        append(" - ")
                        append(etAnswerEnd.text.toString())
                    }
                }
                View.GONE->{
                    etAnswer.text.toString()
                }

                else -> {
                    etAnswer.text.toString()
                }
            }
            if (userAnswer == answer){
                setCurrentQst(quizViewModel.currentQstIndex, skipped = false, answeredCorrectly = true)
            }else{
                setCurrentQst(quizViewModel.currentQstIndex, skipped = false, answeredCorrectly = false)
            }
        }

    }

    private fun setEditTextsFunctionality() {
        binding?.apply {
            etAnswer.addTextChangedListener {editable ->
                btnSubmit.apply {
                    when (etAnswer.maskString) {
                        "####" -> {
                            if (editable.toString().length>=4 && etAnswerEnd.visibility == View.GONE){
                                isClickable = true
                                isEnabled = true
                                backgroundTintList = ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.yellow
                                    )
                                )
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.yellow
                                    )
                                )

                            }else{
                                isClickable = false
                                isEnabled = false
                                backgroundTintList = ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.darker_gray
                                    )
                                )
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.darker_gray
                                    )
                                )
                            }
                        }
                        "##/##/####" -> {
                            Toast.makeText(requireContext(), editable.toString().length.toString(), Toast.LENGTH_SHORT).show()
                            if (editable.toString().length>=10 && llEndDate.visibility == View.GONE){
                                Toast.makeText(requireContext(), "h1", Toast.LENGTH_SHORT).show()

                                isClickable = true
                                isEnabled = true
                                backgroundTintList = ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.yellow
                                    )
                                )
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.yellow
                                    )
                                )

                            }else{
                                isClickable = false
                                isEnabled = false
                                backgroundTintList = ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.darker_gray
                                    )
                                )
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.darker_gray
                                    )
                                )
                            }
                        }
                    }

                }

            }
            etAnswerEnd.addTextChangedListener{ editable->

                btnSubmit.apply {
                    when{
                        etAnswerEnd.maskString == "####"->{
                            if (editable.toString().length>=4 && etAnswer.text.toString().length>=4){
                                isClickable = true
                                isEnabled = true
                                backgroundTintList = ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.yellow
                                    )
                                )
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.yellow
                                    )
                                )

                            }else{
                                isClickable = false
                                isEnabled = false
                                backgroundTintList = ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.darker_gray
                                    )
                                )
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.darker_gray
                                    )
                                )
                            }
                        }
                        etAnswer.maskString == "##/##/####"->{
                            if (editable.toString().length>=10 && etAnswer.text.toString().length>=10){
                                isClickable = true
                                isEnabled = true
                                backgroundTintList = ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.yellow
                                    )
                                )
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.yellow
                                    )
                                )

                            }else{
                                isClickable = false
                                isEnabled = false
                                backgroundTintList = ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.darker_gray
                                    )
                                )
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.darker_gray
                                    )
                                )
                            }
                        }

                    }
                }

            }
        }
    }

    private inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }
    private fun retrieveAndSetQuiz() {
        binding?.apply {
            if (arguments!=null){
                quiz = arguments?.parcelable("Quiz")!!
                quizViewModel.quizRemainingTime.observe(viewLifecycleOwner){    millisUntilFinished ->
                    if (millisUntilFinished == 0L){
                        val finalScore = ((score/quiz.questions.size)*100)
                        quizViewModel.setFinalScore(finalScore)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.frag_fade_in, R.anim.frag_fade_out)
                            .replace(R.id.frag_container, QuizResultFragment())
                            .commit()
                    }
                    val minutes = (millisUntilFinished / 1000)/60
                    val seconds = (millisUntilFinished / 1000)%60
                    binding?.lpiQuizProgress?.progress  =  (quiz.time - millisUntilFinished).toInt()
                    binding?.tvTime?.text  =  buildString {
                        append("$minutes")
                        append(":")
                        append(String.format("%02d", seconds))
                    }
                }
                binding?.lpiQuizProgress?.max = quiz.time.toInt()
                Toast.makeText(requireContext(), "${quiz.time}", Toast.LENGTH_SHORT).show()
                quiz.questions = quiz.questions.shuffled()
                quizViewModel.startQuizTime(quiz.time)
                setCurrentQst(quizViewModel.currentQstIndex, skipped = false, answeredCorrectly = false)
            }

        }

    }

    private fun updateScore(skipped: Boolean, answeredCorrectly: Boolean) {
        if (skipped) {
            score -= 1
        }
        if (answeredCorrectly) {
            score++
            correctAnswersNum++
        } else {
            incorrectAnswersNum++
        }
    }

    private fun setCurrentQst(currentQstIndex: Int, skipped: Boolean?, answeredCorrectly: Boolean?) {
        if (skipped != null && answeredCorrectly != null) {
            updateScore(skipped, answeredCorrectly)
        }
            binding?.apply {
            etAnswer.text?.clear()
            etAnswerEnd.text?.clear()
            tvQstCount.text = buildString {
                append("${currentQstIndex+1}")
                append("/")
                append("${quiz.questions.size}")
            }
            if (currentQstIndex < quiz.questions.size) {
                tvQuizQuestion.text = quiz.questions[currentQstIndex].first
                currentQst = quiz.questions[currentQstIndex]

                if (currentQst.third.contains("-")){
                    llEndDate.visibility = View.VISIBLE
                    tvStartDateIndicator.visibility = View.VISIBLE
                    if (currentQst.third.length in 5..11){
                        etAnswer.setMask("####")
                        etAnswer.hint = getString(R.string.annee)
                        etAnswerEnd.setMask("####")
                        etAnswerEnd.hint = getString(R.string.annee)
                    }else{
                        etAnswer.setMask("##/##/####")
                        etAnswer.hint = getString(R.string.jour_mois_annee)
                        etAnswerEnd.setMask("##/##/####")
                        etAnswerEnd.hint = getString(R.string.jour_mois_annee)
                    }
                }else{
                    llEndDate.visibility = View.GONE
                    tvStartDateIndicator.visibility = View.GONE
                    if (currentQst.third.length==4){
                        etAnswer.setMask("####")
                        etAnswer.hint = getString(R.string.annee)
                    }else{
                        etAnswer.setMask("##/##/####")
                        etAnswer.hint = getString(R.string.jour_mois_annee)
                        etAnswerEnd.setMask("##/##/####")
                        etAnswerEnd.hint = getString(R.string.jour_mois_annee)
                    }
                }
                answer = currentQst.third
            }else{
                val finalScore :Int = ((score.toDouble()/quiz.questions.size)*100).toInt()
                quizViewModel.setAnswered(correctAnswersNum)
                quizViewModel.setNotAnswered(incorrectAnswersNum)
                quizViewModel.setFinalScore(finalScore)
                saveScoreInRoomDb(finalScore, quiz.type)
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.frag_fade_in, R.anim.frag_fade_out)
                    .replace(R.id.frag_container, QuizResultFragment())
                    .commit()
            }
        }
        quizViewModel.incrementCurrentQstIndex()
    }
    private fun saveScoreInRoomDb(finalScore: Int, type: HashMap<String, String>) {
        val db =  Room.databaseBuilder(
            requireContext().applicationContext,
            RoomDb::class.java, // Replace with your actual Room database class
            "local_room_database" // Replace with your desired database name
        )
            .fallbackToDestructiveMigration()
            .build()
        val module : String = type[Constants.MODULE] ?: ""
        val semester : String = type[Constants.QUIZ_SEMESTER] ?: ""
        val quizType : String = type[Constants.QUIZ_TYPE] ?: ""
        coroutineScope.launch(Dispatchers.IO) {
            db.scoresDao.addScore(QuizScore(0, finalScore, module, semester, quizType))
        }
    }


}