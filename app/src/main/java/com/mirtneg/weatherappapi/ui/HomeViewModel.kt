package com.mirtneg.weatherappapi.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mirtneg.weatherappapi.data.models.Main
import com.mirtneg.weatherappapi.data.models.Weather
import com.mirtneg.weatherappapi.data.models.WeatherX
import com.mirtneg.weatherappapi.data.models.Wind
import com.mirtneg.weatherappapi.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val repository = Repository()
    val mainData: MutableLiveData<Main> = MutableLiveData<Main>()
    val nameData: MutableLiveData<String> = MutableLiveData<String>()
    val windData = MutableLiveData<Wind>()
    val descriptionData: MutableLiveData<List<WeatherX>> = MutableLiveData<List<WeatherX>>()

    private val apiKey = "63469917233f9cacb4fc47652cea06d8"

    fun getWeatherDetails(cityName: String) {
        repository.apiService.getWeatherData(cityName, apiKey)
            .enqueue(object : Callback<Weather> {
                override fun onResponse(
                    call: Call<Weather>,
                    response: Response<Weather>
                ) {
                    mainData.postValue(response.body()?.main)
                    nameData.postValue(response.body()?.name)
                    windData.postValue(response.body()?.wind)
                    descriptionData.postValue(response.body()?.weather)
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}