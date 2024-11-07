package com.tp.bacprep.presentation.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.tp.bacprep.presentation.adapters.viewPagersAdapters.MainFragmentsAdapter
import com.tp.bacprep.databinding.FragmentMainProgressBinding

class MainProgressFragment : Fragment() {
    private lateinit var binding : FragmentMainProgressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpProgressFrags.isUserInputEnabled = false
        val pagerAdapter = MainFragmentsAdapter(childFragmentManager, lifecycle,
            listOf(ProgressFragment::class.java, StudyTimeFragment::class.java)
        )
        binding.vpProgressFrags.adapter = pagerAdapter
        binding.tabProgressFrags.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null){
                    when(tab.position){
                        0 ->{
                            binding.vpProgressFrags.currentItem = 0
                        }
                        1 ->{
                            binding.vpProgressFrags.currentItem = 1
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }




}