package com.example.newsapp.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.model.Article
import com.example.newsapp.repo.NewsRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _application: Application = application
    var ModelRepository: NewsRepo = NewsRepo(application)

    private var _articles: MutableLiveData<List<Article>> = MutableLiveData()
    val articles: LiveData<List<Article>>
        get() = _articles

    private var _isOnline: MutableLiveData<Boolean> = MutableLiveData()
    val isOnline: LiveData<Boolean>
        get() = _isOnline

    private var _navToDetails: MutableLiveData<Article> = MutableLiveData()
    val navToDetails: LiveData<Article>
        get() = _navToDetails


    fun fetchData() {
        val exceptionHandlerException = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            Log.i("id", "exception")
        }
        CoroutineScope(Dispatchers.IO + exceptionHandlerException).launch {
            isOnline
            if (_isOnline.value == true) {
                val result = ModelRepository.fetchNewsData()
                _articles.postValue(result)
                _isOnline.postValue(true)

            } else {
                _isOnline.postValue(false)

            }
        }

    }

    fun navToDetails(article: Article) {
        _navToDetails.value = article
    }

    fun isOnline() {
        val connectivityManager =
            _application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    _isOnline.value = true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    _isOnline.value = true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    _isOnline.value = true
                }
            }
        }
        _isOnline.value = false
    }
}