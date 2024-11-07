package com.tp.bacprep.presentation.fragments.admin

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.tp.bacprep.R
import com.tp.bacprep.databinding.FragmentAdminPostBinding
import com.tp.bacprep.domain.models.Post
import com.tp.bacprep.domain.models.Request
import com.tp.bacprep.presentation.fragments.BaseFragment
import com.tp.bacprep.presentation.fragments.admin.dialogFragments.FlairBottomSheetFragment
import com.tp.bacprep.presentation.viewmodels.CreatePostViewModel
import com.tp.bacprep.util.AttachmentView
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.FlairView
import com.tp.bacprep.util.FormattedTimestampDate
import com.tp.bacprep.util.NetworkChangeReceiver

class AdminCreatePostFragment : BaseFragment(), AttachmentView.RemoveButtonClickListener, NetworkChangeReceiver.ConnectivityChangeListener {

    private var binding: FragmentAdminPostBinding? = null
    private val networkChangeReceiver = NetworkChangeReceiver()
    private val viewModel: CreatePostViewModel by activityViewModels()
    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri: Uri? = data?.data
                if (uri != null) {
                    // Handle the selected file URI here
                    // Process the selected file URI as needed
                    val contentResolver: ContentResolver = requireContext().contentResolver!!
                    val fileUri: Uri = uri

                    val fileExtension = getFileExtension(contentResolver, fileUri)
                    if (fileExtension != null) {
                        when (fileExtension) {
                            "jpg" -> {
                                val cursor =
                                    requireActivity().contentResolver.query(uri, null, null, null, null)
                                cursor?.use {
                                    it.moveToFirst()
                                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                    val fileName = it.getString(nameIndex)
                                    viewModel.addToAttachList(Pair(uri, fileName))
                                }
                            }
                            "pdf" -> {
                                // The file has a PDF extension
                                // Handle accordingly
                                val cursor =
                                    requireActivity().contentResolver.query(uri, null, null, null, null)
                                cursor?.use {
                                    it.moveToFirst()
                                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                    val fileName = it.getString(nameIndex)
                                    viewModel.addToAttachList(Pair(uri, fileName))
                                }
                            }
                            else -> {
                                // The file has a different extension
                                // Handle accordingly or consider using a different approach
                                Toast.makeText(requireContext(), "1", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        // Failed to retrieve the file extension
                        // Handle accordingly
                        Toast.makeText(requireContext(), "2", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // File picking was canceled or failed
                    Toast.makeText(requireContext(), "3", Toast.LENGTH_SHORT).show()
                }
            }
        }
    private fun getFileExtension(contentResolver: ContentResolver, uri: Uri): String? {
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val mimeType = contentResolver.getType(uri)
        return mimeTypeMap.getExtensionFromMimeType(mimeType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminPostBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Register the BroadcastReceiver
        registerConnectivityBroadcaster()
        // Observing data from the viewModel
        //setting viewModel variables on observe
        observeViewModelVariables()
        setUiFunctionality()
    }

    private fun setUiFunctionality() {
        binding?.apply {
            etPostText.addTextChangedListener {
                checkPublishPossibility()
            }
            etPostTitle.addTextChangedListener {
                checkPublishPossibility()
            }
            viewModel.uploadProgress.observe(viewLifecycleOwner){progress ->
                updateLinearProgressIndicator(progress)
            }
            binding?.btnPublish?.setOnClickListener {
                showLinearProgressIndicatorDialog()
                retrievePost {post ->
                    if (post != null) {
                        viewModel.publishPost(post, viewModel.attachList.value ?: mutableListOf())
                    } else {
                        Toast.makeText(requireContext(), "null", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            binding?.btnAddAttachment?.setOnClickListener {
                openFilePicker()
            }

            binding?.btnAddFlair?.setOnClickListener {
                val bottomSheetDialogFrag = FlairBottomSheetFragment()
                bottomSheetDialogFrag.show(parentFragmentManager, "Flair bottom sheet dialog")
            }
            btnCancelResponse.setOnClickListener {
                llRespondToRequest.visibility = View.GONE
            }
        }
    }

    private fun observeViewModelVariables() {
        viewModel.flairList.observe(viewLifecycleOwner){ flairsList ->
            if (flairsList != null) {
                binding?.hsvFlairsSv?.removeAllViews()
                for(flair in flairsList){
                    addToFlairsView(flair)
                }
            }
        }
        viewModel.attachList.observe(viewLifecycleOwner){ attachList ->
            if (attachList != null) {
                updatePickedFilesHv(attachList)
            }
        }
        viewModel.publishPostResult.observe(viewLifecycleOwner) { result ->
            if (result.second) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.publication_envoy_e),
                    Toast.LENGTH_SHORT
                )
                    .show()
                hideLinearProgressIndicatorDialog()
                checkPublishPossibility()
                goToFeedFrag()
            } else {
                hideLinearProgressIndicatorDialog()
                checkPublishPossibility()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.la_publication_n_a_pas_t_envoy_e),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun registerConnectivityBroadcaster() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, intentFilter)
        // Set the ConnectivityChangeListener
        networkChangeReceiver.setConnectivityChangeListener(this@AdminCreatePostFragment)
    }

    private fun addToFlairsView(flairText : String) {
        val flair = FlairView(requireContext(), null)
        flair.setText(flairText)
        binding?.hsvFlairsSv?.addView(flair)
    }

    private fun goToFeedFrag() {
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.vp_admin_main)
        viewPager.currentItem = 0
        onDestroy()
    }

    private fun checkPublishPossibility() {
        binding?.apply {
            btnPublish.isEnabled = etPostText.text.toString().length >= 8 && etPostTitle.text.toString().length >= 10
        }
    }

    private fun openFilePicker() {
        val intent = getFileChooserIntent()
        filePickerLauncher.launch(intent)
    }

    private fun retrievePost(callback : (Post?)->Unit){
        if (binding?.etPostTitle?.text.toString()
                .isNotBlank() && binding?.etPostText?.text.toString().isNotBlank()
        ) {
            val ownerId = viewModel.getUserId()
            viewModel.getAdminName{ result ->
                    val title = binding?.etPostTitle?.text.toString().trim()
                    val content = binding?.etPostText?.text.toString().trim()
                    val flairsList = viewModel.flairList.value
                    val formattedDate = FormattedTimestampDate().todayTsDate
                if (arguments != null){
                    val respondingToRequest = requireArguments().getBoolean(Constants.RESPONDING_TO_REQUEST)
                    if (respondingToRequest) {
                        @Suppress("DEPRECATION")
                        val request =
                            requireArguments().getParcelable<Request>(Constants.REQUEST_TO_ANSWER_TO)!!
                        callback(
                            Post(
                                ownerId = ownerId,
                                ownerFullName = result,
                                title = title,
                                content = content,
                                repliedToRequest = true,
                                request = request,
                                timestamp = formattedDate,
                                flairsList = flairsList ?: listOf()
                            )
                        )
                    }
                }else{
                    callback(
                        Post(
                        ownerId = ownerId,
                        ownerFullName = result,
                        title = title,
                        content = content,
                        repliedToRequest = false,
                        request = null,
                        timestamp = formattedDate,
                        flairsList =  flairsList ?: listOf()
                    )
                    )
                }

            }

        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.ajouter_un_titre_et_un_text_a_votre_publication),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getFileChooserIntent(): Intent {
        val mimeTypes = arrayOf("image/*", "application/pdf")
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
        if (mimeTypes.isNotEmpty()) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        return intent
    }

    private fun updatePickedFilesHv(attachList: List<Pair<Uri, String>>) {
        binding?.hsvAttachmentsSv?.removeAllViews()
        for (attach in attachList){
            val attachmentView = AttachmentView(requireContext(), null)
            attachmentView.onRemoveClickListener = this
            attachmentView.setText(attach.second)
            binding?.hsvAttachmentsSv?.addView(attachmentView)
        }
    }

    override fun onResume() {
        super.onResume()
        if (arguments != null){
            val respondingToRequest = arguments?.getBoolean(Constants.RESPONDING_TO_REQUEST)
            @Suppress("DEPRECATION")
            val request = requireArguments().getParcelable<Request>(Constants.REQUEST_TO_ANSWER_TO)!!
            if (respondingToRequest != null){
                if (respondingToRequest){
                    binding?.apply {
                        tvRequestOwnerName.text = request.ownerName
                        tvRequestText.text = request.requestText
                        llRespondToRequest.visibility = View.VISIBLE
                        checkPublishPossibility()
                    }
                }
            }else{
                binding?.llRespondToRequest?.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.publishPostResult.removeObservers(viewLifecycleOwner)
        viewModel.uploadProgress.removeObservers(viewLifecycleOwner)
    }

    override fun onRemoveButtonClickListener(position: Int, textView: TextView) {
        binding?.hsvAttachmentsSv?.removeViewAt(position)
        viewModel.removeFromAttachList(textView.text.toString())
    }

    override fun onNetworkConnected() {
        if (mAgreeDialog != null){
            hideAgreeDialog()
        }
    }

    override fun onNetworkDisconnected() {
        binding?.btnPublish?.isEnabled = false
        showAgreeDialog(requireContext(), getString(R.string.probleme_de_connection),
            getString(R.string.veuillez_verifiez), ContextCompat.
            getDrawable(requireContext(), R.drawable.icon_no_internet_connection)
        ) { requireActivity().finishAffinity() }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the BroadcastReceiver
        requireContext().unregisterReceiver(networkChangeReceiver)
    }

}