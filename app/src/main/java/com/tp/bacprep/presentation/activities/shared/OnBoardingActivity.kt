package com.tp.bacprep.presentation.activities.shared

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.databinding.ActivityOnBoardingBinding
import com.tp.bacprep.util.SharedPref

class OnBoardingActivity : BaseActivity() {

    private var binding : ActivityOnBoardingBinding? = null
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setBackgroundFullScreen()
        binding?.apply {
            btnNext.setOnClickListener {
                nextObPage()
            }
        }

    }
    override fun onResume() {
        super.onResume()
        setDotsIndicator()
        setSwipingUpdates()
    }

    private fun setSwipingUpdates() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                updateObUi(position, false)
            }
        })
    }

    private fun setDotsIndicator() {
        if (findViewById<ViewPager2>(R.id.view_pager_ob) != null){
            viewPager = findViewById(R.id.view_pager_ob)
            binding?.diDotsIndicator?.attachTo(viewPager)
            binding?.diDotsIndicator?.dotsClickable = false
        }
    }

    private fun nextObPage() {
        val viewPager = findViewById<ViewPager2>(R.id.view_pager_ob)
        val current = viewPager.currentItem
        updateObUi(current, true)
    }

    //Updating ui when scrolling/pushing next button
    private fun updateObUi(position: Int, fromBtn: Boolean){
        when(position){
            0 ->{
                if (fromBtn){
                    viewPager.currentItem = 1
                }
                binding?.btnNext?.text = getString(R.string.next)
            }
            1 ->{
                if (fromBtn){
                    viewPager.currentItem = 2
                }
                binding?.btnNext?.text = getString(R.string.next)
            }
            2 ->{
                if(fromBtn){
                    val sharedPref = SharedPref(this)
                    sharedPref.setObVisited(true)
                    intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                binding?.btnNext?.text = getString(R.string.commencer)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}