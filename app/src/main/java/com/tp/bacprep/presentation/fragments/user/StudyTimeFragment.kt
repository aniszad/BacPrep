package com.tp.bacprep.presentation.fragments.user

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.tp.bacprep.R
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.MonthsSelectorRvAdapter
import com.tp.bacprep.databinding.FragmentTimeBinding
import com.tp.bacprep.domain.models.StudyTime
import com.tp.bacprep.presentation.activities.user.PomodoroActivity
import com.tp.bacprep.presentation.viewmodels.StudyTimeViewModel
import com.tp.bacprep.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class StudyTimeFragment : Fragment(), MonthsSelectorRvAdapter.MonthSelectedListener {
    private lateinit var binding: FragmentTimeBinding
    private lateinit var monthsAdapter: MonthsSelectorRvAdapter
    private val studyTimeVm: StudyTimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTimeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTimerSpinner()
        setMonthsAdapter()
        binding.apply {
            btnStartTimer.setOnClickListener {
                val intent = Intent(requireActivity(), PomodoroActivity::class.java)
                val timerStudyDuration =
                    spinnerStudyTime.selectedItem.toString().removeSuffix(Constants.MIN_SUFFIX)
                        .trim().toLong()
                val timerRestDuration =
                    spinnerRestTime.selectedItem.toString().removeSuffix(Constants.MIN_SUFFIX)
                        .trim().toLong()
                intent.putExtra(Constants.TIMER_STUDY_DURATION, timerStudyDuration)
                intent.putExtra(Constants.TIMER_REST_DURATION, timerRestDuration)
                startActivity(intent)
            }

        }
        val month = getCurrentMonth()
        val year = getCurrentYear()
        studyTimeVm.getGetMonthTimeData(month, year) { studyTimeList ->
            setLastWeekChartView(studyTimeList)
            setAllTimeChartView(studyTimeList)
        }

    }

    private fun setMonthsAdapter() {
        requireActivity().runOnUiThread {
            binding.rvMonthsSelector.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            monthsAdapter = MonthsSelectorRvAdapter(getMonths(), requireContext())
            monthsAdapter.setMonthListener(this@StudyTimeFragment)
            monthsAdapter.setCurrentMonth(getCurrentMonth())
            binding.rvMonthsSelector.adapter = monthsAdapter
            (binding.rvMonthsSelector.layoutManager as LinearLayoutManager).smoothScrollToPosition(
                binding.rvMonthsSelector,
                null,
                getCurrentMonth() + 1
            )
        }
    }

    private fun setTimerSpinner() {
        binding.apply {
            val studyDurations = listOf(
                "15 min",
                "30 min",
                "45 min",
                "60 min",
                "90 min",
            )
            val restDurations = listOf(
                "5 min",
                "10 min",
                "15 min",
                "20 min",
                "25 min"
            )
            val studyDurationAdapter = ArrayAdapter(
                requireContext(),
                R.layout.pomodoro_spinner_item_layout,
                studyDurations
            )
            val restDurationAdapter = ArrayAdapter(
                requireContext(),
               R.layout.pomodoro_spinner_item_layout,
                restDurations
            )
            spinnerStudyTime.adapter = studyDurationAdapter
            spinnerRestTime.adapter = restDurationAdapter
        }
    }

    private fun getMonths(): ArrayList<String> {
        return arrayListOf(
            getString(R.string.janvier),
            getString(R.string.fevrier),
            getString(R.string.mars),
            getString(R.string.avril),
            getString(R.string.may),
            getString(R.string.juin),
            getString(R.string.juillet),
            getString(R.string.ao_t),
            getString(R.string.septembre),
            getString(R.string.octobre),
            getString(R.string.novembre),
            getString(R.string.decembre)
        )
    }


    override fun onResume() {
        super.onResume()
        val month = getCurrentMonth()
        val year = getCurrentYear()
        studyTimeVm.getGetMonthTimeData(month, year) { studyTimeList ->
            setLastWeekChartView(studyTimeList)
            setAllTimeChartView(studyTimeList)
        }
    }

    private fun setAllTimeChartView(studyTimeList: List<StudyTime>?) {
        //val dataEntries = prepareChartData(studyTimeList)
        val dataEntries = mutableListOf<Entry>()
        dataEntries.add(Entry(1f, 3f))    // Day 1
        dataEntries.add(Entry(2f, 5f))    // Day 2
        dataEntries.add(Entry(3f, 4f))    // Day 3
        dataEntries.add(Entry(4f, 6f))    // Day 4
        dataEntries.add(Entry(5f, 7f))    // Day 5
        dataEntries.add(Entry(6f, 2f))    // Day 6
        dataEntries.add(Entry(7f, 3.5f))  // Day 7
        dataEntries.add(Entry(8f, 4.2f))  // Day 8
        dataEntries.add(Entry(9f, 5.8f))  // Day 9
        dataEntries.add(Entry(10f, 3.7f)) // Day 10
        dataEntries.add(Entry(11f, 6.1f)) // Day 11
        dataEntries.add(Entry(12f, 4.5f)) // Day 12
        dataEntries.add(Entry(13f, 7.2f)) // Day 13
        dataEntries.add(Entry(14f, 5.3f)) // Day 14
        dataEntries.add(Entry(15f, 3.9f)) // Day 15
        dataEntries.add(Entry(16f, 2.8f)) // Day 16
        dataEntries.add(Entry(17f, 4.6f)) // Day 17
        dataEntries.add(Entry(18f, 6.5f)) // Day 18
        dataEntries.add(Entry(19f, 3.2f)) // Day 19
        dataEntries.add(Entry(20f, 5.1f)) // Day 20
        dataEntries.add(Entry(21f, 4.7f)) // Day 21
        dataEntries.add(Entry(22f, 7.4f)) // Day 22
        dataEntries.add(Entry(23f, 6.0f)) // Day 23
        dataEntries.add(Entry(24f, 3.6f)) // Day 24
        dataEntries.add(Entry(25f, 2.3f)) // Day 25
        dataEntries.add(Entry(26f, 4.9f)) // Day 26
        dataEntries.add(Entry(27f, 5.7f)) // Day 27
        dataEntries.add(Entry(28f, 3.4f)) // Day 28
        dataEntries.add(Entry(29f, 4.0f)) // Day 29
        dataEntries.add(Entry(30f, 6.2f)) // Day 30
        dataEntries.add(Entry(31f, 2f)) // Day 30
        val dataSet = LineDataSet(dataEntries, getString(R.string.temps_d_tudes_de_mois_en_heures))
        dataSet.setDrawFilled(true)
        dataSet.fillDrawable = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.green_transparent))
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawCircles(false)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.flair_green)
        dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
        // show the value on top of the bar if its not equal to 0.0

        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // Show the value on top of the bar if it's not equal to 0.0, otherwise show an empty string
                return if (value != 0.0f) {
                    value.toString()
                } else {
                    ""
                }
            }
        }

        val lineData = LineData(dataSet)
        binding.cvTimeStudiedChartOverall.apply {
            data = lineData
            description = null
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(true)
            xAxis.setDrawAxisLine(true)
            axisLeft.setDrawGridLines(false)
            axisLeft.setDrawAxisLine(true)
            axisLeft.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            axisLeft.axisMaximum = 12.0f
            axisRight.axisMaximum = 12.0f
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            invalidate()

        }

    }

    private fun setLastWeekChartView(studyTimeList: List<StudyTime>?) {
        //val dataEntries = prepareLastWeekChartData(studyTimeList)
        val dataEntries = mutableListOf<Entry>()
        dataEntries.add(Entry(1f, 3f))  // Day 1
        dataEntries.add(Entry(2f, 5f))  // Day 2
        dataEntries.add(Entry(3f, 4f))  // Day 3
        dataEntries.add(Entry(4f, 6f))  // Day 4
        dataEntries.add(Entry(5f, 7f))  // Day 5
        dataEntries.add(Entry(6f, 2f))  // Day 6
        dataEntries.add(Entry(7f, 3.5f))
        val dataSet = LineDataSet(dataEntries, getString(R.string.temps_d_tudes_de_la_semaine_en_heures))
        dataSet.setDrawFilled(true)
        dataSet.fillDrawable = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.blue_transparent))
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawCircles(false)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.sea_blue)
        dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
        // show the value on top of the bar if its not equal to 0.0
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                // Show the value on top of the bar if it's not equal to 0.0, otherwise show an empty string
                return if (value != 0.0f) {
                    value.toString()
                } else {
                    ""
                }
            }
        }
        val lineData = LineData(dataSet)
        binding.cvTimeStudiedChartLastWeek.apply {
            data = lineData
            description = null
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(true)
            xAxis.setDrawAxisLine(true)
            xAxis.axisMaximum = 7f
            axisLeft.isEnabled= false
            axisLeft.setDrawLabels(false)
            axisRight.setDrawLabels(false)
            axisLeft.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            axisLeft.axisMaximum = 12.0f
            axisRight.axisMaximum = 12.0f
            axisRight.isEnabled = false
            xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            invalidate()
        }

    }

    private fun prepareChartData(studyTimeList: List<StudyTime>?): List<BarEntry> {
        val dataEntries = mutableListOf<BarEntry>()

        if (studyTimeList == null) {
            for (i in 0 until 32) {
                dataEntries.add(BarEntry(i.toFloat() + 1, 0f))
            }
        } else {
            // Initialize an array to hold study durations for each day
            val studyDurations = DoubleArray(31) { 0.0 }
            studyTimeList.forEach { studyTime ->
                // Assuming day, month, and year are 1-based
                val dayIndex = studyTime.day - 1
                studyDurations[dayIndex] += (studyTime.duration / (1000 * 60)).toDouble()
            }
            for (i in studyDurations.indices) {
                dataEntries.add(
                    BarEntry(
                        i.toFloat() + 1,
                        String.format("%.1f", (studyDurations[i].toFloat() / 60)).toFloat()
                    )
                )
            }
        }
        return dataEntries
    }


    private fun prepareLastWeekChartData(studyTimeList: List<StudyTime>?): List<BarEntry> {
        val dataEntries = mutableListOf<BarEntry>()

        // Calculate the start and end date for the last week
        val endDate = Calendar.getInstance().time
        val calendar = Calendar.getInstance()
        calendar.time = endDate
        calendar.add(Calendar.DAY_OF_YEAR, -6) // Subtract 6 days to get the start date
        val startDate = calendar.time

        if (studyTimeList == null) {
            for (i in 0 until 7) {
                dataEntries.add(BarEntry(i.toFloat() + 1, 0f))
            }
        } else {
            // Initialize an array to hold study durations for each day of the last week
            val studyDurations = DoubleArray(7) { 0.0 }
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            studyTimeList.forEach { studyTime ->
                val studyDate = getDateFromFields(studyTime.year, studyTime.month, studyTime.day)
                if (studyDate in startDate..endDate) {
                    val dayIndex =
                        (studyDate.time - startDate.time) / (24 * 60 * 60 * 1000).toFloat()
                    studyDurations[dayIndex.toInt()] += (studyTime.duration / (1000 * 60)).toDouble()
                }
            }

            for (i in studyDurations.indices) {
                dataEntries.add(
                    BarEntry(
                        i.toFloat() + 1,
                        String.format("%.1f", (studyDurations[i].toFloat() / 60.0f)).toFloat()
                    )
                )
            }
        }

        return dataEntries
    }

    private fun getDateFromFields(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day) // Month is 0-based, so subtract 1
        return calendar.time
    }

    override fun onMonthSelected(month: String) {
        val monthNum = getMonthNum(month)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        studyTimeVm.getGetMonthTimeData(monthNum, year) { studyTimeList ->
            setAllTimeChartView(studyTimeList)
        }
    }

    private fun getCurrentMonth(): Int {
        val currentTimeMillis = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis
        return calendar.get(Calendar.MONTH) + 1
    }

    private fun getCurrentYear(): Int {
        val currentTimeMillis = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis
        return calendar.get(Calendar.YEAR)
    }

    private fun getMonthNum(month: String): Int {
        return when (month) {
            getString(R.string.janvier) -> {
                1
            }

            getString(R.string.fevrier) -> {
                2
            }

            getString(R.string.mars) -> {
                3
            }

            getString(R.string.avril) -> {
                4
            }

            getString(R.string.may) -> {
                5
            }

            getString(R.string.juin)-> {
                6
            }

            getString(R.string.juillet) -> {
                7
            }

            getString(R.string.ao_t) -> {
                8
            }

            getString(R.string.septembre) -> {
                9
            }

            getString(R.string.octobre) -> {
                10
            }

            getString(R.string.novembre) -> {
                11
            }

            getString(R.string.decembre) -> {
                12
            }

            else -> {
                0
            }
        }
    }

}