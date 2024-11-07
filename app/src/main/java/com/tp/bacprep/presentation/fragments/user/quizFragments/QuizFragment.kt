package com.tp.bacprep.presentation.fragments.user.quizFragments

import android.content.res.ColorStateList
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.room.Room
import com.tp.bacprep.R
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.databinding.FragmentQuizBinding
import com.tp.bacprep.domain.models.Quiz
import com.tp.bacprep.domain.models.QuizScore
import com.tp.bacprep.presentation.viewmodels.QuizViewModel
import com.tp.bacprep.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizFragment : Fragment() {
    private val quizViewModel  : QuizViewModel by activityViewModels()
    private lateinit var quiz : Quiz
    private lateinit var currentQst : Triple<String, List<String>?, String>
    private var binding : FragmentQuizBinding? = null
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
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonsFunctionality()

        // setting quiz details on UI
        retrieveAndSetQuiz()
        // setting the radio buttons
        setRadioButtonsFunctionality()
    }

    private fun setButtonsFunctionality() {
        binding?.apply {
            //setting skip button functionality
            btnSkip.setOnClickListener {
                setCurrentQst(quizViewModel.currentQstIndex, skipped = true, answeredCorrectly = false)
            }
            // setting submit button functionality
            btnSubmit.setOnClickListener {
                setSubmitButtonFunctionality()
            }
        }
    }

    private fun retrieveAndSetQuiz() {
        binding?.apply {
            if (arguments != null) {
                // retrieving quiz from arguments using the inline function to remove deprecation
                quiz = arguments?.parcelable("Quiz") ?: Quiz(listOf(), 0L, hashMapOf())
                // observing quizRemainingTime and setting the time in the ui
                quizViewModel.quizRemainingTime.observe(viewLifecycleOwner) { millisUntilFinished ->
                    if (millisUntilFinished == 0L) {
                        val finalScore = ((score.toDouble() / quiz.questions.size) * 100).toInt()

                        quizViewModel.setFinalScore(finalScore)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.frag_fade_in, R.anim.frag_fade_out)
                            .replace(R.id.frag_container, QuizResultFragment())
                            .commit()
                    }
                    val minutes = (millisUntilFinished / 1000) / 60
                    val seconds = (millisUntilFinished / 1000) % 60
                    binding?.lpiQuizProgress?.progress = (quiz.time - millisUntilFinished).toInt()
                    binding?.tvTime?.text = buildString {
                        append("$minutes")
                        append(":")
                        append(String.format("%02d", seconds))
                    }
                }
                // shuffling questions of the qui
                quiz.questions = quiz.questions.shuffled()
                //starting the quiz time
                quizViewModel.startQuizTime(quiz.time)
                // setting the first question
                setCurrentQst(
                    quizViewModel.currentQstIndex,
                    skipped = null,
                    answeredCorrectly = null
                )
                // setting progress indicator max value
                lpiQuizProgress.max = quiz.time.toInt()
                when(quiz.type[Constants.QUIZ_TYPE]){
                    Constants.DATES_QUIZ->{
                        tvQuizType.text = buildString { append(getString(R.string.dates_quiz)) }
                    }
                    Constants.TERMES_QUIZ->{
                        tvQuizType.text = buildString { append(getString(R.string.termes_quiz)) }

                    }
                }

            }
        }
    }

    // extension function to get parcelable
    private inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

    private fun setRadioButtonsFunctionality() {
        binding?.apply {
            for (rb in listOf(rbA, rbB, rbC, rbD)){
                rgChoices.setOnCheckedChangeListener { _, _ ->
                    val changedRb =
                        requireView().findViewById<RadioButton>(rgChoices.checkedRadioButtonId)
                            ?: null
                    if (changedRb != null){
                            if (changedRb.isChecked) {
                                activateSubmitButton()
                            } else {
                                deactivateSubmitButton()
                            }
                    }
                }
            }
        }
    }

    private fun deactivateSubmitButton() {
        binding?.btnSubmit?.apply {
            isClickable = false
            isEnabled = false
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.darker_gray
                )
            )
            iconTint = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.darker_gray
                )
            )
        }
    }

    private fun activateSubmitButton() {
        binding?.btnSubmit?.apply {
            isClickable = true
            isEnabled = true
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            iconTint = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.white
                )
            )
        }
    }

    private fun setSubmitButtonFunctionality() {
        binding?.apply {
            val userAnswer = root.findViewById<RadioButton>(rgChoices.checkedRadioButtonId).text.toString()
            if (userAnswer == answer){
                setCurrentQst(quizViewModel.currentQstIndex, skipped=false, answeredCorrectly = true)
            }else{
                setCurrentQst(quizViewModel.currentQstIndex, skipped=false, answeredCorrectly = false)
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
            tvQstCount.text = buildString {
                append("${currentQstIndex+1}")
                append("/")
                append("${quiz.questions.size}")
            }
            if (currentQstIndex < quiz.questions.size) {
                tvQuizQuestion.text = quiz.questions[currentQstIndex].first
                rgChoices.clearCheck()
                currentQst = quiz.questions[currentQstIndex]
                val optionsRbs = listOf(rbA, rbB, rbC, rbD)
                val choicesList = mutableListOf(currentQst.third)
                val options = currentQst.second!!.toMutableList()
                options.shuffle()
                for (i in 0..2){
                    choicesList.add(options[i])
                }
                choicesList.shuffle()
                for ((index, rb) in optionsRbs.withIndex()) {
                    rb.text = choicesList[index]
                    answer = currentQst.third
                }
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