package com.tp.bacprep.presentation.fragments.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tp.bacprep.R
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.ModulesProgressAdapter
import com.tp.bacprep.databinding.FragmentProgressBinding
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.presentation.activities.user.EditProgressActivity
import com.tp.bacprep.presentation.viewmodels.ModulesProgressViewModel
import com.tp.bacprep.util.BounceEdgeEffectFactory
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.modulesprograms.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month


@AndroidEntryPoint
class ProgressFragment : Fragment(), ModulesProgressAdapter.ModuleProgressClickListener, ModulesProgressAdapter.UpdateOverAllProgressListener{
    private lateinit var binding : FragmentProgressBinding
    private lateinit var modulesProgressAdapter : ModulesProgressAdapter
    private val modulesProgressVm : ModulesProgressViewModel by viewModels()
    private lateinit var branches : List<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTimeUntilExam()

        modulesProgressVm.getMyBranch(){ user ->
            if (user != null){
                modulesProgressVm.getMyModulesProgress(user.branch) {    modulesProgressList ->
                    when(user.branch){
                        Constants.SCIENCE_BRANCH->{
                            setModulesProgressRv(modulesProgressList, SciPrograms())
                        }
                        Constants.MATH_BRANCH->{
                            setModulesProgressRv(modulesProgressList, MathPrograms())
                        }
                        Constants.GESTION_BRANCH->{
                            setModulesProgressRv(modulesProgressList, GestionPrograms())
                        }
                        Constants.LETTRE_BRANCH->{
                            setModulesProgressRv(modulesProgressList, PhiloProgram())
                        }
                        Constants.LANGUES_BRANCH->{
                            setModulesProgressRv(modulesProgressList, LangProgram())
                        }
                        Constants.MATH_TECH_BRANCH_MEC->{
                            setModulesProgressRv(modulesProgressList, TechMathProgramMec())
                        }
                        Constants.MATH_TECH_BRANCH_ELE->{

                            requireActivity().runOnUiThread {
                                Toast.makeText(requireContext(), user.branch.toString(), Toast.LENGTH_SHORT).show()
                            }
                            setModulesProgressRv(modulesProgressList, TechMathProgramEle())
                        }
                        Constants.MATH_TECH_BRANCH_CIV->{
                            setModulesProgressRv(modulesProgressList, TechMathProgramCiv())
                        }
                        Constants.MATH_TECH_BRANCH_METH->{
                            setModulesProgressRv(modulesProgressList, TechMathProgramMeth())
                        }
                    }
                }

            }

        }
    }

    private fun setModulesProgressRv(modulesProgressList: List<ModuleProgress>?, moduleProgram : BranchProgram) {
        requireActivity().runOnUiThread {
            binding.apply {
            if (!modulesProgressList.isNullOrEmpty()) {
                modulesProgressAdapter = ModulesProgressAdapter(requireContext(), moduleProgram, modulesProgressList)
                modulesProgressAdapter.setModuleProgressClickListener(this@ProgressFragment)
                modulesProgressAdapter.setUpdateOverAllProgressListener(this@ProgressFragment)
                rvModulesProgress.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rvModulesProgress.edgeEffectFactory = BounceEdgeEffectFactory()
                rvModulesProgress.adapter = modulesProgressAdapter
        } else {
                modulesProgressAdapter = ModulesProgressAdapter(
                    requireContext(),
                    moduleProgram,
                    listOf()
                )
                modulesProgressAdapter.setModuleProgressClickListener(this@ProgressFragment)
                modulesProgressAdapter.setUpdateOverAllProgressListener(this@ProgressFragment)
                rvModulesProgress.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rvModulesProgress.edgeEffectFactory = BounceEdgeEffectFactory()
                rvModulesProgress.adapter = modulesProgressAdapter
        }
    }
        }

    }


    override fun onResume() {
        super.onResume()
        modulesProgressVm.getMyBranch(){ user ->
            if (user != null){
                val branch = user.branch
                modulesProgressVm.getMyModulesProgress(branch) {    modulesProgressList ->
                    when(branch){
                        Constants.SCIENCE_BRANCH->{
                            setModulesProgressRv(modulesProgressList, SciPrograms())
                        }
                        Constants.MATH_BRANCH->{
                            setModulesProgressRv(modulesProgressList, MathPrograms())
                        }
                        Constants.GESTION_BRANCH->{
                            setModulesProgressRv(modulesProgressList, GestionPrograms())
                        }
                        Constants.LETTRE_BRANCH->{
                            setModulesProgressRv(modulesProgressList, PhiloProgram())
                        }
                        Constants.LANGUES_BRANCH->{
                            setModulesProgressRv(modulesProgressList, LangProgram())
                        }
                        Constants.MATH_TECH_BRANCH_MEC->{
                            setModulesProgressRv(modulesProgressList, TechMathProgramMec())
                        }
                        Constants.MATH_TECH_BRANCH_ELE->{
                            setModulesProgressRv(modulesProgressList, TechMathProgramEle())
                        }
                        Constants.MATH_TECH_BRANCH_CIV->{
                            setModulesProgressRv(modulesProgressList, TechMathProgramCiv())
                        }
                        Constants.MATH_TECH_BRANCH_METH->{
                            setModulesProgressRv(modulesProgressList, TechMathProgramMeth())
                        }
                    }
                }
            }

        }
    }

    override fun startEditProgressActivity(moduleToEdit: String, branch: BranchProgram) {
        val intent = Intent(requireActivity(), EditProgressActivity::class.java)
        intent.putExtra(Constants.MODULE_PROGRESS_TO_EDIT, moduleToEdit)
        intent.putExtra(Constants.FILIERE_PROGRESS_TO_EDIT, branch)
        Toast.makeText(requireContext(), branch.BRANCH_NAME, Toast.LENGTH_SHORT).show()
        requireActivity().startActivity(intent)
    }

    override fun updateOverAllProgress(progress: Double, reset : Boolean) {
        val decimalFormat = DecimalFormat("#.##")
        binding.apply {
            if (reset) {
                progressBarOverall.progress = 0
                tvOverallProgress.text = buildString {
                    append("0.0%")
                }
            }
            val progressString = tvOverallProgress.text.toString().removeSuffix("%")
            val progressDouble = replaceCommaWithPoint(decimalFormat.format(progressString.toDouble() + progress/10)).toDouble()
            progressBarOverall.setProgress(progressDouble.toInt())
            tvOverallProgress.text = buildString {
                append("$progressDouble%")
            }

        }
    }

    private fun replaceCommaWithPoint(number : String) : String{
        return number.replace(',', '.')
    }

    private fun getTimeUntilExam(){
        val targetDate = LocalDate.of(2024, Month.JUNE, 9)
        val timeRemaining = Duration.between(LocalDateTime.now(), LocalDateTime.of(targetDate, java.time.LocalTime.MIDNIGHT))
        val monthsRemaining = timeRemaining.toDays() / 30
        val daysRemaining = timeRemaining.toDays() % 30
        val hoursRemaining = timeRemaining.toHours() % 24
        setTimeRemainingView(monthsRemaining, daysRemaining, hoursRemaining)
    }

    private fun setTimeRemainingView(monthsRemaining: Long, daysRemaining: Long, hoursRemaining: Long) {
        binding.tvTimeRemainingUntilExam.text = buildString {
            append(monthsRemaining)
            append(" ")
            append(getString(R.string.mois_pluriel))
            append("\n")
            append(daysRemaining)
            append(" ")
            append(getString(R.string.jours))
            append("\n")
            append(hoursRemaining)
            append(" ")
            append(getString(R.string.heures))
        }
    }
}