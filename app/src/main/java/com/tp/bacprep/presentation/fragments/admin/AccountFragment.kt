package com.tp.bacprep.presentation.fragments.admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.tp.bacprep.R
import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.databinding.FragmentAccountBinding
import com.tp.bacprep.presentation.activities.admin.MyPostsActivity
import com.tp.bacprep.presentation.activities.admin.ReceivedRequestsActivity
import com.tp.bacprep.presentation.activities.shared.LoginActivity
import com.tp.bacprep.presentation.fragments.BaseFragment
import com.tp.bacprep.presentation.viewmodels.AdminSharedViewModel
import com.tp.bacprep.util.SharedPref

class AccountFragment : BaseFragment(){
    private var binding : FragmentAccountBinding? = null
    private val sharedVm : AdminSharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return (binding?.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedVm.resetEmailSent.observe(viewLifecycleOwner){ sendResult ->
            if (sendResult.second){
                requireActivity().runOnUiThread {
                    showErrorSnackBar(requireView(), getString(R.string.email_de_changement_de_mot_de_passe_envoy), false)
                }
            }else{
                requireActivity().runOnUiThread {
                    showErrorSnackBar(requireView(),getString(R.string.email_non_envoy), true)
                }
            }
        }
        sharedVm.loggedOut.observe(viewLifecycleOwner){loggedOut ->
            if (loggedOut){
                requireActivity().startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }

        setUiFunctionality()
        setAdminView()
    }

    private fun setUiFunctionality() {
        binding?.apply {
            cardLogout.setOnClickListener {
                sharedVm.logout()
            }
            cardMyPosts.setOnClickListener {
                requireActivity().startActivity(Intent(requireActivity(),MyPostsActivity::class.java))
            }
            cardChangePassword.setOnClickListener {
                showWarningDialog(requireContext(), getString(R.string.change_psw_warning_text)) { sendResetEmail() }
            }
            switchTheme.setOnClickListener {
                if (switchTheme.isPressed){
                    val isDarkMode = SharedPref(requireContext()).getThemeMode()
                    if (isDarkMode){
                        SharedPref(requireContext()).setThemeMode(isDarkMode = false)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }else{
                        SharedPref(requireContext()).setThemeMode(isDarkMode = true)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
            }
            cardRequest.setOnClickListener {
                startActivity(Intent(requireActivity(), ReceivedRequestsActivity::class.java))
            }
        }

    }

    private fun sendResetEmail() {
        sharedVm.sendResetPasswordEmail()
    }

    private fun setAdminView() {
        val sharedPref = SharedPref(requireContext())
        val userName = sharedPref.getUserName(FbAuth(requireContext()).getUserId())
        binding?.apply {
            tvMyName.text = buildString {
                append(userName)
            }
        }
        binding?.switchTheme?.isChecked = SharedPref(requireContext()).getThemeMode()
        binding?.imMyProfilePicture?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_account_clicked))

    }





}