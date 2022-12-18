package com.mirtneg.weatherappapi.repository

import com.mirtneg.weatherappapi.data.service.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    val BASE_URL = "https://api.openweathermap.org"
    val apiService: ApiService

    init {
        val client = OkHttpClient.Builder().build()

        apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}