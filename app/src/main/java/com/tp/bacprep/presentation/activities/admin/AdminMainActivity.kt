package com.tp.bacprep.presentation.activities.admin

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.presentation.adapters.viewPagersAdapters.MainFragmentsAdapter
import com.tp.bacprep.databinding.ActivityAdminMainBinding
import com.tp.bacprep.domain.models.Request
import com.tp.bacprep.presentation.fragments.admin.AccountFragment
import com.tp.bacprep.presentation.fragments.admin.AdminCreatePostFragment
import com.tp.bacprep.presentation.fragments.shared.FeedFragment
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminMainActivity : BaseActivity() {
    private var binding : ActivityAdminMainBinding? = null
    private var vpAdapter : MainFragmentsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        vpAdapter = MainFragmentsAdapter(supportFragmentManager, lifecycle, listOf(FeedFragment::class.java as Class<out Fragment>, AdminCreatePostFragment::class.java as Class<out Fragment>,
            AccountFragment::class.java as Class<out Fragment>))

        setStatusBarColor(ContextCompat.getColor(this@AdminMainActivity, R.color.light_gray_to_deep_charcoal),
            !SharedPref(this@AdminMainActivity).getThemeMode())

        binding?.apply {
            binding?.vpAdminMain?.adapter = vpAdapter
            binding?.vpAdminMain?.currentItem = 0
            bnvAdminMain.setOnItemSelectedListener { item ->
                handleBottomNavigationView(item.itemId)
                true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (intent.extras != null){
            val respondingToRequest = intent.getBooleanExtra(Constants.RESPONDING_TO_REQUEST, false)
            if (respondingToRequest){
                @Suppress("DEPRECATION")
                val request = intent.getParcelableExtra<Request>(Constants.REQUEST_TO_ANSWER_TO)
                val arguments = Bundle().apply {
                    putBoolean(Constants.RESPONDING_TO_REQUEST, true)
                    putParcelable(Constants.REQUEST_TO_ANSWER_TO, request)
                }
                vpAdapter?.setFragmentData(1, arguments)
                Toast.makeText(this@AdminMainActivity, "success", Toast.LENGTH_LONG).show()
                handleBottomNavigationView(R.id.admin_create_post)
            }
        }
    }

    private fun handleBottomNavigationView(itemId: Int) {
        binding?.apply {
            when (itemId) {

                R.id.admin_feed -> {
                    bnvAdminMain.menu.getItem(1).icon = ContextCompat.getDrawable(this@AdminMainActivity, R.drawable.icon_my_space)
                    bnvAdminMain.menu.getItem(0).icon = ContextCompat.getDrawable(this@AdminMainActivity, R.drawable.icon_home_clicked)
                    bnvAdminMain.menu.getItem(2).icon = ContextCompat.getDrawable(this@AdminMainActivity, R.drawable.icon_account)
                    binding?.vpAdminMain?.currentItem = 0
                    setStatusBarColor(ContextCompat.getColor(this@AdminMainActivity, R.color.light_gray_to_deep_charcoal), !SharedPref(this@AdminMainActivity).getThemeMode())

                }
                R.id.admin_create_post -> {
                    bnvAdminMain.menu.getItem(0).icon = ContextCompat.getDrawable(this@AdminMainActivity, R.drawable.icon_home)
                    bnvAdminMain.menu.getItem(1).icon = ContextCompat.getDrawable(this@AdminMainActivity, R.drawable.icon_my_space_clicked)
                    bnvAdminMain.menu.getItem(2).icon = ContextCompat.getDrawable(this@AdminMainActivity, R.drawable.icon_account)
                    binding?.vpAdminMain?.currentItem = 1
                    setStatusBarColor(ContextCompat.getColor(this@AdminMainActivity, R.color.white), !SharedPref(this@AdminMainActivity).getThemeMode())

                }
                R.id.admin_account -> {
                    bnvAdminMain.menu.getItem(1).icon = ContextCompat.getDrawable(this@AdminMainActivity, R.drawable.icon_my_space)
                    bnvAdminMain.menu.getItem(0).icon = ContextCompat.getDrawable(this@AdminMainActivity, R.drawable.icon_home)
                    bnvAdminMain.menu.getItem(2).icon = ContextCompat.getDrawable(this@AdminMainActivity, R.drawable.icon_account_clicked)
                    binding?.vpAdminMain?.currentItem = 2
                    setStatusBarColor(ContextCompat.getColor(this@AdminMainActivity, R.color.white),
                        !SharedPref(this@AdminMainActivity).getThemeMode())
                }
            }
        }
    }
}