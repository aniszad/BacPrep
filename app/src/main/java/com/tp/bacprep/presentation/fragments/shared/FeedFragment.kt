package com.tp.bacprep.presentation.fragments.shared

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.tp.bacprep.R
import com.tp.bacprep.data.datasource.FbAuth
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.FeedRvAdapter
import com.tp.bacprep.databinding.FragmentFeedBinding
import com.tp.bacprep.domain.models.Bookmark
import com.tp.bacprep.domain.models.Post
import com.tp.bacprep.presentation.activities.admin.AdminMainActivity
import com.tp.bacprep.presentation.activities.user.UserMainActivity
import com.tp.bacprep.presentation.activities.user.UserSettingsActivity
import com.tp.bacprep.presentation.fragments.BaseFragment
import com.tp.bacprep.presentation.viewmodels.BaseViewModel
import com.tp.bacprep.presentation.viewmodels.PostsFeedViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.DocumentSnapshotConverter
import com.tp.bacprep.util.DownloadService
import com.tp.bacprep.util.NetworkChangeReceiver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FeedFragment : BaseFragment(), FeedRvAdapter.AttachmentItemClickListener, NetworkChangeReceiver.ConnectivityChangeListener,
    FeedRvAdapter.RetrySearchButtonClickListener,
    FeedRvAdapter.AddToBookmarksClickListener, FeedRvAdapter.LoadMorePostsClickListener,
    FeedRvAdapter.BookmarksListener, FeedRvAdapter.OnLongHold {
    private var binding: FragmentFeedBinding? = null
    private var isStatusBarColorMatched = false
    private val networkChangeReceiver = NetworkChangeReceiver()
    private val postsViewModel: PostsFeedViewModel by activityViewModels()
    private val sharedViewModel : BaseViewModel by activityViewModels()
    private lateinit var feedAdapter: FeedRvAdapter
    private var isFilterVisible = false
    private var filtersReady = false
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts
            .RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), getString(R.string.autorisation_accordée), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.autorisation_refusée), Toast.LENGTH_SHORT).show()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val data = intent?.getBooleanExtra("downloadComplete", false)
            val positionInRv = intent?.getIntExtra("position", -1) ?: -1
            Toast.makeText(requireContext(), positionInRv.toString(), Toast.LENGTH_SHORT).show()
            if (data != null) {
                // Handle the received data
                if (data==true){
                    if (positionInRv != -1){
                        feedAdapter.notifyItemChanged(positionInRv)
                    }
                    unregister(requireContext())
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // observe internet connection changes
        registerConnectivityBroadcastReceiver()
        // Observe ViewModel Data
        setViewModelDataOnObserve()
        // get Initial First Posts batch
        getPosts()
        // setting corresponding view depending on the type of the user
        setRoleView()
        // setting search bar recent suggestions
        setSearchBarSuggestionAdapter()
        // setting buttons functionality
        setButtonsFunctionality()
    }

    private fun setButtonsFunctionality() {
        binding?.apply {
            btnSearch.setOnClickListener {
                if (autoTvAdminSearch.text.isNotBlank() && filtersReady){
                    val queryString = autoTvAdminSearch.text.toString()
                    performSearch(queryString)
                }
            }
            autoTvAdminSearch.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Perform the search action here
                    if (autoTvAdminSearch.text.isNotBlank() && filtersReady){
                        val queryString = autoTvAdminSearch.text.toString()
                        performSearch(queryString)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            feedRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), R.color.green),
            )
            // implementing feed refreshing
            feedRefreshLayout.setOnRefreshListener {
                getLatestPosts()
                feedRefreshLayout.isRefreshing = false
            }
            // Set up the scroll listener for the blue view
            rvFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // Calculate the threshold position based on your needs
                    val statusBarColorThreshold = recyclerView.height / 2 - 300

                    // Get the current scroll position
                    val scrollPosition = recyclerView.computeVerticalScrollOffset()

                    // Check if the scroll position has passed the threshold
                    if (scrollPosition >= statusBarColorThreshold && !isStatusBarColorMatched) {
                        // Change the status bar color to match the view's color
                        changeStatusBarColor(Color.WHITE)
                        changeToolbarColors(Color.WHITE)
                        isStatusBarColorMatched = true
                    } else if (scrollPosition < statusBarColorThreshold && isStatusBarColorMatched) {
                        // Change the status bar color back to the original color
                        changeStatusBarColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_gray_to_deep_charcoal
                            )
                        )
                        changeToolbarColors(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_gray_to_deep_charcoal
                            )
                        )
                        isStatusBarColorMatched = false
                    }
                }
            })
        }

    }

    private fun setViewModelDataOnObserve() {

        postsViewModel.postsBatch.observe(viewLifecycleOwner) { docSnapshotsList ->
            addNextPostsBatchToFeed(docSnapshotsList)
        }

        postsViewModel.queryResults.observe(viewLifecycleOwner){ queryResults ->
            requireActivity().runOnUiThread {
                val postsList = DocumentSnapshotConverter().mapDocSnapshotsToPostList(queryResults)
                setNewsFeedAdapter(postsList)
            }

        }

        postsViewModel.deleteResult.observe(viewLifecycleOwner){ isDeleted ->
            if (isDeleted){
                Toast.makeText(requireContext(), "Post deleted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Failed to delete", Toast.LENGTH_SHORT).show()
            }
            hideWarningDialog()
        }
    }

    private fun addNextPostsBatchToFeed(docSnapshotsList : List<DocumentSnapshot>?) {
        Toast.makeText(requireContext(), "hehe", Toast.LENGTH_SHORT).show()
        if (!docSnapshotsList.isNullOrEmpty()){
            postsViewModel.setLastVisible(docSnapshotsList.last())
            if (::feedAdapter.isInitialized && feedAdapter.getPreviousPostsList().contains(
                    DocumentSnapshotConverter().mapDocSnapshotsToPostList(docSnapshotsList)[0]
                )){
                val extendedPostsList =
                    feedAdapter.getPreviousPostsList().toMutableList()
                extendedPostsList.addAll(DocumentSnapshotConverter().mapDocSnapshotsToPostList(docSnapshotsList))
                setNewsFeedAdapter(extendedPostsList)
            }else{
                val postsList = DocumentSnapshotConverter().mapDocSnapshotsToPostList(docSnapshotsList)
                setNewsFeedAdapter(postsList)
            }

        }else{
            if (::feedAdapter.isInitialized){
                Toast.makeText(requireContext(), getString(R.string.aucun_element_trouvé), Toast.LENGTH_SHORT).show()
                feedAdapter.hideLoadMoreButton()
            }else{
                setNewsFeedAdapter(null)
            }
        }
    }

    private fun getLatestPosts() {
        postsViewModel.setLastVisible(null)
        postsViewModel.getPostsBatch()
    }

    private fun setRoleView() {
        when(requireActivity()){
            is UserMainActivity ->{
                if (context != null){
                    binding?.btnSettings?.setOnClickListener {
                        val intent = Intent(requireActivity(), UserSettingsActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            }
            is AdminMainActivity ->{
                if (context != null){
                    binding?.btnSettings?.setOnClickListener {
                    }
                }
            }
        }
        sharedViewModel.getCurrentUser(FbAuth(requireContext()).getUserId()){user ->
            if (user!=null){
                requireActivity().runOnUiThread {
                    binding?.tvUsername?.text = buildString {
                        append(user.name)
                        append(" ")
                        append(user.lastName)
                    }
                }

            }
        }

    }

    private fun performSearch(queryString : String) : List<Post> {
        postsViewModel.performQuery(queryString)
        return listOf()
    }

    private fun changeToolbarColors(color: Int) {
        binding?.apply {
            toolbarAdmin.setBackgroundColor(color)
        }
    }

    private fun getPosts() {
        binding?.apply {
            rvFeed.visibility = View.GONE
            loadingAnimation.visibility = View.VISIBLE
            loadingAnimation.playAnimation()
        }
        postsViewModel.setLastVisible(null)
        postsViewModel.getPostsBatch()
    }

    private fun setSearchBarSuggestionAdapter() {
        // Create an array of suggestions
        // TODO -- Save searching expressions and display them
        val suggestions = arrayOf("Apple", "Banana", "Cherry", "Duran", "Elderberry")
        // Create an ArrayAdapter with the suggestions
        val adapter =
            ArrayAdapter(requireContext(), R.layout.search_auto_complete_item_layout, suggestions)
        // Set the adapter on the AutoCompleteTextView
        binding?.autoTvAdminSearch?.setAdapter(adapter)
    }

    private fun changeStatusBarColor(color: Int) {
        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = true  // true or false as desired.
        // And then you can set any background color to the status bar.
        window.statusBarColor = color
    }

    private fun setNewsFeedAdapter(postList: List<Post>?) {
        Toast.makeText(requireContext(), "hehe: "+postList.toString(), Toast.LENGTH_SHORT).show()
        feedAdapter = if (postList == null) {
            FeedRvAdapter(requireContext(), mutableListOf())
        }else {
            FeedRvAdapter(requireContext(), postList.toMutableList())
        }
        feedAdapter.attachmentItemClickListener = this@FeedFragment
        feedAdapter.retryButtonClickListener = this@FeedFragment
        feedAdapter.addToBookmarksButtonClickListener = this@FeedFragment
        feedAdapter.loadMorePostsClickListener = this@FeedFragment
        feedAdapter.bookmarksListener = this@FeedFragment
        feedAdapter.onLongHold = this@FeedFragment
        binding?.rvFeed?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
        binding?.rvFeed?.adapter = feedAdapter
        binding?.apply {
            loadingAnimation.cancelAnimation()
            loadingAnimation.visibility = View.GONE
            rvFeed.visibility = View.VISIBLE
        }
    }

    @Suppress("DEPRECATION")
    private fun registerConnectivityBroadcastReceiver() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(networkChangeReceiver, intentFilter)
        // Set the ConnectivityChangeListener
        networkChangeReceiver.setConnectivityChangeListener(this@FeedFragment)
    }

    // unregistering listeners and releasing resources
    override fun onDestroy() {
        super.onDestroy()
        requestPermissionLauncher.unregister()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // Release the binding
        // Unregister the BroadcastReceiver
        requireActivity().unregisterReceiver(networkChangeReceiver)

    }

    override fun onNetworkDisconnected() {
        binding?.apply {
            rvFeed.visibility = View.GONE
            btnSearch.isEnabled = false
        }
    }

    override fun onNetworkConnected() {
        binding?.apply {
            rvFeed.visibility = View.VISIBLE
            btnSearch.isEnabled = false
        }
    }

    override fun onDownloadFileClick(downloadUrl: String, fileName: String, downloadPath : String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.FOREGROUND_SERVICE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(context, DownloadService::class.java).apply {
                    putExtra("downloadUrl", downloadUrl)
                    putExtra("fileName", fileName)
                    putExtra("downloadPath", downloadPath)
                }
                // registering a broadcast receiver when the download is finished
                register(requireContext())
                requireContext().startService(intent)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE)
            }
        } else {
            val intent = Intent(context, DownloadService::class.java).apply {
                putExtra("downloadUrl", downloadUrl)
                putExtra("fileName", fileName)
                putExtra("downloadPath", downloadPath)
            }
            register(requireContext())
            requireContext().startService(intent)
        }
    }

    override fun onRetryClick() {
        if (postsViewModel.postsBatch.isInitialized) {
            val docSnapshots = postsViewModel.postsBatch.value
            val postsList = DocumentSnapshotConverter().mapDocSnapshotsToPostList(docSnapshots)
            setNewsFeedAdapter(postsList)
        }
    }

    override fun onLoadMorePostsClicked(requestOwnerId : String) {
        postsViewModel.getPostsBatch()
    }

    override fun onBookmarkButtonClick(bookmark: Bookmark, position: Int) {
        postsViewModel.updateBookmark(bookmark)
        feedAdapter.notifyItemChanged(position)
    }

    override fun inMyBookmarksListener(postId: String, callback: (Boolean) -> Unit) {
        postsViewModel.inMyBookmarks(postId){inMyBookmarks ->
            GlobalScope.launch {
                callback(inMyBookmarks)
             }
        }
    }

    // register broadcast receiver
    fun register(context: Context) {
        val filter = IntentFilter(Constants.DOWNLOAD_FILE_FROM_FIREBASE_ACTION)
        context.registerReceiver(receiver, filter)
    }


    // unregister broadcast receiver
    fun unregister(context: Context) {
        context.unregisterReceiver(receiver)
    }

    override fun onLongHoldPostOwner(ownerId : String, postId: String) {
        showWarningDialog(requireContext(), "Do you want to delete this post, this action is irreversible") {
            deletePost(
                ownerId,
                postId
            )
        }
    }

    private fun deletePost(ownerId: String, postId : String){
        postsViewModel.deletePost(ownerId, postId)
    }


}