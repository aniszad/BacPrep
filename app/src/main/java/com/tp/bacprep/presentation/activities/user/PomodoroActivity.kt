package com.tp.bacprep.presentation.activities.user

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.databinding.ActivityPomodoroBinding
import com.tp.bacprep.domain.models.StudyTime
import com.tp.bacprep.presentation.viewmodels.PomodoroTimerViewModel
import com.tp.bacprep.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class PomodoroActivity : BaseActivity() {
    private lateinit var binding : ActivityPomodoroBinding
    private val pomodoroTimerVm : PomodoroTimerViewModel by viewModels()
    private lateinit var countDownTimer: CountDownTimer
    private var studyTimeInMinutes : Long = 0L
    private var restTimeInMinutes : Long = 0L
    private var timeIsRunning = false
    private var isPaused = false
    private var isRestTimer = false
    private var remainingTime : Long = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPomodoroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // hiding system navigation bar
        hideNavigationBar(binding.root)
        // setting the color of the status bar
        setStatusBarColor(ContextCompat.getColor(this@PomodoroActivity, R.color.blackish), true)
        // getting timer info from intent
        if (intent.extras != null){
            studyTimeInMinutes = intent.getLongExtra(Constants.TIMER_STUDY_DURATION, 60L)
            restTimeInMinutes = intent.getLongExtra(Constants.TIMER_REST_DURATION, 10L)
            // setting the timer view
            setBeforeStartUI(studyTimeInMinutes)
        }

        binding.apply {
            btnStart.setOnClickListener {
                setButtonFunctionality()
            }
        }


    }

    private fun setButtonFunctionality() {
        if (timeIsRunning){
            // Pausing the counter
            countDownTimer.cancel()
            isPaused = true
            timeIsRunning = false
            binding.btnStart.text = getString(R.string.start)
        }else{
            if (isPaused){
                startTimer(remainingTime)
                timeIsRunning = true
                isPaused = false
                binding.btnStart.text = getString(R.string.pause)
            }else{
                startTimer(studyTimeInMinutes*60*1000)
                binding.btnStart.text = getString(R.string.pause)
                timeIsRunning = true
            }
        }
    }

    private fun startTimer(timeInLong : Long) {
        countDownTimer = object : CountDownTimer(timeInLong, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimerRemainingTime.text = formatTimerTime(millisUntilFinished)
                binding.cpiPomodoro.setProgress(millisUntilFinished.toInt(), true)
                remainingTime = millisUntilFinished
            }

            override fun onFinish() {
               if (isRestTimer){
                   setNextStudyTime()
                   setStudyView()
               }else{
                   setNextRestTime()
                   setRestView()
               }
            }
        }
        countDownTimer.start()
    }

    private fun setRestView() {
        binding.apply {
            cardTime.setCardBackgroundColor(ContextCompat.getColor(this@PomodoroActivity, R.color.green))
        }
    }

    private fun setStudyView() {
        binding.apply {
            cardTime.setCardBackgroundColor(ContextCompat.getColor(this@PomodoroActivity, R.color.flair_orange))
        }
    }

    private fun setNextRestTime() {
        isRestTimer = true
        saveTimeInRoomDb()
        binding.cardTime.setCardBackgroundColor(ContextCompat.getColor(this@PomodoroActivity, R.color.flair_green))
        binding.cpiPomodoro.max = (restTimeInMinutes*60*1000).toInt()
        startTimer(restTimeInMinutes*60*1000)
    }

    private fun saveTimeInRoomDb() {
        val currentTimeMillis = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        pomodoroTimerVm.getStudyTime(day, month, year){studyTime ->
            if (studyTime!=null){
                studyTime.duration += studyTimeInMinutes*60*1000
                pomodoroTimerVm.updateStudyTimeInRoomDb(studyTime)
            }else{
                pomodoroTimerVm.saveTimeInRoomDb(StudyTime(id = 0,
                    year = year,
                    month = month,
                    day = day,
                    duration = studyTimeInMinutes*60*1000))
            }
        }

    }

    private fun setNextStudyTime() {
        isRestTimer = false
        binding.cardTime.setCardBackgroundColor(ContextCompat.getColor(this@PomodoroActivity, R.color.black))
        binding.cpiPomodoro.max = (studyTimeInMinutes*60*1000).toInt()
        startTimer(studyTimeInMinutes*60*1000)
    }

    private fun setBeforeStartUI(studyTimeInMinutes: Long) {
        binding.tvTimerRemainingTime.text = formatTimerTime(studyTimeInMinutes*60*1000)
        binding.cpiPomodoro.max = (studyTimeInMinutes*60*1000).toInt()
        binding.cpiPomodoro.progress = (studyTimeInMinutes*60*1000).toInt()
    }
    private fun formatTimerTime(millisUntilFinished: Long?): CharSequence? {
        if (millisUntilFinished == null) {
            return null
        }
        val totalSeconds = millisUntilFinished / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

}
