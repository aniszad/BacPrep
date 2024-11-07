package com.tp.bacprep.presentation.activities.shared

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.databinding.ActivityRegisterBinding
import com.tp.bacprep.presentation.fragments.shared.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {
    private var binding: ActivityRegisterBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSystemBarsColors()
        setFirstRegisterFrag()
        setBackPressedFunctionality()
    }

    // changing nav bar and status bar colors to green
    private fun setSystemBarsColors() {
        setStatusBarColor(ContextCompat.getColor(this@RegisterActivity, R.color.green), true)
        setNavigationBarColor(ContextCompat.getColor(this@RegisterActivity, R.color.green))
    }

    // set the first registration frag in the activity
    private fun setFirstRegisterFrag() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_register, RegisterFragment())
            .commit()
    }

    // set the functionality when the back button is clicked
    private fun setBackPressedFunctionality() {
        onBackPressedDispatcher.addCallback(
            this /* lifecycle owner */,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showWarningDialog(
                        getString(R.string.cancel_inscription_warning_text)
                    ) { onWarningDialogConfirmed() }
                }
            })
    }

    private fun onWarningDialogConfirmed() {
        hideWarningDialog()
        this@RegisterActivity.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
