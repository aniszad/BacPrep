package com.tp.bacprep.presentation.adapters.recyclerviewsAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tp.bacprep.databinding.ModuleItemLayoutBinding
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.util.MultiLangStrings
import com.tp.bacprep.util.modulesprograms.BranchProgram

class ModulesProgressAdapter(
    private var context : Context,
    private var branchProgram: BranchProgram,
    modulesProgressList: List<ModuleProgress>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var moduleProgressClickListener: ModuleProgressClickListener
    private lateinit var updateOverAllProgress: UpdateOverAllProgressListener

    private val progressList : MutableList<ModuleProgress> =
        branchProgram.getDefaultProgram().toMutableList()

    init{
        // replacing the module retrieved from the room db in the default modules progress list
        if (modulesProgressList.isNotEmpty()) {
            for (module in modulesProgressList) {
                val defaultModelIndex = progressList.indexOfFirst { it.moduleName == module.moduleName }
                if (defaultModelIndex != -1) {
                    // Replace the corresponding item in the default list with the retrieved item
                    progressList[defaultModelIndex] = module
                }
            }
        }

    }

    interface ModuleProgressClickListener{
        fun startEditProgressActivity(moduleToEdit: String, branch: BranchProgram)
    }

    fun setModuleProgressClickListener(moduleProgressClickListener: ModuleProgressClickListener){
        this.moduleProgressClickListener = moduleProgressClickListener
    }

    interface UpdateOverAllProgressListener{
        fun updateOverAllProgress(progress : Double, reset : Boolean)
    }

    fun setUpdateOverAllProgressListener(updateOverAllProgress: UpdateOverAllProgressListener){
        this.updateOverAllProgress = updateOverAllProgress
    }
    class ModuleProgressViewHolder(binding : ModuleItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        val tvModuleName = binding.tvModuleName
        val tvModuleProgress = binding.tvModuleProgress
        val moduleProgressBar = binding.lpiModuleProgress
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ModuleProgressViewHolder(ModuleItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return branchProgram.getDefaultProgram().size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentModule = progressList[position]
        when (holder) {
            is ModuleProgressViewHolder -> {
                holder.apply {
                    tvModuleName.text = MultiLangStrings(context).translateModuleName(currentModule.moduleName)
                    val moduleProgress = branchProgram.calculateProgress(currentModule)
                    tvModuleProgress.text = buildString {
                        append("$moduleProgress%")
                    }
                    moduleProgressBar.progress = moduleProgress.toInt()
                    updateOverAllProgress.updateOverAllProgress(moduleProgress, position==0)
                }
                holder.itemView.setOnClickListener {
                    Log.e("ehehhehehe", branchProgram.BRANCH_NAME)
                    moduleProgressClickListener.startEditProgressActivity(currentModule.moduleName, branchProgram)
                }
            }
        }
    }


}