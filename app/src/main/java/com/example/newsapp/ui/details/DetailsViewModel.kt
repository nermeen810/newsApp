package com.example.newsapp.details

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val _application: Application = application


    private var _isOnline: MutableLiveData<Boolean> = MutableLiveData()
    val isOnline: LiveData<Boolean>
        get() = _isOnline

    private var _navToHome: MutableLiveData<Boolean> = MutableLiveData()
    val navToHome: LiveData<Boolean>
        get() = _navToHome

    fun navToHome() {
        _navToHome.value = true
    }

    fun isOnline(): Boolean {
        val connectivityManager =
            _application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}