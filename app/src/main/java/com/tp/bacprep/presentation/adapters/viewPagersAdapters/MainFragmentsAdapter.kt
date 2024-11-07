package com.tp.bacprep.presentation.adapters.viewPagersAdapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainFragmentsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val fragmentClasses: List<Class<out Fragment>>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentDataList: MutableList<Bundle?> = mutableListOf(null, null, null, null, null)

    override fun getItemCount(): Int {
        return fragmentClasses.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = fragmentClasses[position].newInstance()
        return if (fragmentDataList[position] != null){
            fragment.arguments = fragmentDataList[position] // Set the data as arguments
            fragmentDataList[position] = null // Resetting data
            fragment
        }else{
            fragment
        }

    }

    // Add a function to set data for a specific fragment
    fun setFragmentData(position: Int, data: Bundle) {
        fragmentDataList.add(position, data)
        notifyItemChanged(position)
    }

}
