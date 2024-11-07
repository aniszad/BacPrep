package com.tp.bacprep.presentation.fragments.user

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tp.bacprep.R
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.AudioFilesAdapter
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.AudioTypeSelectorRvAdapter
import com.tp.bacprep.databinding.FragmentMediaBinding
import com.tp.bacprep.domain.models.AudioFile
import com.tp.bacprep.presentation.activities.user.AudioPlayerActivity
import com.tp.bacprep.presentation.fragments.BaseFragment
import com.tp.bacprep.presentation.viewmodels.AudioResourcesViewModel
import com.tp.bacprep.util.BounceEdgeEffectFactory
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.NetworkChangeReceiver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AudioResourcesFragment : BaseFragment(), AudioTypeSelectorRvAdapter.AudioTypeSelectedListener,
    AudioFilesAdapter.PlayButtonClickListener, NetworkChangeReceiver.ConnectivityChangeListener {
    private lateinit var binding : FragmentMediaBinding
    private val networkChangeReceiver = NetworkChangeReceiver()
    private val audioResourcesVm : AudioResourcesViewModel by viewModels()
    private lateinit var regularAudioRvAdapter : AudioFilesAdapter
    private lateinit var audioTypeSelectorRvAdapter : AudioTypeSelectorRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMediaBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Register the BroadcastReceiver
        registerConnectivityBroadcastReceiver()

        audioResourcesVm.audioQueryResult.observe(viewLifecycleOwner){ queryResult ->
            if (queryResult!=null){
                setAudiosRv(queryResult)
            }else{
                Log.e("Audio Resources Fragment - line58", "Null Audio Resources List")
            }
            hideProgressDialog()
        }
        audioResourcesVm.audioQueryResult.observe(viewLifecycleOwner){typeQueryResult->
            hideProgressDialog()
            if (typeQueryResult!=null){
                setAudiosRv(typeQueryResult)
            }else{
                Log.e("Audio Resources Fragment - line58", "Null Audio Resources List")
            }
        }
        binding.apply {
            autoTvAudioSearch.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Perform the search action here
                    if (autoTvAudioSearch.text.isNotBlank()){
                        preformSearch(autoTvAudioSearch.text.toString())
                    }else{
                        if (audioResourcesVm.audioQueryResult.value != null){
                            val audioList  : List<AudioFile>? = audioResourcesVm.audioQueryResult.value
                            regularAudioRvAdapter.resetData(audioList)
                        }
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            btnSearch.setOnClickListener {
                if (autoTvAudioSearch.text.isNotBlank()){
                    preformSearch(autoTvAudioSearch.text.toString())
                }else{
                    if (audioResourcesVm.audioQueryResult.value != null){
                        val audioList  : List<AudioFile>? = audioResourcesVm.audioQueryResult.value
                        regularAudioRvAdapter.resetData(audioList)
                    }
                }
            }
        }
    }

    private fun preformSearch(query: String) {
        regularAudioRvAdapter.displayQuery(query)
    }

    override fun onStart() {
        super.onStart()
        showProgressDialog()
        setAudioTypeSelectorRv()
        audioResourcesVm.queryAudioResources(Constants.HISTOIRE_DRIVE_FOLDER_ID)
    }
    private fun setAudioTypeSelectorRv() {
        audioTypeSelectorRvAdapter = AudioTypeSelectorRvAdapter(
            listOf(getString(R.string.histoire),
                getString(R.string.geographie)
            ),
            requireContext()
            )
        audioTypeSelectorRvAdapter.setAudioTypeListener(this@AudioResourcesFragment)
        binding.rvAudioTypeSelector.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvAudioTypeSelector.adapter = audioTypeSelectorRvAdapter
    }
    private fun setAudiosRv(audioResourcesList: List<AudioFile>) {
        requireActivity().runOnUiThread {
            regularAudioRvAdapter = AudioFilesAdapter(
                audioResourcesList
            )
            regularAudioRvAdapter.setPlayButtonClickedListener(this@AudioResourcesFragment)
            binding.rvRegularAudios.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.rvRegularAudios.adapter = regularAudioRvAdapter
            binding.rvRegularAudios.edgeEffectFactory = BounceEdgeEffectFactory()
        }

    }
    override fun onModuleSelected(audioType: String) {
        when(audioType){
            Constants.HISTOIRE->{
                showProgressDialog()
                audioResourcesVm.queryAudioResources(Constants.HISTOIRE_DRIVE_FOLDER_ID)
            }
        }
    }


    override fun onPlayButtonClicked(audioName: String, audioDownloadUrl: String) {
            val intent = Intent(requireActivity(), AudioPlayerActivity::class.java)
            intent.putExtra(Constants.AUDIO_FILE_DOWNLOAD_URL, audioDownloadUrl)
            intent.putExtra(Constants.AUDIO_FILE_NAME, audioName)
            requireActivity().startActivity(intent)
    }

    @Suppress("DEPRECATION")
    private fun registerConnectivityBroadcastReceiver() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(networkChangeReceiver, intentFilter)
        // Set the ConnectivityChangeListener
        networkChangeReceiver.setConnectivityChangeListener(this)
    }

    override fun onNetworkDisconnected() {
        binding.apply {
            rvRegularAudios.visibility = View.GONE
            rvAudioTypeSelector.visibility = View.VISIBLE
        }
    }
    override fun onNetworkConnected() {
        binding.apply {
            rvRegularAudios.visibility = View.VISIBLE
            rvAudioTypeSelector.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver
        requireActivity().unregisterReceiver(networkChangeReceiver)
    }

}