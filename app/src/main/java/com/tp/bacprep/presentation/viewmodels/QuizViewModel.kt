package com.tp.bacprep.presentation.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


class QuizViewModel : ViewModel() {
    private lateinit var countDownTimer: CountDownTimer

    private var _currentQstIndex : Int = 0
    val currentQstIndex : Int get() = _currentQstIndex

    private var _score : Int= 0
    val score : Int  get() = _score

    private var _answered : Int= 0
    val answered : Int  get() = _answered

    private var _notAnswered : Int= 0
    val notAnswered : Int  get() = _notAnswered

    private val _quizRemainingTime = MutableLiveData<Long>()
    val quizRemainingTime : LiveData<Long>  get() = _quizRemainingTime

    fun setAnswered(num : Int){
        _answered = num
    }
    fun setNotAnswered(num : Int){
        _notAnswered = num
    }

    fun incrementCurrentQstIndex(){
        _currentQstIndex++
    }

    fun setFinalScore(finalScore : Int){
        _score = finalScore
    }

    fun startQuizTime(quizTime: Long) {
        countDownTimer = object : CountDownTimer(quizTime, 1000){
            override fun onTick(millisUntilFinished: Long) {
                _quizRemainingTime.postValue(millisUntilFinished)
            }
            override fun onFinish() {
                _quizRemainingTime.postValue(0)
            }

        }
        countDownTimer.start()
    }
}