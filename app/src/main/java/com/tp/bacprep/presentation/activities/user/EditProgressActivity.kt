package com.tp.bacprep.presentation.activities.user

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.presentation.adapters.expandablelistsAdapters.ModuleProgramExpandableListAdapter
import com.tp.bacprep.databinding.ActivityEditProgressBinding
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.presentation.viewmodels.ModulesProgressViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.modulesprograms.BranchProgram
import com.tp.bacprep.util.modulesprograms.SciPrograms
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProgressActivity : BaseActivity(),
    ModuleProgramExpandableListAdapter.EditProgressDataListener {
    private lateinit var binding : ActivityEditProgressBinding
    private val modulesProgressVm : ModulesProgressViewModel by viewModels()
    private lateinit var moduleToShow : String
    private lateinit var programToEdit : BranchProgram
    private lateinit var elvAdapter : ModuleProgramExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarColor(ContextCompat.getColor(this@EditProgressActivity, R.color.light_gray_to_deep_charcoal), true)
        if (intent != null){
            if (intent.extras != null){
                moduleToShow = intent.getStringExtra(Constants.MODULE_PROGRESS_TO_EDIT) ?: ""
                programToEdit = intent.parcelable(Constants.FILIERE_PROGRESS_TO_EDIT) ?: SciPrograms()
                Toast.makeText(this@EditProgressActivity, programToEdit.BRANCH_NAME, Toast.LENGTH_SHORT).show()
            }
        }
        setExpandableListView()
    }
    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    private fun setExpandableListView() {
        binding.elvModuleProgram.setGroupIndicator(null);
        binding.elvModuleProgram.setChildIndicator(null);
        binding.elvModuleProgram.setChildDivider(ContextCompat.getDrawable(this@EditProgressActivity, R.color.light_gray_to_deep_charcoal))
        binding.elvModuleProgram.setDivider(ContextCompat.getDrawable(this@EditProgressActivity, R.color.white))
        binding.elvModuleProgram.setDividerHeight(2);
        modulesProgressVm.getModuleProgress(moduleToShow, programToEdit.BRANCH_NAME){moduleProgress ->
            runOnUiThread {
                val program : Pair<List<String>, HashMap<String, List<String>>> = getModuleProgram()
                if (moduleProgress!=null){
                    elvAdapter = ModuleProgramExpandableListAdapter(program.first, program.second, moduleProgress.progressList)
                    elvAdapter.setEditProgressListener(this@EditProgressActivity)
                    binding.elvModuleProgram.setAdapter(elvAdapter)
                }else{
                    elvAdapter = ModuleProgramExpandableListAdapter( program.first, program.second,
                        listOf()
                    )
                    elvAdapter.setEditProgressListener(this@EditProgressActivity)
                    binding.elvModuleProgram.setAdapter(elvAdapter)
                }
            }


        }
    }

    private fun getModuleProgram(): Pair<List<String>, HashMap<String, List<String>>> {
        return when(moduleToShow){
            Constants.SCIENCE ->{
                programToEdit.getScienceProgram()
            }
            Constants.MATHEMATIQUE ->{
                programToEdit.getMathProgram()
            }
            Constants.PHYSIQUE ->{
                programToEdit.getPhysicsProgram()
            }
            Constants.HISTOIRE ->{
                programToEdit.getHisProgram()
            }
            Constants.GEOGRAPHIE ->{
                programToEdit.getGeoProgram()
            }
            Constants.FRANCAIS ->{
                programToEdit.getFrenchProgram()
            }
            Constants.ANGLAIS ->{
                programToEdit.getEnlgishProgram()
            }
            Constants.ARABE ->{
                programToEdit.getArabProgram()
            }
            Constants.S_ISLAMIQUE ->{
                programToEdit.getIslamicProgram()
            }
            Constants.PHILO ->{
                programToEdit.getPhiloProgram()
            }
            Constants.GESTION_COMPTABLE_ET_FINANCIERE ->{
                programToEdit.getGestionFinanciereProgram()
            }
            Constants.ECONOMIE_ET_GESTION ->{
                programToEdit.getEconomieProgram()
            }
            Constants.LOI ->{
                programToEdit.getLoiProgram()
            }
            Constants.ALLEMAND ->{
                programToEdit.getGermanProgram()
            }
            Constants.ESPAGNOL ->{
                programToEdit.getSpanishProgram()
            }
            Constants.TECHNOLOGIE_ELEC ->{
                programToEdit.getElectricProgram()
            }

            else->{
                Pair(listOf(""), hashMapOf("" to listOf("")))
            }
        }
    }

    override fun updateProgress(finishedElement: String) {
        modulesProgressVm.getModuleProgress(moduleToShow, programToEdit.BRANCH_NAME){ moduleProgress ->
            if (moduleProgress!=null){
                if (moduleProgress.progressList.contains(finishedElement)){
                    val updatedProgressList = moduleProgress.progressList.toMutableList()
                    updatedProgressList.remove(finishedElement)
                    moduleProgress.progressList = updatedProgressList
                    modulesProgressVm.updateModuleProgress(moduleProgress)
                }else{
                    val updatedProgressList = moduleProgress.progressList.toMutableList()
                    updatedProgressList.add(finishedElement)
                    moduleProgress.progressList = updatedProgressList
                    modulesProgressVm.updateModuleProgress(moduleProgress)
                }
            }else{
                modulesProgressVm.saveModuleProgress(ModuleProgress(moduleToShow, programToEdit.BRANCH_NAME,listOf(finishedElement)))
            }
        }
    }
}