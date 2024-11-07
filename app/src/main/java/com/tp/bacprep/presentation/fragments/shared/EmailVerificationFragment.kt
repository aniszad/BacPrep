package com.tp.bacprep.presentation.fragments.shared

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.tp.bacprep.R
import com.tp.bacprep.databinding.FragmentEmailVerificationBinding
import com.tp.bacprep.presentation.activities.shared.LoginActivity
import com.tp.bacprep.presentation.fragments.BaseFragment
import com.tp.bacprep.presentation.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailVerificationFragment : BaseFragment() {
    private var binding : FragmentEmailVerificationBinding? = null
    private val viewModel : RegisterViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observing viewModel data
        setViewModelObservers()
        // function to set buttons function
        setButtonsFunctionality()
        // function to populate textViews
        setViewsTexts()




    }

    private fun setViewsTexts() {
        binding?.apply {
            tvTitle.text = buildString {
                append(getString(R.string.finaliser_creation_du_compte))
            }

            tvStep1.text = buildString {
                append(getString(R.string._1_envoyer_un_email_de_v_rification_l_adresse))
                append(viewModel.email.value)
            }
            tvStep2.text = buildString {
                append(getString(R.string._2_cliquer_sur_le_lien_de_v_rification_dans_l_email))
            }
            tvStep3.text = buildString {
                append(getString(R.string._3_revenez_l_application_et_cliquez_sur_terminer_une_fois_la_v_rification_effectu_e))
            }
        }
    }

    private fun setButtonsFunctionality() {
        binding?.apply {
            btnSendVerifEmail.setOnClickListener {
                showProgressDialog()
                viewModel.sendVerificationEmail()
            }
            btnFinishRegistration.setOnClickListener {
                viewModel.isCurrentUserVerified()
            }
        }
    }

    private fun setViewModelObservers() {
        viewModel.verifyEmailSentResult.observe(viewLifecycleOwner){ result ->
            if (result.second){
                binding?.apply {
                    btnSendVerifEmail.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.darker_gray
                        )
                    )
                    btnSendVerifEmail.isEnabled = false
                    tvEmailStatus.text = getString(R.string.email_de_v_rification_envoy)
                }
                hideProgressDialog()
            }else{
                hideProgressDialog()
                showErrorSnackBar(requireView(),buildString {
                    append(result.first.toString())
                    append(getString(R.string.email_de_v_rification_non_envoy))
                }, true)
            }
        }
        viewModel.isCurrentUserVerified.observe(viewLifecycleOwner){ result ->
            if (result.second){
                viewModel.signOut()
                requireActivity().startActivity(Intent(requireActivity(),LoginActivity::class.java))
                requireActivity().finish()
            }else{
                Toast.makeText(requireContext(), getString(R.string.votre_compte_n_est_pas_encore_verifie), Toast.LENGTH_SHORT).show()
            }
        }
    }


}