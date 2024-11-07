package com.tp.bacprep.presentation.activities.shared

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.databinding.ActivityLoginBinding
import com.tp.bacprep.domain.models.User
import com.tp.bacprep.presentation.activities.admin.AdminMainActivity
import com.tp.bacprep.presentation.activities.user.UserMainActivity
import com.tp.bacprep.presentation.viewmodels.AuthViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private var binding  : ActivityLoginBinding? = null
    private val viewModel : AuthViewModel by viewModels()
    private lateinit var sharedPref : SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setBackgroundFullScreen()  // setting the screen to take the absolute size of the screen
        setViewModelObservers() // observing sign in results and psw reset email
        setButtonsListeners() // setting buttons functionalities
    }

    private fun setButtonsListeners() {
        binding?.apply {
            tvForgotPassword.setOnClickListener {
                if (etEmail.text.toString().isNotBlank()){
                    val email = etEmail.text.toString()
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        viewModel.sendPswResetEmail(email)
                    }else{
                        showErrorSnackBar(
                            getString(R.string.entrez_votre_email),
                            true)
                    }
                }else{
                    showErrorSnackBar(
                        getString(R.string.entrez_votre_email),
                        true)
                }
            }
            loginBtn.setOnClickListener {
                if (etEmail.text.toString().isNotBlank()){
                    val email = etEmail.text.toString()
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        if (etPassword.text.toString().isNotBlank()){
                            val psw = etPassword.text.toString()
                            viewModel.signIn(email, psw)
                        }
                    }else{
                        showErrorSnackBar(
                            getString(R.string.email_invalid),
                            true)
                    }
                }else{
                    showErrorSnackBar(
                        getString(R.string.email_invalid),
                        true)
                }
            }

            tvNoAccountRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)

            }
        }
    }

    private fun setViewModelObservers() {
        viewModel.pswResetEmailResult.observe(this@LoginActivity){ result ->
            if (result.second){
                showAgreeDialog(this@LoginActivity, getString(R.string.email_envoye),
                    getString(R.string.clickez_sur_le_lien_et_changez_le_mot_de_passe),
                    ContextCompat.getDrawable(this@LoginActivity, R.drawable.icon_email)
                ) { hideAgreeDialog() }
            }else{
                showErrorSnackBar(
                    result.first.toString(),
                    true)
            }
        }
        viewModel.signInResult.observe(this@LoginActivity){ signInResult ->
            if (signInResult.first == null) {
                if (signInResult.second!!.emailVerified){
                    Toast.makeText(this@LoginActivity, signInResult.second.toString(),Toast.LENGTH_SHORT).show()
                    viewModel.saveUserInRoomDb(signInResult.second as User)
                    sharedPref = SharedPref(this@LoginActivity)
                    sharedPref.saveUserName("${signInResult.second?.id}", "${signInResult.second?.name} ${signInResult.second?.lastName}")
                    sharedPref.saveUserId(signInResult.second!!.id)
                    if (signInResult.second?.role == Constants.TEACHER_ROLE){
                        val intent = Intent(this@LoginActivity, AdminMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this@LoginActivity, UserMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }else{
                    viewModel.sendEmailVerification()
                    Toast.makeText(this@LoginActivity, "Votre compte n'est pas vérifié, un email de vérification a été envoyé a votre adresse", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@LoginActivity, signInResult.first.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}