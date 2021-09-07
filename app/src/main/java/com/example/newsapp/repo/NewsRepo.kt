package com.example.newsapp.repo

import android.app.Application
import com.example.newsapp.api.Constants
import com.example.newsapp.model.Article


class NewsRepo(application: Application) {
    private lateinit var remoteDataSource: RemoteDataSource

    init {
        remoteDataSource = RemoteDataSource()
    }

    suspend fun fetchNewsData(): List<Article>? {
        return remoteDataSource.fetchNewsData(Constants.country, Constants.apiKey)
    }

}