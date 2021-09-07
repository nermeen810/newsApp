package com.example.newsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private lateinit var retrofit: Retrofit

        init {
            retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getApi(): NewsApi {
            return retrofit.create(NewsApi::class.java)
        }
    }
}