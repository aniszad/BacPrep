package com.tp.bacprep.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tp.bacprep.MyApp
import com.tp.bacprep.databinding.ActivityMainBinding
import com.tp.bacprep.presentation.activities.admin.AdminMainActivity
import com.tp.bacprep.presentation.activities.shared.LoginActivity
import com.tp.bacprep.presentation.activities.shared.OnBoardingActivity
import com.tp.bacprep.presentation.activities.user.UserMainActivity
import com.tp.bacprep.presentation.viewmodels.AuthViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : AuthViewModel by viewModels()
    private lateinit var myApp : MyApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()
        setContentView(binding.root)
        myApp = (application as MyApp)

        setLanguage(this@StarterActivity)
        isUserSignedIn()
        setDarkMode()
        binding.root.setBackgroundColor(ContextCompat.getColor(this@StarterActivity, android.R.color.black))


    }

    private fun setDarkMode() {
        val isDarkMode = SharedPref(this@StarterActivity).getThemeMode()
        if (isDarkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    private fun setLanguage(context: Context) {
        val languageCode = SharedPref(context).getMyLanguage() ?: "en"
        myApp.updateLocale(this@StarterActivity, languageCode)
    }

    private fun isUserSignedIn() {
        viewModel.isUserSignedIn{ role ->
            runOnUiThread {
                Toast.makeText(this@StarterActivity, role.toString(), Toast.LENGTH_SHORT).show()

            }
            if (role != null){
                val userRole = role.first
                val emailVerified = role.second
                if (emailVerified){
                    when(userRole){
                        Constants.STUDENT_ROLE ->{
                            startActivity(Intent(this@StarterActivity, UserMainActivity::class.java))
                            this@StarterActivity.finish()
                        }
                        Constants.TEACHER_ROLE ->{
                            startActivity(Intent(this@StarterActivity, AdminMainActivity::class.java))
                            this@StarterActivity.finish()
                        }
                        Constants.CREATOR_ROLE ->{
                            startActivity(Intent(this@StarterActivity, AdminMainActivity::class.java))
                            this@StarterActivity.finish()
                        }
                    }
                }else{
                    goToBoardingOrLogin()
                }
            }else{
                goToBoardingOrLogin()
            }
        }
    }

    private fun goToBoardingOrLogin() {
        if (SharedPref(this@StarterActivity).getObVisited()){
            intent = Intent(this@StarterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            intent = Intent(this@StarterActivity, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

