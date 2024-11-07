package com.tp.bacprep.presentation.adapters.recyclerviewsAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tp.bacprep.R
import com.tp.bacprep.databinding.MonthItemViewBinding

class MonthsSelectorRvAdapter(
    private val monthsList: ArrayList<String>,
    private val context: Context
) : RecyclerView.Adapter<MonthsSelectorRvAdapter.MonthsSelectorViewHolder>() {
    private var previousSelectedPosition = RecyclerView.NO_POSITION
    private var selectedPosition = 0
    private lateinit var monthSelectedListener : MonthSelectedListener

    interface MonthSelectedListener{
       fun onMonthSelected(month : String)
    }
    fun setMonthListener(monthSelectedListener : MonthSelectedListener){
        this.monthSelectedListener = monthSelectedListener
    }
    class MonthsSelectorViewHolder(binding: MonthItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tvMonth = binding.tvMonth
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthsSelectorViewHolder{
        return MonthsSelectorViewHolder(
            MonthItemViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MonthsSelectorViewHolder, position: Int) {
        val currentMonth = monthsList[position]
        holder.apply {
            tvMonth.text = currentMonth
            tvMonth.setOnClickListener {
                previousSelectedPosition = selectedPosition
                selectedPosition = holder.bindingAdapterPosition
                notifyDataSetChanged()
                monthSelectedListener.onMonthSelected(currentMonth)
            }
            if (position == selectedPosition){
                tvMonth.setTextColor(ContextCompat.getColor(context, R.color.black))

            }else{
                tvMonth.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            }

        }

    }

    override fun getItemCount(): Int {
        return monthsList.size
    }

    fun setCurrentMonth(currentMonth: Int) {
        this.selectedPosition = currentMonth-1
    }
}