package com.mirtneg.weatherappapi.data.service

import com.mirtneg.weatherappapi.data.models.Main
import com.mirtneg.weatherappapi.data.models.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/weather")
    fun getWeatherData(@Query("q") cityName: String, @Query("appid") apiKey:String): Call<Weather>

}