package com.example.newsapp.repo


import androidx.lifecycle.MutableLiveData
import com.example.newsapp.api.ApiClient
import com.example.newsapp.model.Article


class RemoteDataSource : RemoteInterface {

    var newsLiveData: MutableLiveData<List<Article>> = MutableLiveData()

    override suspend fun fetchNewsData(
        country: String,
        apiKey: String
    ): List<Article>? {

        val response = ApiClient.getApi().getNews(country, apiKey)
        try {
            if (response.isSuccessful) {
                return response.body()?.articles
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return null
    }
}