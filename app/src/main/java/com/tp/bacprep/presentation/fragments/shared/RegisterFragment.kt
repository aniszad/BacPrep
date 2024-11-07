package com.tp.bacprep.presentation.fragments.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import com.tp.bacprep.R
import com.tp.bacprep.databinding.FragmentRegisterBinding
import com.tp.bacprep.domain.models.User
import com.tp.bacprep.presentation.fragments.BaseFragment
import com.tp.bacprep.presentation.viewmodels.RegisterViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.MultiLangStrings
import com.tp.bacprep.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {
    private var binding : FragmentRegisterBinding? = null
    private val viewModel : RegisterViewModel by activityViewModels()
    private lateinit var email : String     // making email a global variable to send it to
                                            // the verification fragment when the user registration is successful

    private lateinit var branches : List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setRoleSpinner()
        setBranchSpinner()
        setViewModelObservers()
        setRegisterButtonFunctionality()

    }

    private fun setBranchSpinner() {
        branches = resources.getStringArray(R.array.branches).toList()
        val spinnerBranchesAdapter = ArrayAdapter(
            requireContext(),
            R.layout.filieres_spinner_item_layout,
            branches
        )
        binding?.spinnerFiliere?.adapter = spinnerBranchesAdapter
        binding?.spinnerFiliere?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val branch :String=  when(binding?.spinnerFiliere?.selectedItem.toString()){
                    MultiLangStrings(requireContext()).SCIENCE_BRANCH ->{
                        Constants.SCIENCE_BRANCH
                    }
                    MultiLangStrings(requireContext()).MATH_BRANCH ->{
                        Constants.MATH_BRANCH
                    }
                    MultiLangStrings(requireContext()).GESTION_BRANCH ->{
                        Constants.GESTION_BRANCH
                    }
                    MultiLangStrings(requireContext()).MATH_TECH_MEC_BRANCH ->{
                        Constants.MATH_TECH_BRANCH_MEC
                    }
                    MultiLangStrings(requireContext()).MATH_TECH_ELE_BRANCH ->{
                        Constants.MATH_TECH_BRANCH_ELE
                    }
                    MultiLangStrings(requireContext()).MATH_TECH_CIV_BRANCH ->{
                        Constants.MATH_TECH_BRANCH_CIV
                    }
                    MultiLangStrings(requireContext()).MATH_TECH_METH_BRANCH ->{
                        Constants.MATH_TECH_BRANCH_METH
                    }
                    MultiLangStrings(requireContext()).LANGUES_BRANCH ->{
                        Constants.LANGUES_BRANCH
                    }
                    MultiLangStrings(requireContext()).LETTRE_BRANCH ->{
                        Constants.LETTRE_BRANCH
                    }
                    else->{
                        Constants.GESTION_BRANCH
                    }
                }
                val sharedPref = SharedPref(requireContext())
                sharedPref.setMyBranch(branch)
            }
            override fun onNothingSelected( adapterView: AdapterView<*>?) {
            }
        }
    }

    private fun setRoleSpinner() {
        // Retrieve the array from resources
        val roleChoices = resources.getStringArray(R.array.role_choices)
        // Create an ArrayAdapter with the choices
        val adapter = ArrayAdapter(requireContext(), R.layout.filieres_spinner_item_layout, roleChoices)
        // Set the dropdown layout style
        adapter.setDropDownViewResource(R.layout.filieres_spinner_item_layout)
        // Set the adapter to the Spinner
        binding?.apply {
            spinnerRole.adapter = adapter
            spinnerRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, adapterView: View?, position: Int, p3: Long) {
                    val selectedItem = spinnerRole.selectedItem.toString()
                    updateAccountTypeView(selectedItem)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

    }

    private fun updateAccountTypeView(selectedItem: String) {
        binding?.apply {
            when(selectedItem){
                resources.getStringArray(R.array.role_choices)[0] ->{
                    clBranch?.visibility = View.VISIBLE
                    tilCreatorAdditionalInfo?.visibility = View.GONE
                    tilTeacherAdditionalInfo?.visibility = View.GONE
                    clBranch?.visibility = View.VISIBLE
                    val constraintLayout = root.findViewById<ConstraintLayout>(R.id.cl_registration_form)
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(constraintLayout)
                    constraintSet.connect(
                        R.id.cb_policy_agreement,
                        ConstraintSet.TOP,
                        R.id.cl_branch,
                        ConstraintSet.BOTTOM,
                        0
                    )
                    constraintSet.applyTo(constraintLayout)
                }
                resources.getStringArray(R.array.role_choices)[1] ->{
                    tilCreatorAdditionalInfo?.visibility = View.VISIBLE
                    tilTeacherAdditionalInfo?.visibility = View.GONE
                    clBranch?.visibility = View.GONE
                    val constraintLayout = root.findViewById<ConstraintLayout>(R.id.cl_registration_form)
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(constraintLayout)
                    constraintSet.connect(
                        R.id.cb_policy_agreement,
                        ConstraintSet.TOP,
                        R.id.til_creator_additional_info,
                        ConstraintSet.BOTTOM,
                        0
                    )
                    constraintSet.applyTo(constraintLayout)
                }
                resources.getStringArray(R.array.role_choices)[2] ->{
                    tilCreatorAdditionalInfo?.visibility = View.GONE
                    tilTeacherAdditionalInfo?.visibility = View.VISIBLE
                    clBranch?.visibility = View.GONE
                    val constraintLayout = root.findViewById<ConstraintLayout>(R.id.cl_registration_form)
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(constraintLayout)
                    constraintSet.connect(
                        R.id.cb_policy_agreement,
                        ConstraintSet.TOP,
                        R.id.til_teacher_additional_info,
                        ConstraintSet.BOTTOM,
                        0
                    )
                    constraintSet.applyTo(constraintLayout)
                }
            }
        }
    }

    private fun setViewModelObservers() {
        binding?.apply {
            viewModel.registerUserResult.observe(viewLifecycleOwner){ result ->
                hideProgressDialog()
                if (result.second){
                    viewModel.setEmail(email)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fcv_register, EmailVerificationFragment())
                        .setCustomAnimations(R.anim.frag_fade_in, R.anim.frag_fade_out)
                        .commit()
                }else{
                    showErrorSnackBar(root,result.first ?: getString(R.string.registration_failed), true)
                    //Toast.makeText(requireContext(), result.first ?: getString(R.string.registration_failed), Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.registerAdminResult.observe(viewLifecycleOwner){ result ->
                hideProgressDialog()
                if (result.second){
                    viewModel.setEmail(email)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fcv_register, EmailVerificationFragment())
                        .setCustomAnimations(R.anim.frag_fade_in, R.anim.frag_fade_out)
                        .commit()
                }else{
                    showErrorSnackBar(root,result.first ?: getString(R.string.registration_failed), true)
                    //Toast.makeText(requireContext(), result.first ?: getString(R.string.registration_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setRegisterButtonFunctionality() {
        binding?.apply {
            btnRegister?.setOnClickListener {
                val lastName = etNom.text.toString()
                val name = etPrenom.text.toString()
                // making email a global variable to send it to
                // the verification fragment when the user registration is successful
                email = etEmail.text.toString()
                val psw = etPassword.text.toString()
                val role = when (spinnerRole.selectedItem.toString()){
                        resources.getStringArray(R.array.role_choices)[0] ->{
                            Constants.STUDENT_ROLE
                        }
                        resources.getStringArray(R.array.role_choices)[1] ->{
                            Constants.CREATOR_ROLE
                        }
                        resources.getStringArray(R.array.role_choices)[2] ->{
                            Constants.TEACHER_ROLE
                        }
                    else->{
                        MultiLangStrings(requireContext()).STUDENT_ROLE
                    }
                }
                val branch = when (spinnerFiliere?.selectedItem.toString()){
                    resources.getStringArray(R.array.branches)[0] ->{
                        Constants.SCIENCE_BRANCH
                    }
                    resources.getStringArray(R.array.branches)[1] ->{
                        Constants.MATH_BRANCH
                    }
                    resources.getStringArray(R.array.branches)[2] ->{
                        Constants.GESTION_BRANCH
                    }
                    resources.getStringArray(R.array.branches)[3] ->{
                        Constants.LANGUES_BRANCH
                    }
                    resources.getStringArray(R.array.branches)[4] ->{
                        Constants.LETTRE_BRANCH
                    }
                    resources.getStringArray(R.array.branches)[5] ->{
                        Constants.MATH_TECH_BRANCH_MEC
                    }
                    resources.getStringArray(R.array.branches)[6] ->{
                        Constants.MATH_TECH_BRANCH_ELE
                    }
                    resources.getStringArray(R.array.branches)[7] ->{
                        Constants.MATH_TECH_BRANCH_CIV
                    }
                    resources.getStringArray(R.array.branches)[8] ->{
                        Constants.MATH_TECH_BRANCH_METH
                    }
                    else->{
                        ""
                    }
                }
                if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || psw.isEmpty() ){
                    Toast.makeText(requireContext(), getString(R.string.veuillez_remplir_les_champs_necessaires), Toast.LENGTH_SHORT).show()
                }else{
                    if (role == MultiLangStrings(requireContext()).STUDENT_ROLE){
                        registerUser(role = role, name = name, lastName = lastName,
                            email = email, branch,psw = psw)
                    }else if(role == MultiLangStrings(requireContext()).CREATOR_ROLE || role == MultiLangStrings(requireContext()).TEACHER_ROLE){
                        Toast.makeText(requireContext(), "im heere", Toast.LENGTH_SHORT).show()
                        registerAdmin(role = role, name = name, lastName = lastName,
                            email = email, branch,psw = psw)
                    }
                }

            }
        }
    }

    private fun registerAdmin(role:String, name: String, lastName:String, email:String, branch : String,psw:String) {
            showProgressDialog()
            viewModel.registerAdmin(
                User(
                    name = name,
                    lastName = lastName,
                    email = email,
                    image = 0,
                    branch = branch,
                    role = role,
                    emailVerified = false,
                    blocked = false
                ),
                psw = psw
            )
    }

    private fun registerUser(role:String, name: String, lastName:String, email:String, branch : String, psw:String) {
        showProgressDialog()
        viewModel.registerUser(
            User(
                name = name,
                lastName = lastName,
                email = email,
                image = 0,
                branch= branch,
                role = role,
                emailVerified = false,
                blocked = false
            ),
            psw = psw
        )
    }

}