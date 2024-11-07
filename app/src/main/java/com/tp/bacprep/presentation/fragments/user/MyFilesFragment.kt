package com.tp.bacprep.presentation.fragments.user

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tp.bacprep.R
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.RecentFilesRvAdapter
import com.tp.bacprep.databinding.FragmentMyFilesBinding
import com.tp.bacprep.presentation.activities.user.AverageCalculatorActivity
import com.tp.bacprep.presentation.activities.user.FilesActivity
import com.tp.bacprep.presentation.fragments.BaseFragment
import com.tp.bacprep.presentation.viewmodels.MyFilesViewModel
import com.tp.bacprep.util.BounceEdgeEffectFactory
import com.tp.bacprep.util.Constants
import com.tp.bacprep.util.NetworkChangeReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log10
import kotlin.math.pow


@AndroidEntryPoint
class MyFilesFragment : BaseFragment(), NetworkChangeReceiver.ConnectivityChangeListener {
    private var binding : FragmentMyFilesBinding? = null
    private var recentFilesRvAdapter :  RecentFilesRvAdapter? = null
    private val networkChangeReceiver = NetworkChangeReceiver()
    private val myFilesVm : MyFilesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyFilesBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Register the BroadcastReceiver
        registerConnectivityBroadcastReceiver()
        setViewModelObservers()
        setFunctionality()
    }

    private fun setFunctionality() {
        binding?.apply {
            cardDownloads.setOnClickListener {
                val intent = Intent(requireActivity(), FilesActivity::class.java)
                intent.putExtra(Constants.FILE_MANAGER_TYPE, Constants.DOWNLOADS)
                startActivity(intent)
            }
            cardSubjectsBank.setOnClickListener {
                val intent = Intent(requireActivity(), FilesActivity::class.java)
                intent.putExtra(Constants.FILE_MANAGER_TYPE, Constants.FILES_BANK)
                startActivity(intent)
            }
            cardBookmarks.setOnClickListener {
                val intent = Intent(requireActivity(), FilesActivity::class.java)
                intent.putExtra(Constants.FILE_MANAGER_TYPE, Constants.BOOKMARKS)
                startActivity(intent)
            }
            cardResources.setOnClickListener {
                val intent = Intent(requireActivity(), FilesActivity::class.java)
                intent.putExtra(Constants.FILE_MANAGER_TYPE, Constants.RESOURCES)
                startActivity(intent)
            }
            btnContribute.setOnClickListener {
                showContributeDialog()
            }
            cardAverageCalculator.setOnClickListener {
                startActivity(Intent(requireActivity(), AverageCalculatorActivity::class.java))
            }
        }
    }

    private fun setViewModelObservers() {
        myFilesVm.apply {
            downloadsCountAndSize.observe(viewLifecycleOwner){filesCountSize ->
                requireActivity().runOnUiThread {
                    binding?.apply {
                        tvDownloadsFileCount.text = filesCountSize.first.toString()
                        tvDownloadsFilesSize.text = getFileSize(filesCountSize.second)
                    }
                }
            }
            subjectsBankCountAndSize.observe(viewLifecycleOwner){filesCountSize ->
                requireActivity().runOnUiThread {
                    binding?.apply {
                        tvSubjectsBankFileCount.text = filesCountSize.first.toString()
                        tvSubjectsFilesSize.text = getFileSize(filesCountSize.second)
                    }
                }
            }
            bookmarksCount.observe(viewLifecycleOwner){ bookmarksCount->
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), bookmarksCount.toString(), Toast.LENGTH_SHORT).show()
                    binding?.apply {
                        tvBookmarksCount.text = bookmarksCount.toString()
                    }
                }
            }
        }
    }

    private fun showContributeDialog() {
        showAgreeDialog(
            requireContext(),
            getString(R.string.contribuer),
            "Voulez-vous contribuer avec des ressources? vous pouvez faire çca en l'envoyant a notre adresse email : ",
            ContextCompat.getDrawable(requireContext(), R.drawable.icon_info_circle),
        ){sendContributionEmail()}
    }

    @Suppress("DEPRECATION")
    private fun registerConnectivityBroadcastReceiver() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(networkChangeReceiver, intentFilter)
        // Set the ConnectivityChangeListener
        networkChangeReceiver.setConnectivityChangeListener(this@MyFilesFragment)
    }

    override fun onStart() {
        super.onStart()
        myFilesVm.getDownloadedFileCountAndSize(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: "")
        myFilesVm.getSubjectsBankCountAndSize(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path ?: "")
        myFilesVm.getBookmarksCount()
        setRecentFilesRv()
    }

    private fun sendContributionEmail(){
        val recepientEmail = "aniszad46@gmail.com" // either set to destination email or leave empty

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$recepientEmail")

        // Check if there are apps that can handle this intent
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            // Start the email composition activity
            startActivity(intent)
        } else {
            // No app can handle this intent, show a message to the user
            Toast.makeText(requireContext(), getString(R.string.no_email_app_found), Toast.LENGTH_SHORT).show()
        }
    }


    private fun getFileSize(fileSize: Long): String {
        if (fileSize <= 0) {
            return "0 B"
        }
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(fileSize.toDouble()) / log10(1024.0)).toInt()
        return "%.1f %s".format(fileSize / 1024.0.pow(digitGroups.toDouble()), units[digitGroups])
    }

    private fun setRecentFilesRv() {
        binding?.apply {
            recentFilesRvAdapter = RecentFilesRvAdapter(requireContext(), listOf())
            rvRecentOpenedFiles.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvRecentOpenedFiles.adapter = recentFilesRvAdapter
            rvRecentOpenedFiles.edgeEffectFactory = BounceEdgeEffectFactory()
        }
    }

    override fun onResume() {
        super.onResume()
        myFilesVm.getMyRecentFiles { recentOpenedFiles->
            if (recentOpenedFiles.size > 20){
                myFilesVm.deleteLastSaved(recentOpenedFiles[0].name)
                recentOpenedFiles.removeAt(0)
            }
            requireActivity().runOnUiThread {
                (recentFilesRvAdapter as RecentFilesRvAdapter).setData(recentOpenedFiles.reversed())
            }
        }
    }

    override fun onNetworkDisconnected() {
        binding?.apply {
            binding?.cardSubjectsBank?.isClickable =  false
            binding?.cardResources?.isClickable =  false
        }
    }


    override fun onNetworkConnected() {
        binding?.cardSubjectsBank?.isClickable =  true
        binding?.cardResources?.isClickable =  true
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver
        requireActivity().unregisterReceiver(networkChangeReceiver)
    }

}