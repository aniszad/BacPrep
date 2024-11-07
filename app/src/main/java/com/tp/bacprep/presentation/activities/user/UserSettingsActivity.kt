package com.tp.bacprep.presentation.activities.user

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.CompoundButton
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.tp.bacprep.MyApp
import com.tp.bacprep.R
import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.databinding.ActivityUserSettingsBinding
import com.tp.bacprep.databinding.DialogChangeLanguageBinding
import com.tp.bacprep.presentation.activities.shared.LoginActivity
import com.tp.bacprep.presentation.viewmodels.UserSettingsViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.MultiLangStrings
import com.tp.bacprep.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class UserSettingsActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener {

    private lateinit var binding : ActivityUserSettingsBinding

    private val userSettingsVm : UserSettingsViewModel by viewModels()

    private var changeLanguageDialog: Dialog? = null

    private lateinit var myApp : MyApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myApp = (application as MyApp)

        setBarsColors()
        observeViewModelData()
        setButtonsFunc()
        //setBranchSpinner()
        getUserView()

        // handling back button clicked
        onBackPressedDispatcher.addCallback(
            this /* lifecycle owner */,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                }
            }
        )
    }

    private fun setBarsColors() {
        if (SharedPref(this@UserSettingsActivity).getThemeMode()){
            setStatusBarColor(ContextCompat.getColor(this@UserSettingsActivity,R.color.deep_charcoal), false)
        }else{
            setStatusBarColor(ContextCompat.getColor(this@UserSettingsActivity,R.color.grayish_black), false)
        }
    }

    private fun setButtonsFunc() {
        binding.apply {
            cardLogout.setOnClickListener {
                resetLocaleSettings()
                userSettingsVm.signOut()
            }
            cardChangeLanguage.setOnClickListener {
                showChangeLanguageDialog()
            }
            cardChangePassword.setOnClickListener {
                showWarningDialog( getString(R.string.change_psw_warning_text)) { sendResetEmail() }
            }
            cardRequest.setOnClickListener {
                startActivity(Intent(this@UserSettingsActivity, SendRequestsActivity::class.java))
            }
            btnExitSettings.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

        }
    }

    private fun observeViewModelData() {
        userSettingsVm.resetEmailSent.observe(this@UserSettingsActivity){ sendResult ->
            if (sendResult.second){
                runOnUiThread {
                    showErrorSnackBar(getString(R.string.email_de_changement_de_mot_de_passe_envoy), false)
                }
            }else{
                runOnUiThread {
                    showErrorSnackBar(getString(R.string.email_non_envoy), true)
                }
            }
        }
        userSettingsVm.signOutResult.observe(this@UserSettingsActivity){ signOutResult ->
            if (signOutResult){
                startActivity(Intent(this@UserSettingsActivity, LoginActivity::class.java))
                finish()
            }
        }

    }

    private fun getUserBranch() {
        val currentBranch = SharedPref(this@UserSettingsActivity).getMyBranch()
        val translatedCurrentBranch = when (currentBranch) {
            Constants.SCIENCE_BRANCH -> {
                MultiLangStrings(this@UserSettingsActivity).SCIENCE_BRANCH
            }
            Constants.MATH_BRANCH -> {
                MultiLangStrings(this@UserSettingsActivity).MATH_BRANCH
            }
            Constants.GESTION_BRANCH -> {
                MultiLangStrings(this@UserSettingsActivity).GESTION_BRANCH
            }
            Constants.MATH_TECH_BRANCH_MEC->{
                MultiLangStrings(this@UserSettingsActivity).MATH_TECH_MEC_BRANCH
            }
             Constants.MATH_TECH_BRANCH_ELE->{
                 MultiLangStrings(this@UserSettingsActivity).MATH_TECH_ELE_BRANCH
            }
            Constants.MATH_TECH_BRANCH_CIV ->{
                 MultiLangStrings(this@UserSettingsActivity).MATH_TECH_CIV_BRANCH
            }
            Constants.MATH_TECH_BRANCH_METH ->{
                MultiLangStrings(this@UserSettingsActivity).MATH_TECH_METH_BRANCH
            }
            Constants.LANGUES_BRANCH -> {
                MultiLangStrings(this@UserSettingsActivity).LANGUES_BRANCH
            }
            Constants.LETTRE_BRANCH -> {
                MultiLangStrings(this@UserSettingsActivity).LETTRE_BRANCH
            }
            else -> {
                MultiLangStrings(this@UserSettingsActivity).GESTION_BRANCH
            }
        }
        //binding.spinnerFiliere.setSelection(branches.indexOf(translatedCurrentBranch))
    }

    private fun sendResetEmail() {
        userSettingsVm.sendResetPasswordEmail()
    }

    private fun resetLocaleSettings() {
        myApp.resetLocaleToSystemDefault(this@UserSettingsActivity)
    }

    private fun getUserView(){
        getUserBranch()
        val sharedPref = SharedPref(this@UserSettingsActivity)
        val userName = sharedPref.getUserName(FbAuth(this@UserSettingsActivity).getUserId())
        binding.apply {
            tvMyName.text = buildString {
                append(userName)
            }
            switchTheme.isChecked = sharedPref.getThemeMode()
            switchTheme.setOnCheckedChangeListener(this@UserSettingsActivity)
        }
    }

    private fun showChangeLanguageDialog() {
        changeLanguageDialog = Dialog(this@UserSettingsActivity)
        val bindingDialog = DialogChangeLanguageBinding.inflate(layoutInflater)
        changeLanguageDialog?.apply {
            setCancelable(true)
            setCanceledOnTouchOutside(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            changeLanguageDialog?.setContentView(bindingDialog.root)
            val lang = getCurrentLocale()
            when(lang){
                "ar" -> {
                    bindingDialog.rbArabic.isChecked = true
                }
                "fr" ->{
                    bindingDialog.rbFrench.isChecked = true
                }
                "en" ->{
                    bindingDialog.rbEnglish.isChecked = true
                }
            }

        }
        bindingDialog.apply {
            btnConfirm.setOnClickListener {
                when{
                    rbArabic.isChecked ->{
                        setAppLocale("ar")
                    }
                    rbFrench.isChecked ->{
                        setAppLocale("fr")
                    }
                    rbEnglish.isChecked ->{
                        setAppLocale("en")
                    }
                }
            }
        }
        if (window.hasFeature(Window.FEATURE_NO_TITLE)) {
            changeLanguageDialog?.show()
        }
    }

    private fun hideChangeLanguageDialog() {
        changeLanguageDialog?.dismiss()
    }

    private fun setAppLocale(languageCode: String) {
        when(languageCode){
            "ar" ->{
                myApp.updateLocale(this@UserSettingsActivity, languageCode)
            }
            "fr" ->{
                myApp.updateLocale(this@UserSettingsActivity, languageCode)
            }
            "en" ->{
                myApp.updateLocale(this@UserSettingsActivity, languageCode)
            }
            else ->{
                myApp.updateLocale(this@UserSettingsActivity, "fr")

            }
        }
        hideChangeLanguageDialog()
        updateConstants()
        restartApp(this@UserSettingsActivity)
    }

    private fun updateConstants() {
        Constants.apply {

        }
    }

    @Suppress("DEPRECATION")
    private fun getCurrentLocale() : String{
        val currentLocale: Locale = resources.configuration.locale
        return currentLocale.language
    }

    private fun restartApp(context: Context) {
        val intent = Intent(context, UserMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
    }

    override fun onCheckedChanged(switchButton: CompoundButton?, isChecked: Boolean) {
        binding.switchTheme.setOnCheckedChangeListener(null)
        if (isChecked){
            SharedPref(this@UserSettingsActivity).setThemeMode(isDarkMode = true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            startActivity(Intent(this@UserSettingsActivity, UserMainActivity::class.java))
            this@UserSettingsActivity.finish()
        }else{
            SharedPref(this@UserSettingsActivity).setThemeMode(isDarkMode = false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            startActivity(Intent(this@UserSettingsActivity, UserMainActivity::class.java))
            this@UserSettingsActivity.finish()
        }
        binding.switchTheme.setOnCheckedChangeListener(this@UserSettingsActivity)
    }
}