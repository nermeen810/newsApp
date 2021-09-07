package com.example.newsapp.repo

import com.example.newsapp.model.Article

interface RemoteInterface {
    suspend fun fetchNewsData(country: String, apiKey: String): List<Article>?
}