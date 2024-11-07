package com.tp.bacprep.presentation.activities.user


import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.FeedRvAdapter
import com.tp.bacprep.databinding.ActivityFilesBinding
import com.tp.bacprep.domain.models.Bookmark
import com.tp.bacprep.domain.models.FileDriveItem
import com.tp.bacprep.domain.models.RecentOpenedFile
import com.tp.bacprep.presentation.viewmodels.FbFileManagerViewModel
import com.tp.bacprep.presentation.viewmodels.PostsFeedViewModel
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.DownloadService
import com.tp.bacprep.util.FileManager
import com.tp.bacprep.util.FirebaseFileManager
import com.tp.bacprep.util.NetworkChangeReceiver
import com.tp.bacprep.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilesActivity : BaseActivity(), FirebaseFileManager.FbFileManagerViewModelFunctions,
    FirebaseFileManager.FinishActivityListener, FileManager.FileManagerViewModelFunctions,
    FeedRvAdapter.AttachmentItemClickListener, FeedRvAdapter.RetrySearchButtonClickListener,
    FeedRvAdapter.AddToBookmarksClickListener, FeedRvAdapter.BookmarksListener ,NetworkChangeReceiver.ConnectivityChangeListener {
    // View binding
    private lateinit var binding : ActivityFilesBinding
    // bookmarks adapter
    private lateinit var feedAdapter : FeedRvAdapter
    // post (bookmarks) view model
    private val postsViewModel: PostsFeedViewModel by viewModels()
    // file manager type (initialized by intent)
    private lateinit var viewType : String
    // Downloads file manager variables
    private lateinit var downloadsFileManager : FileManager
    // Firebase files file manager variables
    private val fbFileManagerViewModel : FbFileManagerViewModel by viewModels()
    private lateinit var fbFileManager : FirebaseFileManager

    private val networkChangeReceiver = NetworkChangeReceiver()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts
            .RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this@FilesActivity, "permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@FilesActivity, "permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val data = intent?.getBooleanExtra("downloadComplete", false)
            val positionInRv = intent?.getIntExtra("position", -1) ?: -1
            Toast.makeText(this@FilesActivity, positionInRv.toString(), Toast.LENGTH_SHORT).show()
            if (data != null) {
                // Handle the received data
                if (data==true){
                    if (positionInRv != -1){
                        feedAdapter.notifyItemChanged(positionInRv)
                    }
                    unregister(this@FilesActivity)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerConnectivityBroadcastReceiver()

        // setting the status bar color
        setStatusBarColor(ContextCompat.getColor(this@FilesActivity, R.color.light_gray_to_deep_charcoal), !SharedPref(this@FilesActivity).getThemeMode())

        // getting the file manager type
        if (intent != null){
            viewType = intent.getStringExtra(Constants.FILE_MANAGER_TYPE) ?: ""
        }
        binding.toolbarDownloads.setNavigationIconTint(ContextCompat.getColor(this@FilesActivity, R.color.white_constant))
        when(viewType){
            Constants.DOWNLOADS->{
                binding.apply {
                    // initializing fileManager
                    downloadsFileManager = FileManager(this@FilesActivity)
                    downloadsFileManager.setRecyclerView(rvDownloads)
                    downloadsFileManager.setFileManagerVmInterface(this@FilesActivity)
                    // setting the action bar
                    setSupportActionBar(toolbarDownloads)
                    // setting the back button functionality
                    toolbarDownloads.setNavigationOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                // handling back button clicked
                onBackPressedDispatcher.addCallback(
                    this /* lifecycle owner */,
                    object : OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            downloadsFileManager.navigateBackOneLevel()
                        }
                    })
            }
            Constants.FILES_BANK->{
                binding.apply {
                    // initializing fileManager
                    fbFileManager = FirebaseFileManager(this@FilesActivity, requestPermissionLauncher)
                    // setting the recycler view (necessarily after setting the view model interface)
                    fbFileManager.setRecyclerView(rvDownloads)
                    // setting the view model interface functions
                    fbFileManager.setFbManagerVmInterface(this@FilesActivity)
                    // setting finishing activity interface
                    fbFileManager.setFinishActivityListener(this@FilesActivity)

                    // setting the action bar
                    setSupportActionBar(toolbarDownloads)
                    // handling back button clicked
                    onBackPressedDispatcher.addCallback(
                        this@FilesActivity /* lifecycle owner */,
                        object : OnBackPressedCallback(true) {
                            override fun handleOnBackPressed() {
                                fbFileManager.navigateBackOneLevel()
                            }
                        })
                    // setting the back button functionality
                    toolbarDownloads.setNavigationOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }

            }
            Constants.BOOKMARKS ->{
                binding.toolbarDownloads.title = buildString { append(getString(R.string.enregistrements)) }
                postsViewModel.getAllBookmarks { bookmarksList ->
                    setNewsFeedAdapter(bookmarksList)
                }
            }
            Constants.RESOURCES ->{

            }

        }
    }

    // inflating ActionBar (toolbar) menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_downloads_toolbar_menu, menu)
        if (menu != null) {setSearchViewStyle(menu)}
        return when (viewType){
            Constants.DOWNLOADS ->{
                if (menu!=null) {
                    val searchItem = menu.findItem(R.id.menu_downloads_search)
                    val searchView = searchItem.actionView as SearchView
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return true
                        }
                        override fun onQueryTextChange(query: String?): Boolean {
                            if (query != null){
                                downloadsFileManager.searchFileAndFolders(query)
                            }
                            return true
                        }
                    })
                }
                true
            }
            Constants.FILES_BANK->{
                binding.toolbarDownloads.title = getString(R.string.banque_de_sujets)
                super.onCreateOptionsMenu(menu)
            }
            else ->{
                return true
            }
        }

    }

    private fun setSearchViewStyle(menu: Menu) {
        val searchItem = menu.findItem(R.id.menu_downloads_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        val search = searchItem.actionView as SearchView
        val editText = search.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(ContextCompat.getColor(this@FilesActivity, R.color.black))
        editText.setHintTextColor(ContextCompat.getColor(this@FilesActivity, R.color.gray_to_light_gray))
        val closeButton = search.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeButton.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@FilesActivity, R.color.white_constant))
        val searchButton = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        searchButton.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@FilesActivity, R.color.black))
    }

    // handling ActionsBar's menu buttons when clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_downloads_search->{

            }
        }
        return true
    }

    override fun getFbFiles(path: String) {
        fbFileManagerViewModel.queryDriveFiles(path)
    }

    override fun setFilesListObserver(callback: (List<FileDriveItem>) -> Unit) {
        fbFileManagerViewModel.driveFilesList.observe(this@FilesActivity){
            callback(it)
        }
    }

    override fun addToRecentFiles(file: RecentOpenedFile) {
        fbFileManagerViewModel.addToRecentFiles(file)
    }

    override fun finishActivity() {
        this@FilesActivity.finish()
    }

    private fun setNewsFeedAdapter(bookmarkList: List<Bookmark>?) {
        if (bookmarkList != null){
            val sortedBookmarksList = bookmarkList.sortedByDescending { it.savedDate }
            runOnUiThread {
                feedAdapter = FeedRvAdapter(this@FilesActivity, sortedBookmarksList.toMutableList())
                feedAdapter.attachmentItemClickListener = this@FilesActivity
                feedAdapter.bookmarksListener = this@FilesActivity
                feedAdapter.addToBookmarksButtonClickListener = this@FilesActivity
                feedAdapter.retryButtonClickListener = this@FilesActivity
                binding.rvDownloads.layoutManager = LinearLayoutManager(
                    this@FilesActivity,
                    LinearLayoutManager.VERTICAL, false
                )
                binding.rvDownloads.adapter = feedAdapter
            }
        }
    }

    override fun onDownloadFileClick(downloadUrl: String, fileName: String, downloadPath : String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(
                    this@FilesActivity,
                    Manifest.permission.FOREGROUND_SERVICE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(this@FilesActivity, DownloadService::class.java).apply {
                    putExtra("downloadUrl", downloadUrl)
                    putExtra("fileName", fileName)
                    putExtra("downloadPath", downloadPath)
                }
                // registering a broadcast receiver when the download is finished
                register(this@FilesActivity)
                startService(intent)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE)
            }
        } else {
            val intent = Intent(this@FilesActivity, DownloadService::class.java).apply {
                putExtra("downloadUrl", downloadUrl)
                putExtra("fileName", fileName)
                putExtra("downloadPath", downloadPath)
            }
            register(this@FilesActivity)
            startService(intent)
        }
    }

    override fun onBookmarkButtonClick(bookmark: Bookmark, position: Int) {
        Toast.makeText(this@FilesActivity, "bookmark clicked", Toast.LENGTH_SHORT).show()
        postsViewModel.updateBookmark(bookmark)
        feedAdapter.removeBookmark(position)
        binding.rvDownloads.scrollToPosition(position)
    }

    override fun onRetryClick() {
        postsViewModel.getAllBookmarks { bookmarksList ->
            setNewsFeedAdapter(bookmarksList)
        }
    }

    @Suppress("DEPRECATION")
    private fun registerConnectivityBroadcastReceiver() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)
        // Set the ConnectivityChangeListener
        networkChangeReceiver.setConnectivityChangeListener(this@FilesActivity)
    }

    override fun onNetworkConnected() {
    }

    override fun onNetworkDisconnected() {
        // No internet connection
        // Display your internet dialog or handle the scenario accordingly

        when(viewType){
            Constants.FILES_BANK ->{
                showAgreeDialog(this@FilesActivity, resources.getString(R.string.probleme_de_connection),
                    resources.getString(R.string.veuillez_verifiez), ContextCompat.
                    getDrawable(this@FilesActivity, R.drawable.icon_no_internet_connection)
                ) { finishActivity() }
            }
            Constants.RESOURCES ->{
                showAgreeDialog(this@FilesActivity, resources.getString(R.string.probleme_de_connection),
                    resources.getString(R.string.veuillez_verifiez), ContextCompat.
                    getDrawable(this@FilesActivity, R.drawable.icon_no_internet_connection)
                ) { finishActivity() }
            }
        }


    }

    override fun inMyBookmarksListener(postId: String, callback: (Boolean) -> Unit) {
        postsViewModel.inMyBookmarks(postId){inMyBookmarks ->
            runOnUiThread {
                Toast.makeText(this@FilesActivity, inMyBookmarks.toString(), Toast.LENGTH_SHORT).show()
            }
            callback(inMyBookmarks)
        }
    }

    // register broadcast reciever when download finished
    fun register(context: Context) {
        val filter = IntentFilter(Constants.DOWNLOAD_FILE_FROM_FIREBASE_ACTION)
        context.registerReceiver(receiver, filter)
    }

    // unregister broadcast receiver
    fun unregister(context: Context) {
        context.unregisterReceiver(receiver)
    }


}