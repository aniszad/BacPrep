package com.tp.bacprep.presentation.adapters.recyclerviewsAdapters

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.tp.bacprep.R
import com.tp.bacprep.presentation.activities.FileViewerActivity
import com.tp.bacprep.databinding.EmptyFeedItemLayoutBinding
import com.tp.bacprep.databinding.LoadMoreFeedItemViewBinding
import com.tp.bacprep.databinding.MoreFlairsDialogLayoutBinding
import com.tp.bacprep.databinding.NewsFeedItemLayoutBinding
import com.tp.bacprep.domain.models.Bookmark
import com.tp.bacprep.domain.models.Post
import com.tp.bacprep.util.AttachmentView
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.FlairView
import com.tp.bacprep.util.FormattedTimestampDate
import com.tp.bacprep.util.SharedPref
import java.io.File


class FeedRvAdapter(
    private val context: Context,
    private var postsList: MutableList<Post>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_POST = 1
        const val VIEW_LOAD_MORE_POSTS = 2
    }


    private lateinit var dialog: Dialog
    private var isLoadMoreAvailable : Boolean = true


    var attachmentItemClickListener: AttachmentItemClickListener? = null
    var addToBookmarksButtonClickListener: AddToBookmarksClickListener? = null
    var retryButtonClickListener: RetrySearchButtonClickListener? = null
    var loadMorePostsClickListener: LoadMorePostsClickListener? = null
    var bookmarksListener: BookmarksListener? = null
    var onLongHold: OnLongHold? = null


    interface BookmarksListener{
        fun inMyBookmarksListener(postId: String, callback : (Boolean) -> Unit)
    }

    interface AttachmentItemClickListener {
        fun onDownloadFileClick(downloadUrl: String, fileName: String, downloadPath: String)
    }

    interface AddToBookmarksClickListener {
        fun onBookmarkButtonClick(bookmark: Bookmark, position: Int)
    }

    interface RetrySearchButtonClickListener {
        fun onRetryClick()
    }

    interface LoadMorePostsClickListener {
        fun onLoadMorePostsClicked(requestOwnerId:String)
    }

    interface OnLongHold{
        fun onLongHoldPostOwner(ownerId :String, postId : String)
    }

    class UserNewsFeedViewHolder(binding: NewsFeedItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val flairsLl = binding.llFlairs
        val attachmentsLl = binding.llAtachments
        val postTitle = binding.tvPostTitle
        val postContent = binding.tvPostText
        val postOwnerFullName = binding.tvUserName
        val timestamp = binding.tvTime
        val btnBookmark = binding.btnAddToBookmark
        val btnMoreFlairs = binding.btnMoreFlairs
        val replyingToRequestView = binding.clRepliedToPost
        val requestText = binding.tvRequestText
        val btnShowMoreRequestText = binding.btnShowMoreRequestText
        val postView = binding.root
    }

    class EmptyViewHolder(binding: EmptyFeedItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val btnRetry = binding.btnRetry
    }

    class EmptySpaceViewHolder(binding: LoadMoreFeedItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val btnLoadMorePosts = binding.btnLoadMorePosts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_POST -> {
                val binding =
                    NewsFeedItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                UserNewsFeedViewHolder(binding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyBinding =
                    EmptyFeedItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                EmptyViewHolder(emptyBinding)
            }
            VIEW_LOAD_MORE_POSTS -> {
                val binding =
                    LoadMoreFeedItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EmptySpaceViewHolder(binding)
            }
            else -> {
                Log.e("Feed adapter, view type error", "View type not supported")
                val emptyBinding =
                    EmptyFeedItemLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                EmptyViewHolder(emptyBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserNewsFeedViewHolder -> {         // checking If the view type is the regular post view
                val currentPost = postsList[position]
                setFlairs(holder, currentPost.flairsList) // setting the flairs view
                bookmarksListener?.inMyBookmarksListener(currentPost.id){ inMyBookmarks ->
                    updateBookmarkButtonState(inMyBookmarks, holder)
                } // Setting the bookmark button view (the post is saved/not saved)
                holder.postContent.text = currentPost.content          // setting the content of the post (text)
                holder.postTitle.text = currentPost.title              // setting the title of the post
                holder.postOwnerFullName.text = currentPost.ownerFullName   // setting the nameof the post owner
                val timestampText = when (currentPost) {                 // formatting the timestamp depending on the time of timestamp
                    is Bookmark -> {   // java timestamp are for bookmarks saved in room database
                        FormattedTimestampDate().formatTimestamp(context, fbTimestamp = null , javaTimestamp = java.sql.Timestamp(currentPost.savedDate))
                    }
                    else -> {   // firebase timestamp are for posts saved in firebase
                        FormattedTimestampDate().formatTimestamp(context, fbTimestamp = currentPost.timestamp, javaTimestamp = null)
                    }
                }
                holder.timestamp.text = timestampText  // setting the date text
                holder.btnBookmark.setOnClickListener {
                    updateBookmarkInRoomDb(currentPost, position)
                }   // updating bookmark (adding/deleting in roomDb)
                if (currentPost.flairsList.isEmpty()) {
                    holder.btnMoreFlairs.visibility = View.GONE
                }   // Hiding flairs more button when there's no flairs
                if (currentPost.repliedToRequest) {
                    setReplyPostView(holder, currentPost)   // setting the reply post view (request text)
                }
                // show more flairs
                holder.btnMoreFlairs.setOnClickListener {
                    showMoreFlairsDialog(currentPost.flairsList)
                }
                // setting the attachment view
                if (currentPost.attachmentsList != null) {
                    setAttachments(holder, currentPost.attachmentsList!!)
                }
                holder.postView.setOnLongClickListener {
                    Toast.makeText(context, "${currentPost.ownerId==SharedPref(context).getUserId()}", Toast.LENGTH_SHORT).show()
                    if (currentPost.ownerId == SharedPref(context).getUserId()){
                        onLongHold?.onLongHoldPostOwner(currentPost.ownerId, currentPost.id)
                    }
                    true
                }

            }
            is EmptyViewHolder -> {
                holder.btnRetry.setOnClickListener {
                    retryButtonClickListener?.onRetryClick()
                }
            }
            is EmptySpaceViewHolder -> {
                if (isLoadMoreAvailable){
                    holder.btnLoadMorePosts.visibility = View.VISIBLE
                    holder.btnLoadMorePosts.setOnClickListener {
                        val currentPost = postsList[position-1]
                        if (currentPost.request?.ownerId != null){
                            loadMorePostsClickListener?.onLoadMorePostsClicked(currentPost.request!!.ownerId)
                        }
                    }
                }else{
                    holder.btnLoadMorePosts.visibility = View.GONE
                }

            }
        }
    }

    private fun setReplyPostView(holder: UserNewsFeedViewHolder, currentPost: Post) {
        holder.replyingToRequestView.visibility = View.VISIBLE
        holder.requestText.text = buildString {
            append(context.getString(R.string.a_repondu_a))
            append(currentPost.request?.requestText ?: "")
        }
        holder.btnShowMoreRequestText.setOnClickListener {
            expand(holder)
        }
    }

    private fun updateBookmarkInRoomDb(currentPost: Post, position: Int) {
        addToBookmarksButtonClickListener?.onBookmarkButtonClick(
            Bookmark(
                bookmarkId = currentPost.id,
                bookmarkOwnerId = currentPost.ownerId,
                bookmarkOwnerFullName = currentPost.ownerFullName,
                bookmarkTitle = currentPost.title,
                bookmarkContent = currentPost.content,
                bookmarkRequest = currentPost.request?.requestText,
                savedDate = System.currentTimeMillis(),
                bookmarkAttachmentsList = currentPost.attachmentsList,
                bookmarkFlairsList = currentPost.flairsList,
            ), position
        )
    }

    private fun updateBookmarkButtonState(inMyBookmarks: Boolean, holder:UserNewsFeedViewHolder) {
        val iconTintRes = if (inMyBookmarks) R.color.yellow else android.R.color.darker_gray
        val text = if (inMyBookmarks) context.getString(R.string.enregistre) else context.getString(R.string.enregistrer)
        val iconTint = ColorStateList.valueOf(ContextCompat.getColor(context, iconTintRes))
        holder.btnBookmark.iconTint = iconTint
        holder.btnBookmark.text = text
    }

    override fun getItemCount(): Int {
        return if (postsList.isEmpty()) 1 else postsList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (postsList.isEmpty()) {
            VIEW_TYPE_EMPTY
        } else {
            if (position == postsList.size) {
                VIEW_LOAD_MORE_POSTS
            } else {
                VIEW_TYPE_POST
            }
        }
    }

    private fun setFlairs(holder: UserNewsFeedViewHolder, flairsList: List<String>) {
        holder.flairsLl.removeAllViews()
        if (flairsList.isNotEmpty()) {
            for (flair in flairsList.subList(0, if(flairsList.size > 2) 2 else flairsList.size)) {
                val flairView = FlairView(context, null)
                flairView.setText(flair)
                holder.flairsLl.addView(flairView)
            }
        }
    }

    private fun setAttachments(
        holder: UserNewsFeedViewHolder,
        attachmentsList: List<Pair<String, String>>,
    ) {
        holder.attachmentsLl.removeAllViews()
        for (attach in attachmentsList) {
            val attachView = AttachmentView(context, null)
            attachView.setText(attach.second)
            val downloadPath =
                (context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path +"/"+ Constants.DOWNLOADS_FOLDER_NAME)
            if (!fileExistsLocally(context, attach.second)){
                attachView.downloadAttachBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_download))
                attachView.downloadAttachBtn.setOnClickListener {

                    attachmentItemClickListener?.onDownloadFileClick(attach.first, attach.second, downloadPath)
                }
            }else{
                attachView.downloadAttachBtn.visibility = View.GONE
                openFileOrImage(attachView, attach,downloadPath)


            }
            holder.attachmentsLl.addView(attachView)
        }
    }

    private fun openFileOrImage(
        attachView: AttachmentView,
        attach: Pair<String, String>,
        downloadPath: String
    ) {
        attachView.setOnClickListener {
            if (attach.second.endsWith(Constants.PDF_EXTENSION)){
                val file: File = File(downloadPath, attach.second)
                val uri: Uri = FileProvider.getUriForFile(context, "com.tp.bacprep.fileprovider", file)
                val intent = Intent(context, FileViewerActivity::class.java)
                intent.putExtra(Constants.FILE_VIEWER_TYPE, Constants.OPEN_PDF)
                intent.putExtra("Uri_file_top_open", uri)
                context.startActivity(intent)
            }else if (attach.second.endsWith(".png") || attach.second.endsWith(".jpg")){
                val file: File = File(downloadPath, attach.second)
                val uri: Uri = FileProvider.getUriForFile(context, "com.tp.bacprep.fileprovider", file)
                val intent = Intent(context, FileViewerActivity::class.java)
                intent.putExtra(Constants.FILE_VIEWER_TYPE, Constants.OPEN_IMAGE)
                intent.putExtra("Uri_file_top_open", uri)
                context.startActivity(intent)
            }

        }
    }

    private fun showMoreFlairsDialog(flairsList: List<String>) {
        dialog = Dialog(context)
        val binding = MoreFlairsDialogLayoutBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        for (flair in flairsList) {
            val myFlair = FlairView(context, null)
            myFlair.setText(flair)
            myFlair.setMargins(8, 8, 8, 8)
            binding.moreFlairsLl.addView(myFlair)
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    private fun expand(holder: UserNewsFeedViewHolder) {
        val maxLines = if (holder.requestText.maxLines == 1) Int.MAX_VALUE else 1
        holder.requestText.maxLines = maxLines
        val arrowDrawableRes =
            if (maxLines == 1) R.drawable.icon_drop_down_arrow else R.drawable.icon_arrow_up
        holder.btnShowMoreRequestText.setImageDrawable(ContextCompat.getDrawable(context, arrowDrawableRes))

        val initialHeight = holder.replyingToRequestView.height
        // Step 2: Temporarily set the height to wrap_content
        holder.replyingToRequestView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        holder.replyingToRequestView.requestLayout()

        // Step 3: Measure the new height after setting it to wrap_content
        holder.replyingToRequestView.measure(
            View.MeasureSpec.makeMeasureSpec(holder.replyingToRequestView.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val newHeight = holder.replyingToRequestView.measuredHeight

        // Step 4: Create a ValueAnimator
        val animator = ValueAnimator.ofInt(initialHeight, newHeight)

        // Step 5: Update the height during the animation
        animator.addUpdateListener { animation ->
            val animatedHeight = animation.animatedValue as Int
            val layoutParams = holder.replyingToRequestView.layoutParams
            layoutParams.height = animatedHeight
            holder.replyingToRequestView.layoutParams = layoutParams
        }

        // Set animation duration
        animator.duration = 500

        // Start the animation
        animator.start()
    }

    fun removeBookmark(postPosition: Int) {
        postsList.removeAt(postPosition)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        postsList.clear() // Set postsList to an empty list to clear the data
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    fun getPreviousPostsList() : List<Post>{
        return postsList
    }

    fun hideLoadMoreButton() {
        isLoadMoreAvailable = false
        notifyDataSetChanged()

    }

    fun fileExistsLocally(context: Context, fileName : String):Boolean{
        val downloadsFilePath = buildString {
            append(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path)
            append("/")
            append(Constants.DOWNLOADS_FOLDER_NAME)
        }
        val file = File(
            downloadsFilePath,
            fileName
        )
        return file.exists()
    }
}

