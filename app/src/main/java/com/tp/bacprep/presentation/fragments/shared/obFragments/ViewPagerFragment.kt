package com.tp.bacprep.presentation.fragments.shared.obFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.tp.bacprep.R
import com.tp.bacprep.presentation.adapters.viewPagersAdapters.ObViewPagerAdapter

class ViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)
        val fragmentList = arrayListOf(
            ObFragmentOne(),
            ObFragmentTwo(),
            ObFragmentThree()
        )

        val adapter = ObViewPagerAdapter(fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager = view.rootView.findViewById<ViewPager2>(R.id.view_pager_ob)
        viewPager.adapter = adapter


        return view
    }



}