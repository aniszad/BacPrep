package com.tp.bacprep.presentation.adapters.recyclerviewsAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp.bacprep.databinding.RegularAudioItemLayoutBinding
import com.tp.bacprep.domain.models.AudioFile

class AudioFilesAdapter(private var audioFilesList : List<AudioFile>) : RecyclerView.Adapter<AudioFilesAdapter.RegularAudioViewHolder>() {
    private lateinit var playButtonClickListener : PlayButtonClickListener

    interface PlayButtonClickListener{
        fun onPlayButtonClicked(audioName : String, audioDownloadUrl : String)
    }

    class RegularAudioViewHolder(binding : RegularAudioItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.tvAudioTitle
        val btnPlay = binding.btnPlayAudio
        val imPlay = binding.imPlay
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegularAudioViewHolder {
        return RegularAudioViewHolder(RegularAudioItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return audioFilesList.size
    }

    override fun onBindViewHolder(holder: RegularAudioViewHolder, position: Int) {
        val currentAudio = audioFilesList[position]
        holder.title.text = currentAudio.title.removeSuffix(".mp3")
        holder.btnPlay.setOnClickListener {
            playButtonClickListener.onPlayButtonClicked(currentAudio.title,currentAudio.downloadUrl)
        }
        holder.imPlay.setOnClickListener {
            playButtonClickListener.onPlayButtonClicked(currentAudio.title,currentAudio.downloadUrl)
        }
    }

    fun setPlayButtonClickedListener(playButtonClickListener: PlayButtonClickListener){
        this.playButtonClickListener = playButtonClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun displayQuery(query: String){
        val updatedList = audioFilesList.filter { it.title.contains(query)}
        this.audioFilesList = updatedList
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetData(audioList: List<AudioFile>?) {
        if (audioList!=null){
            this.audioFilesList = audioList
            notifyDataSetChanged()
        }
    }

}