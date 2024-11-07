package com.tp.bacprep.presentation.activities.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.databinding.ActivityAverageCalculatorBinding
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.modulesprograms.*
import com.tp.bacprep.util.MultiLangStrings
import com.tp.bacprep.util.SharedPref
import java.lang.Exception

class AverageCalculatorActivity : BaseActivity() {

    private lateinit var binding : ActivityAverageCalculatorBinding
    private lateinit var currentTable : View

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAverageCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting background color
        setStatusBarColor(
            ContextCompat.getColor(this@AverageCalculatorActivity, R.color.black),
            !SharedPref(this@AverageCalculatorActivity).getThemeMode()
        )
        // setting the functionality of the spinner when an element gets clicked clicked
        setSpinnerFunctionality()
    }

    private fun setSpinnerFunctionality() {
        val branches = resources.getStringArray(R.array.branches).toList()
        val spinnerBranchesAdapter = ArrayAdapter(
            this@AverageCalculatorActivity,
            R.layout.filieres_spinner_item_layout,
            branches
        )
        binding.spinnerFiliere.adapter = spinnerBranchesAdapter
        binding.spinnerFiliere.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(binding.spinnerFiliere.selectedItem.toString()){
                    MultiLangStrings(this@AverageCalculatorActivity).SCIENCE_BRANCH ->{
                        buildTable(Constants.SCIENCE_BRANCH)
                    }
                    MultiLangStrings(this@AverageCalculatorActivity).MATH_BRANCH ->{
                        buildTable(Constants.MATH_BRANCH)
                    }
                    MultiLangStrings(this@AverageCalculatorActivity).GESTION_BRANCH ->{
                        buildTable(Constants.GESTION_BRANCH)
                    }
                    MultiLangStrings(this@AverageCalculatorActivity).MATH_TECH_MEC_BRANCH ->{
                        buildTable(Constants.MATH_TECH_BRANCH_MEC)
                    }
                    MultiLangStrings(this@AverageCalculatorActivity).MATH_TECH_ELE_BRANCH ->{
                        buildTable(Constants.MATH_TECH_BRANCH_ELE)
                    }
                    MultiLangStrings(this@AverageCalculatorActivity).MATH_TECH_CIV_BRANCH ->{
                        buildTable(Constants.MATH_TECH_BRANCH_CIV)
                    }
                    MultiLangStrings(this@AverageCalculatorActivity).MATH_TECH_METH_BRANCH ->{
                       buildTable(Constants.MATH_TECH_BRANCH_METH)
                    }
                    MultiLangStrings(this@AverageCalculatorActivity).LANGUES_BRANCH ->{
                        buildTable(Constants.LANGUES_BRANCH)
                    }
                    MultiLangStrings(this@AverageCalculatorActivity).LETTRE_BRANCH ->{
                        buildTable(Constants.LETTRE_BRANCH)
                    }
                    else->{
                        buildTable(Constants.SCIENCE_BRANCH)
                    }
                }
            }
            override fun onNothingSelected( adapterView: AdapterView<*>?) {
            }
        }

    }
    private fun buildTable(branch : String) {
        binding.apply {
            when(branch){
                Constants.SCIENCE_BRANCH ->{
                    val inflater = LayoutInflater.from(applicationContext)
                    val newLayout = inflater.inflate(R.layout.science_calculate_table, includeLayout.root, false)
                    newLayout.id = R.id.layout_science_calculate_table
                    newLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
                    includeLayout.root.removeAllViews()
                    currentTable = newLayout
                    includeLayout.root.addView(newLayout)
                    setEditTextsListeners(listOf<EditText>(
                        newLayout.findViewById(R.id.et_science),
                        newLayout.findViewById(R.id.et_math),
                        newLayout.findViewById(R.id.et_physique),
                        newLayout.findViewById(R.id.et_arabe),
                        newLayout.findViewById(R.id.et_francais),
                        newLayout.findViewById(R.id.et_anglais),
                        newLayout.findViewById(R.id.et_his_geo),
                        newLayout.findViewById(R.id.et_philo),
                        newLayout.findViewById(R.id.et_s_islamique),
                        newLayout.findViewById(R.id.et_tamazight),
                        newLayout.findViewById(R.id.et_sport)
                    ))
                    btnCalculate.setOnClickListener {
                        val noteSci = newLayout.findViewById<EditText>(R.id.et_science).text.toString()
                        val noteMath = newLayout.findViewById<EditText>(R.id.et_math).text.toString()
                        val notePhy = newLayout.findViewById<EditText>(R.id.et_physique).text.toString()
                        val noteArab = newLayout.findViewById<EditText>(R.id.et_arabe).text.toString()
                        val noteFr = newLayout.findViewById<EditText>(R.id.et_francais).text.toString()
                        val noteAng = newLayout.findViewById<EditText>(R.id.et_anglais).text.toString()
                        val noteHisGeo = newLayout.findViewById<EditText>(R.id.et_his_geo).text.toString()
                        val notePhilo = newLayout.findViewById<EditText>(R.id.et_philo).text.toString()
                        val noteIsl = newLayout.findViewById<EditText>(R.id.et_s_islamique).text.toString()


                        val noteTamazight : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_tamazight).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_tamazight).text.toString()
                        }else{
                            null
                        }
                        val noteSport : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_sport).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_sport).text.toString()
                        }else{
                            null
                        }

                        if (
                            noteSci.isNotBlank() &&
                            noteMath.isNotBlank() &&
                            notePhy.isNotBlank() &&
                            noteArab.isNotBlank() &&
                            noteFr.isNotBlank() &&
                            noteAng.isNotBlank() &&
                            noteHisGeo.isNotBlank() &&
                            notePhilo.isNotBlank() &&
                            noteIsl.isNotBlank()
                        ){
                            val average : Double = SciPrograms().calculateAverage(
                                noteSci = noteSci.toDouble(),
                                noteMath = noteMath.toDouble(),
                                notePhy = notePhy.toDouble(),
                                noteArab = noteArab.toDouble(),
                                noteFr = noteFr.toDouble(),
                                noteAng = noteAng.toDouble(),
                                noteHisGeo = noteHisGeo.toDouble(),
                                notePhilo = notePhilo.toDouble(),
                                noteIsl = noteIsl.toDouble(),
                                noteTamazight = noteTamazight?.toDouble(),
                                noteSport = noteSport?.toDouble(),
                            )
                            newLayout.findViewById<TextView>(R.id.tv_result_average).text = average.toString()
                        }else{
                            Toast.makeText(this@AverageCalculatorActivity,"Notes non complètes", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                Constants.MATH_BRANCH ->{
                    val inflater = LayoutInflater.from(applicationContext)
                    val newLayout = inflater.inflate(R.layout.math_calculate_table, includeLayout.root, false)
                    newLayout.id = R.id.layout_math_calculate_table
                    newLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
                    includeLayout.root.removeAllViews()
                    currentTable = newLayout
                    includeLayout.root.addView(newLayout)

                    setEditTextsListeners(listOf<EditText>(
                        newLayout.findViewById(R.id.et_science),
                        newLayout.findViewById(R.id.et_math),
                        newLayout.findViewById(R.id.et_physique),
                        newLayout.findViewById(R.id.et_arabe),
                        newLayout.findViewById(R.id.et_francais),
                        newLayout.findViewById(R.id.et_anglais),
                        newLayout.findViewById(R.id.et_his_geo),
                        newLayout.findViewById(R.id.et_philo),
                        newLayout.findViewById(R.id.et_s_islamique),
                        newLayout.findViewById(R.id.et_tamazight),
                        newLayout.findViewById(R.id.et_sport)
                    ))
                    btnCalculate.setOnClickListener {
                        val noteSci = newLayout.findViewById<EditText>(R.id.et_science).text.toString()
                        val noteMath = newLayout.findViewById<EditText>(R.id.et_math).text.toString()
                        val notePhy = newLayout.findViewById<EditText>(R.id.et_physique).text.toString()
                        val noteArab = newLayout.findViewById<EditText>(R.id.et_arabe).text.toString()
                        val noteFr = newLayout.findViewById<EditText>(R.id.et_francais).text.toString()
                        val noteAng = newLayout.findViewById<EditText>(R.id.et_anglais).text.toString()
                        val noteHisGeo = newLayout.findViewById<EditText>(R.id.et_his_geo).text.toString()
                        val notePhilo = newLayout.findViewById<EditText>(R.id.et_philo).text.toString()
                        val noteIsl = newLayout.findViewById<EditText>(R.id.et_s_islamique).text.toString()

                        val noteTamazight : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_tamazight).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_tamazight).text.toString()
                        }else{
                            null
                        }
                        val noteSport : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_sport).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_sport).text.toString()
                        }else{
                            null
                        }
                        if (
                            noteSci.isNotBlank() &&
                            noteMath.isNotBlank() &&
                            notePhy.isNotBlank() &&
                            noteArab.isNotBlank() &&
                            noteFr.isNotBlank() &&
                            noteAng.isNotBlank() &&
                            noteHisGeo.isNotBlank() &&
                            notePhilo.isNotBlank() &&
                            noteIsl.isNotBlank()
                        ){
                            val average : Double = MathPrograms().calculateAverage(
                                noteSci = noteSci.toDouble(),
                                noteMath = noteMath.toDouble(),
                                notePhy = notePhy.toDouble(),
                                noteArab = noteArab.toDouble(),
                                noteFr = noteFr.toDouble(),
                                noteAng = noteAng.toDouble(),
                                noteHisGeo = noteHisGeo.toDouble(),
                                notePhilo = notePhilo.toDouble(),
                                noteIsl = noteIsl.toDouble(),
                                noteTamazight = noteTamazight?.toDouble(),
                                noteSport = noteSport?.toDouble(),
                            )
                            newLayout.findViewById<TextView>(R.id.tv_result_average).text = average.toString()
                        }else{
                            Toast.makeText(this@AverageCalculatorActivity,"Notes non complètes", Toast.LENGTH_SHORT).show()
                        }

                    }

                }
                Constants.GESTION_BRANCH ->{
                    val inflater = LayoutInflater.from(applicationContext)
                    val newLayout = inflater.inflate(R.layout.gestion_calculate_table, includeLayout.root, false)
                    newLayout.id = R.id.layout_gestion_calculate_table
                    newLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
                    includeLayout.root.removeAllViews()
                    currentTable = newLayout
                    includeLayout.root.addView(newLayout)
                    setEditTextsListeners(listOf<EditText>(
                        newLayout.findViewById(R.id.et_comptabilite),
                        newLayout.findViewById(R.id.et_economy),
                        newLayout.findViewById(R.id.et_math),
                        newLayout.findViewById(R.id.et_arabe),
                        newLayout.findViewById(R.id.et_francais),
                        newLayout.findViewById(R.id.et_droit),
                        newLayout.findViewById(R.id.et_anglais),
                        newLayout.findViewById(R.id.et_philo),
                        newLayout.findViewById(R.id.et_his_geo),
                        newLayout.findViewById(R.id.et_tamazight),
                        newLayout.findViewById(R.id.et_s_islamique),
                        newLayout.findViewById(R.id.et_sport)
                    ))
                    btnCalculate.setOnClickListener {
                        val noteCompta = newLayout.findViewById<EditText>(R.id.et_comptabilite).text.toString()
                        val noteEconomie = newLayout.findViewById<EditText>(R.id.et_economy).text.toString()
                        val noteMath = newLayout.findViewById<EditText>(R.id.et_math).text.toString()
                        val noteArab = newLayout.findViewById<EditText>(R.id.et_arabe).text.toString()
                        val noteDroit = newLayout.findViewById<EditText>(R.id.et_droit).text.toString()
                        val noteFr = newLayout.findViewById<EditText>(R.id.et_francais).text.toString()
                        val noteAng = newLayout.findViewById<EditText>(R.id.et_anglais).text.toString()
                        val noteHisGeo = newLayout.findViewById<EditText>(R.id.et_his_geo).text.toString()
                        val notePhilo = newLayout.findViewById<EditText>(R.id.et_philo).text.toString()
                        val noteIsl = newLayout.findViewById<EditText>(R.id.et_s_islamique).text.toString()

                        val noteTamazight : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_tamazight).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_tamazight).text.toString()
                        }else{
                            null
                        }
                        val noteSport : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_sport).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_sport).text.toString()
                        }else{
                            null
                        }
                        if (
                            noteCompta.isNotBlank() &&
                            noteMath.isNotBlank() &&
                            noteEconomie.isNotBlank() &&
                            noteDroit.isNotBlank() &&
                            noteArab.isNotBlank() &&
                            noteFr.isNotBlank() &&
                            noteAng.isNotBlank() &&
                            noteHisGeo.isNotBlank() &&
                            notePhilo.isNotBlank() &&
                            noteIsl.isNotBlank()
                        ){
                            val average : Double = GestionPrograms().calculateAverage(
                                noteCompta = noteCompta.toDouble(),
                                noteMath = noteMath.toDouble(),
                                noteEco = noteEconomie.toDouble(),
                                noteDroit = noteDroit.toDouble(),
                                noteArab = noteArab.toDouble(),
                                noteFr = noteFr.toDouble(),
                                noteAng = noteAng.toDouble(),
                                noteHisGeo = noteHisGeo.toDouble(),
                                notePhilo = notePhilo.toDouble(),
                                noteIsl = noteIsl.toDouble(),
                                noteTamazight = noteTamazight?.toDouble(),
                                noteSport = noteSport?.toDouble(),
                            )
                            newLayout.findViewById<TextView>(R.id.tv_result_average).text = average.toString()
                        }else{
                            Toast.makeText(this@AverageCalculatorActivity,"Notes non complètes", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                Constants.MATH_TECH_BRANCH_MEC ->{
                    val inflater = LayoutInflater.from(applicationContext)
                    val newLayout = inflater.inflate(R.layout.math_tech_calculate_table, includeLayout.root, false)
                    newLayout.id = R.id.layout_math_tech_calculate_table
                    newLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
                    includeLayout.root.removeAllViews()
                    currentTable = newLayout
                    includeLayout.root.addView(newLayout)

                    setEditTextsListeners(listOf<EditText>(
                        newLayout.findViewById(R.id.et_technology),
                        newLayout.findViewById(R.id.et_math),
                        newLayout.findViewById(R.id.et_physique),
                        newLayout.findViewById(R.id.et_arabe),
                        newLayout.findViewById(R.id.et_francais),
                        newLayout.findViewById(R.id.et_anglais),
                        newLayout.findViewById(R.id.et_his_geo),
                        newLayout.findViewById(R.id.et_philo),
                        newLayout.findViewById(R.id.et_s_islamique),
                        newLayout.findViewById(R.id.et_tamazight),
                        newLayout.findViewById(R.id.et_sport)
                    ))
                    btnCalculate.setOnClickListener {
                        val noteSci = newLayout.findViewById<EditText>(R.id.et_technology).text.toString()
                        val noteMath = newLayout.findViewById<EditText>(R.id.et_math).text.toString()
                        val notePhy = newLayout.findViewById<EditText>(R.id.et_physique).text.toString()
                        val noteArab = newLayout.findViewById<EditText>(R.id.et_arabe).text.toString()
                        val noteFr = newLayout.findViewById<EditText>(R.id.et_francais).text.toString()
                        val noteAng = newLayout.findViewById<EditText>(R.id.et_anglais).text.toString()
                        val noteHisGeo = newLayout.findViewById<EditText>(R.id.et_his_geo).text.toString()
                        val notePhilo = newLayout.findViewById<EditText>(R.id.et_philo).text.toString()
                        val noteIsl = newLayout.findViewById<EditText>(R.id.et_s_islamique).text.toString()

                        val noteTamazight : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_tamazight).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_tamazight).text.toString()
                        }else{
                            null
                        }
                        val noteSport : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_sport).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_sport).text.toString()
                        }else{
                            null
                        }

                        if (
                            noteSci.isNotBlank() &&
                            noteMath.isNotBlank() &&
                            notePhy.isNotBlank() &&
                            noteArab.isNotBlank() &&
                            noteFr.isNotBlank() &&
                            noteAng.isNotBlank() &&
                            noteHisGeo.isNotBlank() &&
                            notePhilo.isNotBlank() &&
                            noteIsl.isNotBlank()
                        ){
                            val average : Double = TechMathProgram().calculateAverage(
                                noteTechnology = noteSci.toDouble(),
                                noteMath = noteMath.toDouble(),
                                notePhy = notePhy.toDouble(),
                                noteArab = noteArab.toDouble(),
                                noteFr = noteFr.toDouble(),
                                noteAng = noteAng.toDouble(),
                                noteHisGeo = noteHisGeo.toDouble(),
                                notePhilo = notePhilo.toDouble(),
                                noteIsl = noteIsl.toDouble(),
                                noteTamazight = noteTamazight?.toDouble(),
                                noteSport = noteSport?.toDouble(),
                            )
                            newLayout.findViewById<TextView>(R.id.tv_result_average).text = average.toString()
                        }else{
                            Toast.makeText(this@AverageCalculatorActivity,"Notes non complètes", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                Constants.MATH_TECH_BRANCH_ELE ->{
                    val inflater = LayoutInflater.from(applicationContext)
                    val newLayout = inflater.inflate(R.layout.math_tech_calculate_table, includeLayout.root, false)
                    newLayout.id = R.id.layout_math_tech_calculate_table
                    newLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
                    includeLayout.root.removeAllViews()
                    currentTable = newLayout
                    includeLayout.root.addView(newLayout)

                    setEditTextsListeners(listOf<EditText>(
                        newLayout.findViewById(R.id.et_technology),
                        newLayout.findViewById(R.id.et_math),
                        newLayout.findViewById(R.id.et_physique),
                        newLayout.findViewById(R.id.et_arabe),
                        newLayout.findViewById(R.id.et_francais),
                        newLayout.findViewById(R.id.et_anglais),
                        newLayout.findViewById(R.id.et_his_geo),
                        newLayout.findViewById(R.id.et_philo),
                        newLayout.findViewById(R.id.et_s_islamique),
                        newLayout.findViewById(R.id.et_tamazight),
                        newLayout.findViewById(R.id.et_sport)
                    ))
                    btnCalculate.setOnClickListener {
                        val noteSci = newLayout.findViewById<EditText>(R.id.et_technology).text.toString()
                        val noteMath = newLayout.findViewById<EditText>(R.id.et_math).text.toString()
                        val notePhy = newLayout.findViewById<EditText>(R.id.et_physique).text.toString()
                        val noteArab = newLayout.findViewById<EditText>(R.id.et_arabe).text.toString()
                        val noteFr = newLayout.findViewById<EditText>(R.id.et_francais).text.toString()
                        val noteAng = newLayout.findViewById<EditText>(R.id.et_anglais).text.toString()
                        val noteHisGeo = newLayout.findViewById<EditText>(R.id.et_his_geo).text.toString()
                        val notePhilo = newLayout.findViewById<EditText>(R.id.et_philo).text.toString()
                        val noteIsl = newLayout.findViewById<EditText>(R.id.et_s_islamique).text.toString()

                        val noteTamazight : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_tamazight).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_tamazight).text.toString()
                        }else{
                            null
                        }
                        val noteSport : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_sport).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_sport).text.toString()
                        }else{
                            null
                        }

                        if (
                            noteSci.isNotBlank() &&
                            noteMath.isNotBlank() &&
                            notePhy.isNotBlank() &&
                            noteArab.isNotBlank() &&
                            noteFr.isNotBlank() &&
                            noteAng.isNotBlank() &&
                            noteHisGeo.isNotBlank() &&
                            notePhilo.isNotBlank() &&
                            noteIsl.isNotBlank()
                        ){
                            val average : Double = TechMathProgram().calculateAverage(
                                noteTechnology = noteSci.toDouble(),
                                noteMath = noteMath.toDouble(),
                                notePhy = notePhy.toDouble(),
                                noteArab = noteArab.toDouble(),
                                noteFr = noteFr.toDouble(),
                                noteAng = noteAng.toDouble(),
                                noteHisGeo = noteHisGeo.toDouble(),
                                notePhilo = notePhilo.toDouble(),
                                noteIsl = noteIsl.toDouble(),
                                noteTamazight = noteTamazight?.toDouble(),
                                noteSport = noteSport?.toDouble(),
                            )
                            newLayout.findViewById<TextView>(R.id.tv_result_average).text = average.toString()
                        }else{
                            Toast.makeText(this@AverageCalculatorActivity,"Notes non complètes", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                Constants.MATH_TECH_BRANCH_CIV ->{
                    val inflater = LayoutInflater.from(applicationContext)
                    val newLayout = inflater.inflate(R.layout.math_tech_calculate_table, includeLayout.root, false)
                    newLayout.id = R.id.layout_math_tech_calculate_table
                    newLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
                    includeLayout.root.removeAllViews()
                    currentTable = newLayout
                    includeLayout.root.addView(newLayout)

                    setEditTextsListeners(listOf<EditText>(
                        newLayout.findViewById(R.id.et_technology),
                        newLayout.findViewById(R.id.et_math),
                        newLayout.findViewById(R.id.et_physique),
                        newLayout.findViewById(R.id.et_arabe),
                        newLayout.findViewById(R.id.et_francais),
                        newLayout.findViewById(R.id.et_anglais),
                        newLayout.findViewById(R.id.et_his_geo),
                        newLayout.findViewById(R.id.et_philo),
                        newLayout.findViewById(R.id.et_s_islamique),
                        newLayout.findViewById(R.id.et_tamazight),
                        newLayout.findViewById(R.id.et_sport)
                    ))
                    btnCalculate.setOnClickListener {
                        val noteSci = newLayout.findViewById<EditText>(R.id.et_technology).text.toString()
                        val noteMath = newLayout.findViewById<EditText>(R.id.et_math).text.toString()
                        val notePhy = newLayout.findViewById<EditText>(R.id.et_physique).text.toString()
                        val noteArab = newLayout.findViewById<EditText>(R.id.et_arabe).text.toString()
                        val noteFr = newLayout.findViewById<EditText>(R.id.et_francais).text.toString()
                        val noteAng = newLayout.findViewById<EditText>(R.id.et_anglais).text.toString()
                        val noteHisGeo = newLayout.findViewById<EditText>(R.id.et_his_geo).text.toString()
                        val notePhilo = newLayout.findViewById<EditText>(R.id.et_philo).text.toString()
                        val noteIsl = newLayout.findViewById<EditText>(R.id.et_s_islamique).text.toString()

                        val noteTamazight : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_tamazight).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_tamazight).text.toString()
                        }else{
                            null
                        }
                        val noteSport : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_sport).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_sport).text.toString()
                        }else{
                            null
                        }

                        if (
                            noteSci.isNotBlank() &&
                            noteMath.isNotBlank() &&
                            notePhy.isNotBlank() &&
                            noteArab.isNotBlank() &&
                            noteFr.isNotBlank() &&
                            noteAng.isNotBlank() &&
                            noteHisGeo.isNotBlank() &&
                            notePhilo.isNotBlank() &&
                            noteIsl.isNotBlank()
                        ){
                            val average : Double = TechMathProgram().calculateAverage(
                                noteTechnology = noteSci.toDouble(),
                                noteMath = noteMath.toDouble(),
                                notePhy = notePhy.toDouble(),
                                noteArab = noteArab.toDouble(),
                                noteFr = noteFr.toDouble(),
                                noteAng = noteAng.toDouble(),
                                noteHisGeo = noteHisGeo.toDouble(),
                                notePhilo = notePhilo.toDouble(),
                                noteIsl = noteIsl.toDouble(),
                                noteTamazight = noteTamazight?.toDouble(),
                                noteSport = noteSport?.toDouble(),
                            )
                            newLayout.findViewById<TextView>(R.id.tv_result_average).text = average.toString()
                        }else{
                            Toast.makeText(this@AverageCalculatorActivity,"Notes non complètes", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                Constants.MATH_TECH_BRANCH_METH ->{
                    val inflater = LayoutInflater.from(applicationContext)
                    val newLayout = inflater.inflate(R.layout.math_tech_calculate_table, includeLayout.root, false)
                    newLayout.id = R.id.layout_math_tech_calculate_table
                    newLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
                    includeLayout.root.removeAllViews()
                    currentTable = newLayout
                    includeLayout.root.addView(newLayout)

                    setEditTextsListeners(listOf<EditText>(
                        newLayout.findViewById(R.id.et_technology),
                        newLayout.findViewById(R.id.et_math),
                        newLayout.findViewById(R.id.et_physique),
                        newLayout.findViewById(R.id.et_arabe),
                        newLayout.findViewById(R.id.et_francais),
                        newLayout.findViewById(R.id.et_anglais),
                        newLayout.findViewById(R.id.et_his_geo),
                        newLayout.findViewById(R.id.et_philo),
                        newLayout.findViewById(R.id.et_s_islamique),
                        newLayout.findViewById(R.id.et_tamazight),
                        newLayout.findViewById(R.id.et_sport)
                    ))
                    btnCalculate.setOnClickListener {
                        val noteSci = newLayout.findViewById<EditText>(R.id.et_technology).text.toString()
                        val noteMath = newLayout.findViewById<EditText>(R.id.et_math).text.toString()
                        val notePhy = newLayout.findViewById<EditText>(R.id.et_physique).text.toString()
                        val noteArab = newLayout.findViewById<EditText>(R.id.et_arabe).text.toString()
                        val noteFr = newLayout.findViewById<EditText>(R.id.et_francais).text.toString()
                        val noteAng = newLayout.findViewById<EditText>(R.id.et_anglais).text.toString()
                        val noteHisGeo = newLayout.findViewById<EditText>(R.id.et_his_geo).text.toString()
                        val notePhilo = newLayout.findViewById<EditText>(R.id.et_philo).text.toString()
                        val noteIsl = newLayout.findViewById<EditText>(R.id.et_s_islamique).text.toString()

                        val noteTamazight : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_tamazight).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_tamazight).text.toString()
                        }else{
                            null
                        }
                        val noteSport : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_sport).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_sport).text.toString()
                        }else{
                            null
                        }

                        if (
                            noteSci.isNotBlank() &&
                            noteMath.isNotBlank() &&
                            notePhy.isNotBlank() &&
                            noteArab.isNotBlank() &&
                            noteFr.isNotBlank() &&
                            noteAng.isNotBlank() &&
                            noteHisGeo.isNotBlank() &&
                            notePhilo.isNotBlank() &&
                            noteIsl.isNotBlank()
                        ){
                            val average : Double = TechMathProgram().calculateAverage(
                                noteTechnology = noteSci.toDouble(),
                                noteMath = noteMath.toDouble(),
                                notePhy = notePhy.toDouble(),
                                noteArab = noteArab.toDouble(),
                                noteFr = noteFr.toDouble(),
                                noteAng = noteAng.toDouble(),
                                noteHisGeo = noteHisGeo.toDouble(),
                                notePhilo = notePhilo.toDouble(),
                                noteIsl = noteIsl.toDouble(),
                                noteTamazight = noteTamazight?.toDouble(),
                                noteSport = noteSport?.toDouble(),
                            )
                            newLayout.findViewById<TextView>(R.id.tv_result_average).text = average.toString()
                        }else{
                            Toast.makeText(this@AverageCalculatorActivity,"Notes non complètes", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                Constants.LANGUES_BRANCH ->{
                    val inflater = LayoutInflater.from(applicationContext)
                    val newLayout = inflater.inflate(R.layout.lang_calculate_table, includeLayout.root, false)
                    newLayout.id = R.id.layout_lang_calculate_table
                    newLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
                    includeLayout.root.removeAllViews()
                    currentTable = newLayout
                    includeLayout.root.addView(newLayout)

                    setEditTextsListeners(listOf<EditText>(
                        newLayout.findViewById(R.id.et_third_language),
                        newLayout.findViewById(R.id.et_arabe),
                        newLayout.findViewById(R.id.et_francais),
                        newLayout.findViewById(R.id.et_anglais),
                        newLayout.findViewById(R.id.et_his_geo),
                        newLayout.findViewById(R.id.et_philo),
                        newLayout.findViewById(R.id.et_s_islamique),
                        newLayout.findViewById(R.id.et_tamazight),
                        newLayout.findViewById(R.id.et_sport)
                    ))
                    btnCalculate.setOnClickListener {
                        val noteThirdLang = newLayout.findViewById<EditText>(R.id.et_third_language).text.toString()
                        val noteArab = newLayout.findViewById<EditText>(R.id.et_arabe).text.toString()
                        val noteFr = newLayout.findViewById<EditText>(R.id.et_francais).text.toString()
                        val noteAng = newLayout.findViewById<EditText>(R.id.et_anglais).text.toString()
                        val noteHisGeo = newLayout.findViewById<EditText>(R.id.et_his_geo).text.toString()
                        val notePhilo = newLayout.findViewById<EditText>(R.id.et_philo).text.toString()
                        val noteIsl = newLayout.findViewById<EditText>(R.id.et_s_islamique).text.toString()


                        val noteTamazight : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_tamazight).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_tamazight).text.toString()
                        }else{
                            null
                        }
                        val noteSport : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_sport).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_sport).text.toString()
                        }else{
                            null
                        }

                        if (
                            noteThirdLang.isNotBlank() &&
                            noteArab.isNotBlank() &&
                            noteFr.isNotBlank() &&
                            noteAng.isNotBlank() &&
                            noteHisGeo.isNotBlank() &&
                            notePhilo.isNotBlank() &&
                            noteIsl.isNotBlank()
                        ){
                            val average : Double = LangProgram().calculateAverage(
                                noteThirdLang = noteThirdLang.toDouble(),
                                noteArab = noteArab.toDouble(),
                                noteFr = noteFr.toDouble(),
                                noteAng = noteAng.toDouble(),
                                noteHisGeo = noteHisGeo.toDouble(),
                                notePhilo = notePhilo.toDouble(),
                                noteIsl = noteIsl.toDouble(),
                                noteTamazight = noteTamazight?.toDouble(),
                                noteSport = noteSport?.toDouble(),
                            )
                            newLayout.findViewById<TextView>(R.id.tv_result_average).text = average.toString()
                        }else{
                            Toast.makeText(this@AverageCalculatorActivity,"Notes non complètes", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                Constants.LETTRE_BRANCH ->{
                    val inflater = LayoutInflater.from(applicationContext)
                    val newLayout = inflater.inflate(R.layout.lettres_calculate_table, includeLayout.root, false)
                    newLayout.id = R.id.layout_lettres_calculate_table
                    newLayout.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
                    includeLayout.root.removeAllViews()
                    currentTable = newLayout
                    includeLayout.root.addView(newLayout)

                    setEditTextsListeners(listOf<EditText>(
                        newLayout.findViewById(R.id.et_arabe),
                        newLayout.findViewById(R.id.et_francais),
                        newLayout.findViewById(R.id.et_math),
                        newLayout.findViewById(R.id.et_anglais),
                        newLayout.findViewById(R.id.et_his_geo),
                        newLayout.findViewById(R.id.et_philo),
                        newLayout.findViewById(R.id.et_s_islamique),
                        newLayout.findViewById(R.id.et_tamazight),
                        newLayout.findViewById(R.id.et_sport)
                    ))
                    btnCalculate.setOnClickListener {
                        val noteMath = newLayout.findViewById<EditText>(R.id.et_math).text.toString()
                        val noteArab = newLayout.findViewById<EditText>(R.id.et_arabe).text.toString()
                        val noteFr = newLayout.findViewById<EditText>(R.id.et_francais).text.toString()
                        val noteAng = newLayout.findViewById<EditText>(R.id.et_anglais).text.toString()
                        val noteHisGeo = newLayout.findViewById<EditText>(R.id.et_his_geo).text.toString()
                        val notePhilo = newLayout.findViewById<EditText>(R.id.et_philo).text.toString()
                        val noteIsl = newLayout.findViewById<EditText>(R.id.et_s_islamique).text.toString()


                        val noteTamazight : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_tamazight).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_tamazight).text.toString()
                        }else{
                            null
                        }
                        val noteSport : String? = if (newLayout.findViewById<CheckBox>(R.id.cb_sport).isChecked){
                            newLayout.findViewById<EditText>(R.id.et_sport).text.toString()
                        }else{
                            null
                        }

                        if (
                            noteMath.isNotBlank() &&
                            noteArab.isNotBlank() &&
                            noteFr.isNotBlank() &&
                            noteAng.isNotBlank() &&
                            noteHisGeo.isNotBlank() &&
                            notePhilo.isNotBlank() &&
                            noteIsl.isNotBlank()
                        ){
                            val average : Double = PhiloProgram().calculateAverage(
                                noteMath = noteMath.toDouble(),
                                noteArab = noteArab.toDouble(),
                                noteFr = noteFr.toDouble(),
                                noteAng = noteAng.toDouble(),
                                noteHisGeo = noteHisGeo.toDouble(),
                                notePhilo = notePhilo.toDouble(),
                                noteIsl = noteIsl.toDouble(),
                                noteTamazight = noteTamazight?.toDouble(),
                                noteSport = noteSport?.toDouble(),
                            )
                            newLayout.findViewById<TextView>(R.id.tv_result_average).text = average.toString()
                        }else{
                            Toast.makeText(this@AverageCalculatorActivity,"Notes non complètes", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            }

        }
    }

    private fun setEditTextsListeners(editTextsList: List<EditText>) {
        for (editText in editTextsList){
            editText.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(changedText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!changedText.isNullOrBlank()){
                        try {
                            if (changedText.toString().toDouble() > 20.0) {
                                editText
                                    .setText(getString(R.string._20_0))
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@AverageCalculatorActivity,
                                "Your input is invalid",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                override fun afterTextChanged(p0: Editable?) {
                }

            })
        }
    }
}