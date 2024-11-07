package com.tp.bacprep.presentation.fragments.admin.dialogFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tp.bacprep.R
import com.tp.bacprep.databinding.DialogFragmentFlairBottomSheetBinding
import com.tp.bacprep.presentation.viewmodels.CreatePostViewModel

class FlairBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding : DialogFragmentFlairBottomSheetBinding? = null
    private val viewModel : CreatePostViewModel by activityViewModels()
    private lateinit var checkBoxList : List<CheckBox>

    private var myFlairsList : MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentFlairBottomSheetBinding.inflate(inflater, container, false)
        binding?.apply {
            checkBoxList = listOf(
                cbSemster1,
                cbSemester2,
                cbSemester3,
                cbFiliereScienctific,
                cbFiliereMatheleme,
                cbFiliereLangues,
                cbFiliereLettresPhilo,
                cbFiliereGestion,
                cbCours,
                cbExercices,
                cbSubjects,
                cbResume,
                cbFiliereTechMathMec,
                cbFiliereTechMathEle,
                cbFiliereTechMathCiv,
                cbFiliereTechMathMeth,
                cbMath,
                cbScience,
                cbArabe,
                cbPhysique,
                cbEnglish,
                cbFrench,
                cbSisl,
                cbHis,
                cbGeo,
                cbPhilo,
                cbAdvice,
                cbInformation
            )
        }
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.flairList.value != null){
            myFlairsList = viewModel.flairList.value?.let { ArrayList(it) } ?: mutableListOf()
            setCheckedBoxes(myFlairsList)
        }
        binding?.apply {
            btnApplyFlairs.setOnClickListener {
                viewModel.setFlairList(myFlairsList)
                dismiss()
            }
            for (checkBox in checkBoxList){
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked){
                        myFlairsList.add(checkBox.text.toString())
                    }else{
                        myFlairsList.remove(checkBox.text.toString())
                    }
                    checkApplyPossibility()
                }
            }
        }
    }

    private fun setCheckedBoxes(flairList: List<String>?) {
        binding?.apply {
        if (flairList != null && ::checkBoxList.isInitialized){
            for (checkBox in checkBoxList){
                if (flairList.contains(checkBox.text.toString())){
                    checkBox.isChecked = true
                }
            }
        }
    }
    }

    private fun checkApplyPossibility() {
        if (viewModel.flairList.value != null){
            Toast.makeText(requireContext(), "${myFlairsList == viewModel.flairList.value!!}"+myFlairsList.toString()+"/////"+viewModel.flairList.value.toString(), Toast.LENGTH_SHORT).show()
            if (myFlairsList == viewModel.flairList.value!!){
                binding?.btnApplyFlairs?.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
                binding?.btnApplyFlairs?.isClickable = false
            }else{
                binding?.btnApplyFlairs?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding?.btnApplyFlairs?.isClickable = true
            }
        }else{
            if(myFlairsList.isNotEmpty()){
                binding?.btnApplyFlairs?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                binding?.btnApplyFlairs?.isClickable = true
            }else{
                binding?.btnApplyFlairs?.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
                binding?.btnApplyFlairs?.isClickable = false
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.TransparentBottomSheetDialog

    }
}