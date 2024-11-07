package com.tp.bacprep.presentation.adapters.recyclerviewsAdapters

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tp.bacprep.R
import com.tp.bacprep.databinding.LoadMoreFeedItemViewBinding
import com.tp.bacprep.databinding.NoDataFirebaseFileManagerBinding
import com.tp.bacprep.databinding.UserRequestViewItemLayoutBinding
import com.tp.bacprep.domain.models.Request
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.FormattedTimestampDate

class RequestsUsersRvAdapter(private val context: Context, private var requestList : List<Request>, private val receiver : String) : RecyclerView.Adapter<ViewHolder>() {
    companion object{
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_REQUESTS = 1
        const val VIEW_LOAD_MORE_REQUESTS = 2
    }
    var deleteRequestClickListener  : DeleteRequestClickListener? = null
    var onLoadMoreRequests  : LoadMoreRequests? = null
    var onCheckAnswersClicked : OnCheckAnswersClicked? = null

    private var isLoadMoreAvailable : Boolean = true

    class RequestsViewHolder(binding : UserRequestViewItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        var requestOwnerName = binding.tvRequestOwnerName
        var requestText = binding.tvRequestText
        var btnExpandText = binding.imMore
        var date = binding.tvDate
        var btnMoreOptions = binding.btnRequestMoreOptions
    }

    class LoadMoreViewHolder(binding: LoadMoreFeedItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val btnLoadMorePosts = binding.btnLoadMorePosts
    }

    class NoDataViewHolder(binding: NoDataFirebaseFileManagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface DeleteRequestClickListener {
        fun onDeleteButtonClicked(request: Request, position : Int)
    }

    interface OnCheckAnswersClicked{
        fun onCheckAnswersClicked(requestOwnerId : String)
    }

    interface LoadMoreRequests {
        fun onLoadMoreRequestsClicked()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            VIEW_TYPE_REQUESTS ->{
                RequestsViewHolder(UserRequestViewItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            VIEW_TYPE_EMPTY ->{
                NoDataViewHolder(NoDataFirebaseFileManagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            VIEW_LOAD_MORE_REQUESTS ->{
                LoadMoreViewHolder(LoadMoreFeedItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else->{
                RequestsViewHolder(UserRequestViewItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return requestList.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder){
            is RequestsViewHolder ->{
                val currentRequest = requestList[position]
                holder.apply {
                    date.text = FormattedTimestampDate().formatTimestamp(context, fbTimestamp = currentRequest.timestamp, javaTimestamp = null)
                    requestOwnerName.text = currentRequest.ownerName
                    requestText.text = currentRequest.requestText
                    if (receiver != Constants.USER) {
                        btnMoreOptions.visibility = View.GONE
                    }
                    btnExpandText.setOnClickListener {
                        if (holder.requestText.maxLines == 1){
                            holder.requestText.maxLines = Int.MAX_VALUE
                            btnExpandText.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_arrow_up))
                            expand(holder)
                        }else{
                            holder.requestText.maxLines = 1
                            btnExpandText.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_arrow_up))
                            expand(holder)
                        }
                    }

                    btnMoreOptions.setOnClickListener {
                        showPopupMenu(btnMoreOptions, currentRequest, position)
                    }
                }
            }
            is NoDataViewHolder ->{

            }
            is LoadMoreViewHolder ->{
                if (isLoadMoreAvailable){
                    holder.btnLoadMorePosts.visibility = View.VISIBLE
                    holder.btnLoadMorePosts.setOnClickListener {
                        onLoadMoreRequests?.onLoadMoreRequestsClicked()
                    }
                }else{
                    holder.btnLoadMorePosts.visibility = View.GONE
                }
            }
            else ->{

            }
        }

    }

    private fun expand(holder : RequestsViewHolder) {
        val initialHeight = holder.requestText.height
        // Step 2: Temporarily set the height to wrap_content
        holder.requestText.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        holder.requestText.requestLayout()

        // Step 3: Measure the new height after setting it to wrap_content
        holder.requestText.measure(
            View.MeasureSpec.makeMeasureSpec(holder.requestText.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val newHeight = holder.requestText.measuredHeight

        // Step 4: Create a ValueAnimator
        val animator = ValueAnimator.ofInt(initialHeight, newHeight)

        // Step 5: Update the height during the animation
        animator.addUpdateListener { animation ->
            val animatedHeight = animation.animatedValue as Int
            val layoutParams = holder.requestText.layoutParams
            layoutParams.height = animatedHeight
            holder.requestText.layoutParams = layoutParams
        }

        // Set animation duration
        animator.duration = 500

        // Start the animation
        animator.start()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyRequestRemoved(position : Int) {
        val updatedList = requestList.toMutableList()
        updatedList.removeAt(position)
        requestList = updatedList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun itemAdded(request: Request) {
        val updatedList = requestList.toMutableList()
        updatedList.add(0, request)
        requestList = updatedList
        notifyDataSetChanged()
    }

    private fun showPopupMenu(view: View, request: Request,position: Int) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_logout_button, popupMenu.menu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            popupMenu.setForceShowIcon(true)
        }
        popupMenu.menu.getItem(0).iconTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
        // Set item click listeners for the menu items
        popupMenu.setOnMenuItemClickListener {  menuItem ->
            when(menuItem.itemId){
                R.id.menu_delete_request ->{
                    deleteRequestClickListener?.onDeleteButtonClicked(request , position)
                }
                R.id.menu_request_answers ->{
                    onCheckAnswersClicked?.onCheckAnswersClicked(request.ownerId)
                }
            }
            false
        }
        // Show the popup menu
        popupMenu.show()
    }

    override fun getItemViewType(position: Int): Int {
        return if (requestList.isEmpty()){
            VIEW_TYPE_EMPTY
        }else if(position == requestList.size){
            VIEW_LOAD_MORE_REQUESTS
        }else{
            VIEW_TYPE_REQUESTS
        }
    }

    fun getPreviousPostsList(): List<Request> {
        return requestList
    }

    fun hideLoadMoreButton() {
        isLoadMoreAvailable = false
        notifyDataSetChanged()
    }

    fun resetContent() {
        this.requestList = listOf()
    }

}