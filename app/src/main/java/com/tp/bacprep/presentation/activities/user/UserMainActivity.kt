package com.tp.bacprep.presentation.activities.user

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewModelScope
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.presentation.adapters.viewPagersAdapters.MainFragmentsAdapter
import com.tp.bacprep.databinding.ActivityUserMainBinding
import com.tp.bacprep.presentation.fragments.user.AudioResourcesFragment
import com.tp.bacprep.presentation.fragments.user.quizFragments.QuizzesFragment
import com.tp.bacprep.presentation.fragments.shared.FeedFragment
import com.tp.bacprep.presentation.fragments.user.MainProgressFragment
import com.tp.bacprep.presentation.fragments.user.MyFilesFragment
import com.tp.bacprep.presentation.viewmodels.BaseViewModel
import com.tp.bacprep.util.SharedPref
import com.tp.bacprep.util.ViewPager2SlideTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserMainActivity : BaseActivity() {
    private var binding : ActivityUserMainBinding? = null
    private val viewModel : BaseViewModel by viewModels()
    private lateinit var mainVpAdapter : MainFragmentsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // setting the navigation bar color
        setNavigationBarColor(ContextCompat.getColor(this@UserMainActivity, R.color.light_gray_to_deep_charcoal))
        // setting the status bar color
        setStatusBarColor(ContextCompat.getColor(this@UserMainActivity, R.color.light_gray_to_deep_charcoal), true)
        // set ViewPager
        setViewPagerAdapter()
        // set Bottom navigation functionality
        setBnvFunctionality()
    }

    private fun setBnvFunctionality() {
        binding?.apply {
            handleBottomNavigationView(R.id.user_feed)
            bnvUserMain.setOnItemSelectedListener { item ->
                handleBottomNavigationView(item.itemId)
                true
            }
        }
    }

    private fun setViewPagerAdapter() {
        binding?.apply {
            // Disable User Input (swiping)
            vpUserMain.isUserInputEnabled = false
            vpUserMain.setPageTransformer(ViewPager2SlideTransformer())
            mainVpAdapter = MainFragmentsAdapter(supportFragmentManager, lifecycle,
                listOf(FeedFragment::class.java, QuizzesFragment::class.java,
                 MyFilesFragment::class.java, MainProgressFragment::class.java,
                  AudioResourcesFragment::class.java
                )
            )
            vpUserMain.adapter = mainVpAdapter
        }
    }

    private fun changeStatusBarColor(color: Int, lightStatusBar : Boolean) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = lightStatusBar  // true or false as desired.
        // And then you can set any background color to the status bar.
        window.statusBarColor = color
    }

    private fun handleBottomNavigationView(itemId: Int) {
        binding?.apply {
            when (itemId) {
                R.id.user_feed -> {
                    vpUserMain.setCurrentItem(0, false)
                    viewModel.viewModelScope.launch {
                        bnvUserMain.menu.getItem(0).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_home_clicked)
                        bnvUserMain.menu.getItem(1).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_quizzes)
                        bnvUserMain.menu.getItem(2).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_my_space)
                        bnvUserMain.menu.getItem(4).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_play)
                        bnvUserMain.menu.getItem(3).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_progress)
                        val isDarkMode = SharedPref(this@UserMainActivity).getThemeMode()
                        changeStatusBarColor(ContextCompat.getColor(this@UserMainActivity, R.color.light_gray_to_deep_charcoal), !isDarkMode)
                    }
                }
                R.id.user_quizzes -> {
                    vpUserMain.setCurrentItem(1, false)
                    viewModel.viewModelScope.launch { bnvUserMain.menu.getItem(0).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_home)
                        bnvUserMain.menu.getItem(1).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_quizzes_clicked)
                        bnvUserMain.menu.getItem(2).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_my_space)
                        bnvUserMain.menu.getItem(4).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_play)
                        bnvUserMain.menu.getItem(3).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_progress)
                        val isDarkMode = SharedPref(this@UserMainActivity).getThemeMode()
                        changeStatusBarColor(ContextCompat.getColor(this@UserMainActivity, R.color.white_to_deep_charcoal), !isDarkMode) }

                }
                R.id.user_my_files -> {
                    vpUserMain.setCurrentItem(2, false)
                    viewModel.viewModelScope.launch {
                        bnvUserMain.menu.getItem(0).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_home)
                        bnvUserMain.menu.getItem(1).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_quizzes)
                        bnvUserMain.menu.getItem(2).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_my_space_clicked)
                        bnvUserMain.menu.getItem(4).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_play)
                        bnvUserMain.menu.getItem(3).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_progress)
                        val isDarkMode = SharedPref(this@UserMainActivity).getThemeMode()
                        changeStatusBarColor(ContextCompat.getColor(this@UserMainActivity, R.color.blackish),false)
                    }

                }
                R.id.user_progress -> {
                    vpUserMain.setCurrentItem(3, false)
                    viewModel.viewModelScope.launch {
                        bnvUserMain.menu.getItem(0).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_home)
                        bnvUserMain.menu.getItem(1).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_quizzes)
                        bnvUserMain.menu.getItem(2).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_my_space)
                        bnvUserMain.menu.getItem(4).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_play)
                        bnvUserMain.menu.getItem(3).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_progress_clicked)
                        val isDarkMode = SharedPref(this@UserMainActivity).getThemeMode()
                        changeStatusBarColor(ContextCompat.getColor(this@UserMainActivity, R.color.white_to_deep_charcoal), !isDarkMode)
                    }

                }
                R.id.user_multimedia -> {
                    vpUserMain.setCurrentItem(4, false)
                    viewModel.viewModelScope.launch {
                        bnvUserMain.menu.getItem(0).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_home)
                        bnvUserMain.menu.getItem(1).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_quizzes)
                        bnvUserMain.menu.getItem(2).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_my_space)
                        bnvUserMain.menu.getItem(4).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_play_clicked)
                        bnvUserMain.menu.getItem(3).icon = ContextCompat.getDrawable(this@UserMainActivity, R.drawable.icon_progress)
                        val isDarkMode = SharedPref(this@UserMainActivity).getThemeMode()
                        Toast.makeText(this@UserMainActivity, isDarkMode.toString(), Toast.LENGTH_SHORT).show()
                        changeStatusBarColor(ContextCompat.getColor(this@UserMainActivity, R.color.white_to_deep_charcoal), !isDarkMode)
                    }

                }
            }
        }
    }
}