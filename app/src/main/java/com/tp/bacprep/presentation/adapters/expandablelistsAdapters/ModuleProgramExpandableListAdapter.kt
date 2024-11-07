package com.tp.bacprep.presentation.adapters.expandablelistsAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.tp.bacprep.databinding.ModuleProgramChildItemLayoutBinding
import com.tp.bacprep.databinding.ModuleProgramGroupItemLayoutBinding


class ModuleProgramExpandableListAdapter(
    private val groups: List<String>,
    private val items: HashMap<String, List<String>>,
    progressData: List<String>
) : BaseExpandableListAdapter() {

    private lateinit var editProgressDataListener: EditProgressDataListener
    private val childCheckedStates: HashMap<String, Boolean> = hashMapOf()

    interface EditProgressDataListener {
        fun updateProgress(finishedElement: String)
    }

    fun setEditProgressListener(editProgressDataListener: EditProgressDataListener) {
        this.editProgressDataListener = editProgressDataListener
    }

    init {
        for (group in groups) {
            for (child in items[group] ?: emptyList()) {
                childCheckedStates[child] = progressData.contains(child)
            }
        }
    }

    override fun getGroupCount(): Int {
        return groups.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return items[groups[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return groups[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return items[groups[groupPosition]]?.get(childPosition) ?: ""
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val binding = ModuleProgramGroupItemLayoutBinding.inflate(LayoutInflater.from(parent?.context), parent,  false)
        binding.tvGroupTitle.text = groups[groupPosition]
        return binding.root
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val binding = ModuleProgramChildItemLayoutBinding.inflate(LayoutInflater.from(parent?.context), parent,  false)
        val currentChild = items[groups[groupPosition]]?.get(childPosition)
        if (currentChild != null) {
            binding.tvChildTitle.text = currentChild
            binding.cbChild.isChecked = childCheckedStates.getOrDefault(currentChild, false)
            binding.cbChild.setOnCheckedChangeListener { _, isChecked ->
                childCheckedStates[currentChild] = isChecked
                editProgressDataListener.updateProgress(currentChild)
            }
        }

        return binding.root
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

}
