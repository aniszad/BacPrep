package com.tp.bacprep.presentation.fragments.user.quizFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tp.bacprep.R
import com.tp.bacprep.databinding.FragmentResultQuizBinding
import com.tp.bacprep.presentation.viewmodels.QuizViewModel

class QuizResultFragment : Fragment() {
    private var binding: FragmentResultQuizBinding? = null
    private val quizViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultQuizBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            btnEndQuiz.setOnClickListener {
                requireActivity().finish()
            }
            val finalScore = quizViewModel.score
            when {
                finalScore == 100 -> {
                    imResultMedal.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.im_trophy
                        )
                    )
                    lavConfettiBackg.playAnimation()
                }

                finalScore >= 90 -> {
                    imResultMedal.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.icon_gold_medal
                        )
                    )
                    lavConfettiBackg.playAnimation()
                }

                finalScore >= 75 -> {
                    imResultMedal.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.icon_silver_medal
                        )
                    )
                }

                finalScore >= 51 -> {
                    imResultMedal.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.icon_bronze_medal
                        )
                    )
                }

                else -> {
                    imResultMedal.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.icon_fail_medal
                        )
                    )
                    tvQuizResultTitle.text = buildString {
                        append(getString(R.string.echoué))
                    }
                }
            }
            tvCorrectAnswers.text = buildString {
                append("${quizViewModel.answered}")
                append(" questions")
            }
            tvWrongAnswers.text = buildString {
                append("${quizViewModel.notAnswered}")
                append(" questions")
            }
            tvQuizResultScore.text = buildString {
                append(finalScore.toString())
                append("%")
            }
        }

    }

}