package com.tp.bacprep.presentation.activities.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.tp.bacprep.R
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Timestamp
import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.FeedRvAdapter
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.RequestsUsersRvAdapter
import com.tp.bacprep.databinding.ActivitySendRequestsBinding
import com.tp.bacprep.databinding.MyRequestsAnswersBottomSheetLayoutBinding
import com.tp.bacprep.domain.models.Bookmark
import com.tp.bacprep.domain.models.Post
import com.tp.bacprep.domain.models.Request
import com.tp.bacprep.presentation.viewmodels.RequestsViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.DocumentSnapshotConverter
import com.tp.bacprep.util.DownloadService
import com.tp.bacprep.util.FormattedTimestampDate
import com.tp.bacprep.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendRequestsActivity : BaseActivity(), RequestsUsersRvAdapter.DeleteRequestClickListener,
    RequestsUsersRvAdapter.OnCheckAnswersClicked,
    FeedRvAdapter.RetrySearchButtonClickListener, FeedRvAdapter.AttachmentItemClickListener,
    FeedRvAdapter.AddToBookmarksClickListener, FeedRvAdapter.LoadMorePostsClickListener,
    FeedRvAdapter.BookmarksListener {
    private var binding : ActivitySendRequestsBinding? = null
    private lateinit var adapter : RequestsUsersRvAdapter
    private val requestViewModel : RequestsViewModel by viewModels()
    private lateinit var mostRecentRequestTime : Timestamp
    private lateinit var answersAdapter : FeedRvAdapter
    private lateinit var bottomSheetBinding : MyRequestsAnswersBottomSheetLayoutBinding
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts
            .RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this@SendRequestsActivity, "permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@SendRequestsActivity, "permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendRequestsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUiFunctionalities()

        setViewModels()

        setStatusBarColor(ContextCompat.getColor(this@SendRequestsActivity, R.color.light_gray_to_deep_charcoal), !SharedPref(this@SendRequestsActivity).getThemeMode())

        onBackPressedDispatcher.addCallback(this@SendRequestsActivity, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this@SendRequestsActivity.finish()
            }
        })

    }

    private fun setViewModels() {
        requestViewModel.getMyRequests(this@SendRequestsActivity)
        requestViewModel.myRequestsList.observe(this@SendRequestsActivity){ myRequestList ->
            if (!myRequestList.isNullOrEmpty()) {
                myRequestList.sortedByDescending { it.timestamp }
                mostRecentRequestTime = myRequestList.maxBy { it.timestamp }.timestamp
                setRequestsRecyclerView(myRequestList)
            }
        }
        requestViewModel.deleteSuccessful.observe(this@SendRequestsActivity){ deleteSuccess ->
            if (deleteSuccess){
                hideWarningDialog()
                showErrorSnackBar(getString(R.string.requete_supprim_e), true)
            }else{
                hideWarningDialog()
                showErrorSnackBar(getString(R.string.requete_non_supprim_e), true)
            }

        }
        requestViewModel.addRequestStatus.observe(this@SendRequestsActivity){ result ->
            if (result.first){
                binding?.etRequestText?.text?.clear()
                if (::adapter.isInitialized){
                    adapter.itemAdded(result.second)
                }else{
                    setRequestsRecyclerView(listOf(result.second))
                }
            }else{
                showErrorSnackBar(getString(R.string.erreur), true)
            }
            hideProgressDialog()
        }
    }

    private fun setUiFunctionalities() {
        binding?.apply {
            toolbarSendRequests.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            etRequestText.addTextChangedListener { et ->
                if (!et.isNullOrBlank() && et.isNotEmpty()){
                    if (et.length >= 10){
                        btnSendRequest.visibility = View.VISIBLE
                    }else{
                        btnSendRequest.visibility = View.GONE
                    }
                }
            }
            btnSendRequest.setOnClickListener {
                sendRequest()
            }
            btnSeeAnswers.setOnClickListener{
                showAnswersBottomSheet(SharedPref(this@SendRequestsActivity).getUserId()!!)
            }
        }
    }

    private fun setRequestsRecyclerView(myRequestList: List<Request>) {
        adapter = RequestsUsersRvAdapter(this@SendRequestsActivity, myRequestList, Constants.USER)
        adapter.deleteRequestClickListener = this@SendRequestsActivity
        adapter.onCheckAnswersClicked = this@SendRequestsActivity
        binding?.rvMyRequestsList?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.rvMyRequestsList?.adapter = adapter
    }

    private fun sendRequest() {
        if (::mostRecentRequestTime.isInitialized){
            if (isTimestampInPast24Hours(mostRecentRequestTime)){
                showErrorSnackBar(getString(R.string.vous_avez_d_ja_en_requete_aujourd_hui), true)
            }else{
                showProgressDialog()
                val requestText = binding?.etRequestText?.text.toString()
                val userId = FbAuth(this@SendRequestsActivity).getUserId()
                requestViewModel.getName{ name ->
                    requestViewModel.addRequest(Request("", userId, name ,requestText, false, FormattedTimestampDate().todayTsDate))
                }
            }
        }else{
            showProgressDialog()
            val requestText = binding?.etRequestText?.text.toString()
            val userId = FbAuth(this@SendRequestsActivity).getUserId()
            requestViewModel.getName{ name ->
                requestViewModel.addRequest(Request("", userId, name ,requestText, false, FormattedTimestampDate().todayTsDate))
            }
        }
    }

    private fun isTimestampInPast24Hours(timestamp: Timestamp): Boolean {
        // Convert the Firebase Timestamp to Unix timestamp (milliseconds since epoch)
        val timestampMillis = timestamp.toDate().time

        // Calculate the current time in milliseconds
        val currentTimeMillis = System.currentTimeMillis()

        // Calculate the time 24 hours ago (in milliseconds)
        val twentyFourHoursAgoMillis = currentTimeMillis - (24 * 60 * 60 * 1000)

        // Compare the converted timestamp with the time 24 hours ago
        return timestampMillis in twentyFourHoursAgoMillis..currentTimeMillis
    }

    override fun onDeleteButtonClicked(request: Request, position : Int) {
        showWarningDialog( getString(R.string.delete_request_warning_text))
        {
            requestViewModel.deleteRequest(request.requestId)
            adapter.notifyRequestRemoved(position)
        }
    }

    override fun onCheckAnswersClicked(requestOwnerId: String) {
        showAnswersBottomSheet(requestOwnerId)
    }

    private fun showAnswersBottomSheet(requestOwnerId : String) {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetBinding = MyRequestsAnswersBottomSheetLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        setRequestAnswersDataOnObserve()
        requestViewModel.getNextRequestAnswersBatch(requestOwnerId)
        bottomSheetDialog.show()
    }

    // request answers code
    private fun setRequestAnswersDataOnObserve() {
        requestViewModel.requestAnswersBath.observe(this@SendRequestsActivity){ requestAnswersDocSnapshotsList ->
            if (!requestAnswersDocSnapshotsList.isNullOrEmpty()) {
                requestViewModel.setLastPostVisible(requestAnswersDocSnapshotsList.last())
                val requestAnswersList = DocumentSnapshotConverter().mapDocSnapshotsToPostList(
                    requestAnswersDocSnapshotsList
                ).toMutableList()
                if (::answersAdapter.isInitialized ){
                    val extendedRequestAnswersList =
                        answersAdapter.getPreviousPostsList().toMutableList()
                    extendedRequestAnswersList.addAll(requestAnswersList)
                    setNewsFeedAdapter(extendedRequestAnswersList)
                }else{
                    setNewsFeedAdapter(requestAnswersList)
                }

            }else{
                if (::answersAdapter.isInitialized){
                    Toast.makeText(this@SendRequestsActivity, requestAnswersDocSnapshotsList?.size.toString(), Toast.LENGTH_SHORT).show()
                    answersAdapter.hideLoadMoreButton()
                }
            }
        }
    }

    private fun setNewsFeedAdapter(postList: List<Post>) {
        answersAdapter = FeedRvAdapter(this, postList.toMutableList())
        answersAdapter.attachmentItemClickListener = this@SendRequestsActivity
        answersAdapter.retryButtonClickListener = this@SendRequestsActivity
        answersAdapter.addToBookmarksButtonClickListener = this@SendRequestsActivity
        answersAdapter.loadMorePostsClickListener = this@SendRequestsActivity
        answersAdapter.bookmarksListener = this@SendRequestsActivity
        bottomSheetBinding.rvMyRequestAnswers.layoutManager = LinearLayoutManager(
            this,
                LinearLayoutManager.VERTICAL, false
            )
        bottomSheetBinding.rvMyRequestAnswers.adapter = answersAdapter
        /*
            binding?.apply {
                loadingAnimation.cancelAnimation()
                loadingAnimation.visibility = View.GONE
                rvFeed.visibility = View.VISIBLE
            }

         */
    }

    override fun onDownloadFileClick(downloadUrl: String, fileName: String, downloadPath: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(
                    this@SendRequestsActivity,
                    Manifest.permission.FOREGROUND_SERVICE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(this@SendRequestsActivity, DownloadService::class.java).apply {
                    putExtra("downloadUrl", downloadUrl)
                    putExtra("fileName", fileName)
                    putExtra("downloadPath", downloadPath)
                }
                this@SendRequestsActivity.startService(intent)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE)
            }
        } else {
            val intent = Intent(this@SendRequestsActivity, DownloadService::class.java).apply {
                putExtra("downloadUrl", downloadUrl)
                putExtra("fileName", fileName)
                putExtra("downloadPath", downloadPath)
            }
            startService(intent)
        }
    }

    override fun onRetryClick() {
        TODO("Not yet implemented")
    }

    override fun onBookmarkButtonClick(bookmark: Bookmark, position: Int) {
        requestViewModel.updateBookmark(bookmark)
        answersAdapter.notifyItemChanged(position)
    }

    override fun onLoadMorePostsClicked(requestOwnerId : String) {
        requestViewModel.getNextRequestAnswersBatch(requestOwnerId)
    }

    override fun inMyBookmarksListener(postId: String, callback: (Boolean) -> Unit) {
        requestViewModel.inMyBookmarks(postId){inMyBookmarks ->
            callback(inMyBookmarks)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}