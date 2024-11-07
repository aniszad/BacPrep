package com.tp.bacprep.presentation.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.tp.bacprep.R
import com.tp.bacprep.databinding.AgreeDialogLayoutBinding
import com.tp.bacprep.databinding.LinearProgressDialogLayoutBinding
import com.tp.bacprep.databinding.WarningDialogLayoutBinding

open class BaseFragment : Fragment() {
    private lateinit var mProgressDialog: Dialog
    private lateinit var mProgressIndicatorDialog : Dialog
    private lateinit var bindingProgressIndicator : LinearProgressDialogLayoutBinding
    private lateinit var mWarningDialog: Dialog
    var mAgreeDialog: Dialog? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }
    fun showLinearProgressIndicatorDialog() {
        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressIndicatorDialog = Dialog(requireContext())
        bindingProgressIndicator = LinearProgressDialogLayoutBinding.inflate(layoutInflater)
        mProgressIndicatorDialog.setContentView(bindingProgressIndicator.root)
        val window: Window? = mProgressIndicatorDialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bindingProgressIndicator.linearProgressDialog.isIndeterminate = false
        bindingProgressIndicator.linearProgressDialog.max = 100
        mProgressIndicatorDialog.setCancelable(false)
        mProgressIndicatorDialog.setCanceledOnTouchOutside(false)
        //Start the dialog and display it on screen.
        mProgressIndicatorDialog.show()
        bindingProgressIndicator.linearProgressDialog.progress = 50

    }
    fun hideLinearProgressIndicatorDialog() {
        mProgressIndicatorDialog.dismiss()
    }

    fun updateLinearProgressIndicator(progress : Int){
        Toast.makeText(requireContext(), progress.toString(), Toast.LENGTH_SHORT).show()
        bindingProgressIndicator.linearProgressDialog.progress = progress
        bindingProgressIndicator.tvProgressValue.text = buildString {
        append(progress)
        append("%")
    }
    }
    fun showProgressDialog() {

        mProgressDialog = Dialog(requireContext())


        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.loading_dialog_layout)
        val window: Window? = mProgressDialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }
    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
    fun showErrorSnackBar(view: View,message: String, errorMessage: Boolean) {
            val snackBar = Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_LONG
            )
            val snackBarView = snackBar.view

            if (errorMessage) {
                snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
            } else {
                snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
            }
            snackBar.show()

    }

    fun showAgreeDialog(context: Context, title : String, content : String, drawable: Drawable?, onConfirmed : () -> Unit) {
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

            mAgreeDialog?.show()
    }

    fun hideAgreeDialog() {
        mAgreeDialog?.dismiss()


    }
    fun showWarningDialog(context : Context, content : String, onConfirmed : () -> Unit) {
        mWarningDialog = Dialog(context)
        mWarningDialog.setContentView(R.layout.warning_dialog_layout)
        mWarningDialog.setCancelable(true)
        mWarningDialog.setCanceledOnTouchOutside(true)
        mWarningDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val bindingDialog = WarningDialogLayoutBinding.inflate(layoutInflater)
        mWarningDialog.setContentView(bindingDialog.root)
        bindingDialog.tvContent.text = content
        bindingDialog.btnConfirm.setOnClickListener {
            onConfirmed.invoke()
        }
        bindingDialog.btnCancel.setOnClickListener {
            hideWarningDialog()
        }
            mWarningDialog.show()

    }
     fun hideWarningDialog() {
        mWarningDialog.dismiss()
    }
}