package com.tp.bacprep.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class InternetConnectivity(private val context : Context) {
    val isConnectedToInternet : Boolean get() = ConnectedToInternetCheck()

    private fun ConnectedToInternetCheck() : Boolean {
        val connectivityManager  = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager != null){
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        }
        return false
    }

}