package com.tp.bacprep.presentation.activities.admin


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.RequestsRvAdapter
import com.tp.bacprep.databinding.ActivityReceivedRequestsBinding
import com.tp.bacprep.domain.models.Request
import com.tp.bacprep.presentation.viewmodels.RequestsViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.DocumentSnapshotConverter
import com.tp.bacprep.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class ReceivedRequestsActivity : BaseActivity(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener, RequestsRvAdapter.AnswerRequestClickListener,
    RequestsRvAdapter.LoadMoreRequests {
    private var binding: ActivityReceivedRequestsBinding? = null
    private val requestViewModel: RequestsViewModel by viewModels()
    private lateinit var adapter: RequestsRvAdapter
    private val calendar = Calendar.getInstance()
    private lateinit var startDateTimestamp: Timestamp
    private lateinit var endDateTimestamp: Timestamp
    private var isFilterVisible = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceivedRequestsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarAnswerRequests)
        requestViewModel.queryRequest(null, null)

        setViewModelDataOnObserve()
        onBackPressedDispatcher.addCallback(
            this /* lifecycle owner */,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    this@ReceivedRequestsActivity.finish()
                }
            })

        setUiElementsFunctionality()
        setStatusBarColor(
            ContextCompat.getColor(
                this@ReceivedRequestsActivity,
                R.color.light_gray_to_deep_charcoal
            ),
            !SharedPref(this@ReceivedRequestsActivity).getThemeMode()
        )
        setRequestsRecyclerView(listOf())

    }

    private fun setUiElementsFunctionality() {
        binding?.apply {
            toolbarAnswerRequests.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            etBeginDate.setOnClickListener {
                showDatePickerDialog(etBeginDate)
                true
            }
            etEndDate.setOnClickListener {
                showDatePickerDialog(etEndDate)
                true
            }
            btnApplyFilters.setOnClickListener {
                applyDateQuery()
            }
        }
    }

    private fun setViewModelDataOnObserve() {
        requestViewModel.allRequestsList.observe(this@ReceivedRequestsActivity) { requestList ->
            if (requestList != null) {
                setRequestsRecyclerView(requestList)
            }
        }
        requestViewModel.requestsDateQueryResults.observe(this@ReceivedRequestsActivity) { dateQueryResults ->
            if (!dateQueryResults.isNullOrEmpty()) {
                requestViewModel.setLastRequestVisible(dateQueryResults.last())
                val dateQueryRequestsList =
                    DocumentSnapshotConverter().mapDocSnapshotsToRequestList(dateQueryResults)
                        ?: listOf()
                if (::adapter.isInitialized) {
                    val extendedRequestList =
                        adapter.getPreviousPostsList().toMutableList()
                    extendedRequestList.addAll(dateQueryRequestsList)
                    requestViewModel.setAllRequestsRetrievedCurrently(extendedRequestList)
                    setRequestsRecyclerView(extendedRequestList)
                } else {
                    requestViewModel.setAllRequestsRetrievedCurrently(dateQueryRequestsList)
                    setRequestsRecyclerView(dateQueryRequestsList)
                }

            } else {
                if (::adapter.isInitialized) {
                    adapter.hideLoadMoreButton()
                }
            }
        }
    }

    private fun showDatePickerDialog(dateEditText: EditText) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->

            },
            year,
            month,
            day
        )
        datePickerDialog.setOnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val selectedDate = calendar.time
            when (dateEditText) {
                binding?.etBeginDate -> {
                    startDateTimestamp =
                        Timestamp(selectedDate) // Convert the selected date to a timestamp
                }

                binding?.etEndDate -> {
                    endDateTimestamp =
                        Timestamp(selectedDate) // Convert the selected date to a timestamp
                }
            }
            val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            val formattedDate = dateFormatter.format(selectedDate)
            dateEditText.setText(formattedDate) // Display the date as needed
            datePickerDialog.dismiss()
        }
        datePickerDialog.show()
    }

    private fun setRequestsRecyclerView(searchResultList: List<Request>) {
        adapter =
            RequestsRvAdapter(this@ReceivedRequestsActivity, searchResultList, Constants.ADMIN)
        binding?.rvRequestsList?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.answerRequestClickListener = this
        adapter.onLoadMoreRequests = this
        binding?.rvRequestsList?.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.requests_toolbar_menu, menu)
        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        val searchIcon = searchView?.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        Toast.makeText(this@ReceivedRequestsActivity, "$searchIcon", Toast.LENGTH_SHORT).show()

// Assuming R.color.your_tint_color is the ID of the color you want to use as the tint
        val tint = ContextCompat.getColor(this, R.color.blackish_constant)

// Apply the tint to the search icon
        searchIcon?.setColorFilter(tint, PorterDuff.Mode.SRC_IN)
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this@ReceivedRequestsActivity)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_requests_filter -> {

                isFilterVisible = if (isFilterVisible) {
                    // Hide the filter view
                    collapse(binding!!.clDateFilter)
                    false
                } else {
                    // Show the filter view
                    expand(binding!!.clDateFilter)
                    true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun expand(view: View) {
        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = view.measuredHeight

        view.layoutParams.height = 0
        view.visibility = View.VISIBLE

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // Speed up the animation
        animation.duration = (targetHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }

    private fun collapse(view: View) {
        val initialHeight = view.measuredHeight
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    view.visibility = View.GONE
                } else {
                    view.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // Speed up the animation
        animation.duration = 1000L
        view.startAnimation(animation)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchRequest(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchRequest(query)
        }
        return true
    }

    private fun searchRequest(query: String) {
        if (requestViewModel.allRequestRetrievedCurrently.value != null && requestViewModel.allRequestRetrievedCurrently.isInitialized) {
            val searchResultList = mutableListOf<Request>()
            for (request in requestViewModel.allRequestRetrievedCurrently.value!!) {
                if (request.requestText.contains(query, true)) {
                    searchResultList.add(request)
                }
            }
            searchResultList.sortByDescending { request ->
                request.timestamp
            }
            setRequestsRecyclerView(searchResultList)
        }
    }

    private fun applyDateQuery() {
        if (::startDateTimestamp.isInitialized && ::endDateTimestamp.isInitialized) {
            requestViewModel.setLastRequestVisible(null)
            adapter.resetContent()
            requestViewModel.queryRequest(startDateTimestamp, endDateTimestamp)
        } else {
            showErrorSnackBar(
                getString(R.string.choississez_une_date_de_d_but_et_unde_date_de_fin),
                true
            )
        }
    }

    override fun onAnswerButtonClicked(request: Request) {
        val intent = Intent(this@ReceivedRequestsActivity, AdminMainActivity::class.java)
        intent.apply {
            putExtra(Constants.RESPONDING_TO_REQUEST, true)
            putExtra(Constants.REQUEST_TO_ANSWER_TO, request)
        }
        startActivity(intent)
        finish()
    }

    override fun onLoadMoreRequestsClicked() {
        if (::startDateTimestamp.isInitialized && ::endDateTimestamp.isInitialized) {
            requestViewModel.queryRequest(startDateTimestamp, endDateTimestamp)
        } else {
            requestViewModel.queryRequest(null, null)
        }
    }

    override fun onClose(): Boolean {
        setRequestsRecyclerView(requestViewModel.allRequestRetrievedCurrently.value!!)
        return true
    }
}