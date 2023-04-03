package com.mirtneg.weatherappapi.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.mirtneg.weatherappapi.data.service.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.O)
class Repository {
    val BASE_URL = "https://api.openweathermap.org"
    val apiService: ApiService

    init {
        val client = OkHttpClient.Builder()
            .readTimeout(Duration.ofSeconds(5))
            .connectTimeout(Duration.ofSeconds(5))
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}