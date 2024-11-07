package com.tp.bacprep.presentation.adapters.recyclerviewsAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tp.bacprep.R
import com.tp.bacprep.databinding.MonthItemViewBinding

class AudioTypeSelectorRvAdapter(
    private val audioTypesList: List<String>,
    private val context: Context
) : RecyclerView.Adapter<AudioTypeSelectorRvAdapter.AudioTypeSelectorViewHolder>() {
    private var previousSelectedPosition = RecyclerView.NO_POSITION
    private var selectedPosition = 0
    private lateinit var audioTypeSelectedListener : AudioTypeSelectedListener

    interface AudioTypeSelectedListener{
       fun onModuleSelected(audioType : String)
    }
    fun setAudioTypeListener(audioTypeSelectedListener : AudioTypeSelectedListener){
        this.audioTypeSelectedListener = audioTypeSelectedListener
    }
    class AudioTypeSelectorViewHolder(binding: MonthItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tvMonth = binding.tvMonth
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioTypeSelectorViewHolder {
        return AudioTypeSelectorViewHolder(
            MonthItemViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AudioTypeSelectorViewHolder, position: Int) {
        val currentMonth = audioTypesList[position]
        holder.apply {
            tvMonth.text = currentMonth
            tvMonth.setTextColor(ContextCompat.getColor(context, R.color.white))
            tvMonth.setOnClickListener {
                previousSelectedPosition = selectedPosition
                selectedPosition = holder.bindingAdapterPosition
                audioTypeSelectedListener.onModuleSelected(currentMonth)
                notifyItemChanged(position)
                notifyItemChanged(previousSelectedPosition)
            }
            if (position == selectedPosition){
                tvMonth.setTextColor(ContextCompat.getColor(context, R.color.black))
            }else{
                tvMonth.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            }
        }

    }

    fun getSelectedModule() : String{
        return audioTypesList[selectedPosition]
    }

    override fun getItemCount(): Int {
        return audioTypesList.size
    }
}