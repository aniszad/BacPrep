package com.tp.bacprep.presentation.activities.user


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import android.view.animation.AnimationUtils
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.databinding.ActivityAudioPlayerBinding
import com.tp.bacprep.util.Constants

class AudioPlayerActivity : BaseActivity() {
    private lateinit var binding : ActivityAudioPlayerBinding
    private lateinit var audioDownloadUrl : String
    private lateinit var audioName : String
    private lateinit var player : ExoPlayer
    private var isPlaying = MutableLiveData<Boolean>()
    private lateinit var positionHandler : Handler
    private lateinit var rotateAnimation : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarColor(ContextCompat.getColor(this@AudioPlayerActivity, R.color.blackish), false)
        setNavigationBarColor(ContextCompat.getColor(this@AudioPlayerActivity, R.color.blackish))
        if (intent.extras != null){
            audioDownloadUrl = intent.getStringExtra(Constants.AUDIO_FILE_DOWNLOAD_URL) ?: ""
            audioName = intent.getStringExtra(Constants.AUDIO_FILE_NAME) ?:""
            setExoPlayer()
        }

        isPlaying.observe(this@AudioPlayerActivity){ isPlaying ->
            if(isPlaying){
                binding.btnPlayAudio.setImageDrawable(ContextCompat.getDrawable(this@AudioPlayerActivity, R.drawable.icon_pause_audio))
            }else{
                binding.btnPlayAudio.setImageDrawable(ContextCompat.getDrawable(this@AudioPlayerActivity, R.drawable.icon_play_audio))
            }
        }

        binding.apply {
            btnPlayAudio.setOnClickListener {
                if (isPlaying.value == true){
                    player.pause()
                    stopImageAnimation()
                }else{
                    player.play()
                    playImageAnimation()
                }
            }
            btnSkip5Sec.setOnClickListener {
                Toast.makeText(this@AudioPlayerActivity, "${player.currentPosition}", Toast.LENGTH_SHORT).show()
                seekAudioTo(sliderAudioProgressBar.value + 0.05F)
            }
            btnGoBack5Sec.setOnClickListener {
                seekAudioTo(sliderAudioProgressBar.value - 0.05F)
            }

            sliderAudioProgressBar.addOnChangeListener { _, value, fromUser ->
                if (fromUser){
                    seekAudioTo(value)
                }
            }
        }
    }

    private fun playImageAnimation() {
        binding.cardAudioImage.animation = rotateAnimation
    }

    private fun stopImageAnimation() {
        binding.cardAudioImage.animation.cancel()
    }

    private fun seekAudioTo(value: Float) {
        player.seekTo(convertToMilliseconds(value))
    }


    private fun convertToMilliseconds(minutesAndSeconds: Float): Long {
        val minutes = minutesAndSeconds.toInt()
        val seconds = ((minutesAndSeconds - minutes) * 100).toInt() // Convert decimal portion to seconds

        return (minutes * 60 + seconds) * 1000L // Convert minutes and seconds to milliseconds
    }
    private fun setExoPlayer() {
        binding.tvAudioTitle.text = audioName.removeSuffix(Constants.MP3_EXTENSION)
        player = ExoPlayer.Builder(this@AudioPlayerActivity)
            .build()
        val mediaItem = MediaItem.fromUri(audioDownloadUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.addListener(object : Player.Listener{
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                this@AudioPlayerActivity.isPlaying.postValue(isPlaying)
                if (isPlaying){
                    updateTimeTextView()
                }else{
                    cancelTimeTextViewUpdating()
                }
            }
        })

        // setting image animation
        rotateAnimation = AnimationUtils.loadAnimation(this@AudioPlayerActivity, R.anim.rotation_audio_image)
        rotateAnimation.duration = 10000L
        rotateAnimation.repeatCount = Animation.INFINITE
    }

    private fun cancelTimeTextViewUpdating() {
            positionHandler.removeCallbacksAndMessages(null)
    }

    private fun updateTimeTextView() {
        positionHandler = Handler(Looper.getMainLooper())
        positionHandler.postDelayed(object : Runnable {
            override fun run() {
                // updating time text view
                binding.apply {
                    val currentPosition = player.currentPosition
                    binding.tvAudioTimeDuration.text = formatAudioTime(player.duration)
                    // these have to be in order so the progressing happens correctly (the slider knows the from, to values and it can know where to position the current time)
                    binding.sliderAudioProgressBar.valueFrom = 0F
                    binding.sliderAudioProgressBar.valueTo = getDurationInFloat(player.duration) ?: 0F
                    binding.sliderAudioProgressBar.value = getDurationInFloat(currentPosition) ?: 0F
                    // Format the current position into a string
                    val formattedTime = formatAudioTime(currentPosition)
                    // Set the text of the text view
                    tvAudioTimePosition.text = formattedTime
                }
                positionHandler.postDelayed(this, 1000) // Update every second
            }
        }, 1000) // Start updating after 1 second
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the ExoPlayer instance
        player.release()
    }

    private fun formatAudioTime(time: Long): String {
        // Get the hours, minutes, and seconds from the time
        val minutes = time / 60000
        val seconds = (time%60000) / 1000

        // Create a string to represent the time
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun getDurationInFloat(duration : Long) : Float?{
        val minutes = (duration / 60000).toInt()
        val seconds = ((duration%60000) / 1000).toInt()
        return try {
            String.format("%02d.%02d", minutes, seconds).toFloat()
        }catch (e : Exception){
            null
        }
    }
}