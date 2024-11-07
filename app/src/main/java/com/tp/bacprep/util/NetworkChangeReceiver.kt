package com.tp.bacprep.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkChangeReceiver : BroadcastReceiver() {

    private var connectivityChangeListener: ConnectivityChangeListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (isNetworkAvailable(context)) {
            connectivityChangeListener?.onNetworkConnected()
        } else {
            connectivityChangeListener?.onNetworkDisconnected()
        }
    }

    // Function to check if there is an internet connection
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            // for other devices that can connect with Ethernet
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    // Interface to communicate network connectivity changes
    interface ConnectivityChangeListener {
        fun onNetworkConnected()
        fun onNetworkDisconnected()
    }


    // Setter for the ConnectivityChangeListener
    fun setConnectivityChangeListener(listener: NetworkChangeReceiver.ConnectivityChangeListener?) {
        connectivityChangeListener = listener
    }
}