package com.tp.bacprep.presentation.activities

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.snackbar.Snackbar
import com.tp.bacprep.R
import com.tp.bacprep.databinding.ActivityBaseBinding
import com.tp.bacprep.databinding.AgreeDialogLayoutBinding
import com.tp.bacprep.databinding.WarningDialogLayoutBinding

open class BaseActivity : AppCompatActivity() {
    private lateinit var mProgressDialog: Dialog
    private lateinit var mWarningDialog: Dialog
    private var mAgreeDialog: Dialog? = null
    private lateinit var binding : ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun setNavigationBarColor(color : Int){
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.navigationBarColor = color
    }

     fun setStatusBarColor(color: Int, lightStatusBar : Boolean) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = lightStatusBar  // true or false as desired.
        // And then you can set any background color to the status bar.
        window.statusBarColor = color
    }

    fun hideNavigationBar(mainContainer : View){
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, mainContainer).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    @Suppress("DEPRECATION")
    fun setBackgroundFullScreen() {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.navigationBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

// Set window background color to transparent
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun showErrorSnackBar(message: String, errorMessage: Boolean?) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        if (errorMessage != null){
        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.red
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.green
                )
            )
        }
    }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.black
                )
            )
        }
        snackBar.show()
    }

    fun showProgressDialog() {
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.loading_dialog_layout)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //Start the dialog and display it on screen.
        if (window != null && window.hasFeature(Window.FEATURE_NO_TITLE)) {
            mProgressDialog.show()
        }
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun showWarningDialog(content : String, onConfirmed : () -> Unit) {
        mWarningDialog = Dialog(this)
        mWarningDialog.setContentView(R.layout.warning_dialog_layout)
        mWarningDialog.setCancelable(true)
        mWarningDialog.setCanceledOnTouchOutside(true)
        mWarningDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bindingDialog = WarningDialogLayoutBinding.inflate(layoutInflater)
        mWarningDialog.setContentView(bindingDialog.root)
        bindingDialog.tvContent.text = content
        bindingDialog.btnConfirm.setOnClickListener {
            onConfirmed.invoke()
            hideWarningDialog()
        }
        bindingDialog.btnCancel.setOnClickListener {
            hideWarningDialog()
        }


        if (window != null && window.hasFeature(Window.FEATURE_NO_TITLE)) {
            mWarningDialog.show()
        }
    }
    fun hideWarningDialog() {
        mWarningDialog.dismiss()
    }

    fun showAgreeDialog(context:Context, title : String, content : String, drawable: Drawable?,onConfirmed : () -> Unit) {
        mAgreeDialog = Dialog(context)
        mAgreeDialog?.setContentView(R.layout.agree_dialog_layout)
        mAgreeDialog?.setCancelable(true)
        mAgreeDialog?.setCanceledOnTouchOutside(true)
        mAgreeDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bindingDialog = AgreeDialogLayoutBinding.inflate(layoutInflater)
        mAgreeDialog?.setContentView(bindingDialog.root)
        bindingDialog.tvTitle.text = title
        bindingDialog.tvContent.text = content
        bindingDialog.btnConfirm.setOnClickListener {
            onConfirmed.invoke()
        }
        if (drawable != null){
            bindingDialog.imIcon.setImageDrawable(drawable)
        }else{
            bindingDialog.imIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_warning))
        }

        if (window.hasFeature(Window.FEATURE_NO_TITLE)) {
            mAgreeDialog?.show()
        }
    }

    fun hideAgreeDialog() {
        mAgreeDialog?.dismiss()
    }




}