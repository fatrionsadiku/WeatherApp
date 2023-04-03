package com.mirtneg.weatherappapi.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirtneg.weatherappapi.data.models.Main
import com.mirtneg.weatherappapi.data.models.Weather
import com.mirtneg.weatherappapi.data.models.WeatherX
import com.mirtneg.weatherappapi.data.models.Wind
import com.mirtneg.weatherappapi.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.lang.Exception

class HomeViewModel : ViewModel() {
    private val repository = Repository()

    private val apiKey = "42d8e86fdaeba1be7605f429eb29df7d"

    suspend fun getWeatherDetails(cityName: String): Weather? {
        return try {
            val weatherCall = repository.apiService.getWeatherData(cityName, apiKey)
            val weatherResponse = weatherCall.awaitResponse()
            if (weatherResponse.isSuccessful) {
                weatherResponse.body()
            } else {
                throw Exception("Failed to fetch weather data")
            }
        } catch (e: Exception) {
            Log.e("WeatherApi", "Error fetching weather data", e)
            null
        }
    }
}


















