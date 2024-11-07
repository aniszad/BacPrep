package com.tp.bacprep.presentation.activities.admin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tp.bacprep.presentation.activities.BaseActivity
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.FeedRvAdapter
import com.tp.bacprep.databinding.ActivityMyPostsBinding
import com.tp.bacprep.domain.models.Bookmark
import com.tp.bacprep.domain.models.Post
import com.tp.bacprep.presentation.viewmodels.PostsFeedViewModel
import com.tp.bacprep.util.DocumentSnapshotConverter
import com.tp.bacprep.util.DownloadService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPostsActivity : BaseActivity(), FeedRvAdapter.AddToBookmarksClickListener,
    FeedRvAdapter.RetrySearchButtonClickListener, FeedRvAdapter.AttachmentItemClickListener {
    private lateinit var binding : ActivityMyPostsBinding
    private val postsViewModel: PostsFeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedRvAdapter
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts
            .RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this@MyPostsActivity, "permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MyPostsActivity, "permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this@MyPostsActivity.finish()
            }
        })
        postsViewModel.getMyPosts()
        postsViewModel.myPosts.observe(this@MyPostsActivity){ result ->
            if (result.second != null){
                runOnUiThread {
                    setMyPostsFeedRv(result.second)
                    binding.tvPostsCount.text = (result.second?.size ?: 0).toString()
                }
            }
        }
    }

    private fun setMyPostsFeedRv(postList: List<Post>?) {
        if (postList != null){
            feedAdapter = FeedRvAdapter(this@MyPostsActivity, postList.toMutableList())
            feedAdapter.attachmentItemClickListener = this@MyPostsActivity
            feedAdapter.retryButtonClickListener = this@MyPostsActivity
            feedAdapter.addToBookmarksButtonClickListener = this@MyPostsActivity
            binding.rvMyPosts.layoutManager = LinearLayoutManager(
                this@MyPostsActivity,
                LinearLayoutManager.VERTICAL, false
            )
            binding.rvMyPosts.adapter = feedAdapter
        }

    }

    override fun onRetryClick() {
        if (postsViewModel.postsBatch.isInitialized) {
            val docSnapshots = postsViewModel.postsBatch.value
            val postsList = DocumentSnapshotConverter().mapDocSnapshotsToPostList(docSnapshots)
            setMyPostsFeedRv(postsList)
        }
    }

    override fun onBookmarkButtonClick(bookmark: Bookmark, position: Int) {
        Toast.makeText(this@MyPostsActivity, "bookmark clicked", Toast.LENGTH_SHORT).show()
        postsViewModel.updateBookmark(bookmark)
        feedAdapter.notifyItemChanged(position)
    }

    override fun onDownloadFileClick(downloadUrl: String, fileName: String, downloadPath:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(
                    this@MyPostsActivity,
                    Manifest.permission.FOREGROUND_SERVICE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(this@MyPostsActivity, DownloadService::class.java).apply {
                    putExtra("downloadUrl", downloadUrl)
                    putExtra("fileName", fileName)
                }
                this@MyPostsActivity.startService(intent)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.FOREGROUND_SERVICE)
            }
        } else {
            val intent = Intent(this@MyPostsActivity, DownloadService::class.java).apply {
                putExtra("downloadUrl", downloadUrl)
                putExtra("fileName", fileName)
            }
            this@MyPostsActivity.startService(intent)
        }
    }


}