package com.mirtneg.weatherappapi.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mirtneg.weatherappapi.R
import com.mirtneg.weatherappapi.data.models.Weather
import com.mirtneg.weatherappapi.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeFragment.apply {
            setOnRefreshListener {
                fetchWeatherDataAndPopulateUI("Pristina")
                isRefreshing = false
            }

        }

        fetchWeatherDataAndPopulateUI("Pristina")

        binding.cityNameTv.setOnLongClickListener { cityname ->
            cityname as TextView
            when (cityname.text) {
                "Pristina" -> {
                    fetchWeatherDataAndPopulateUI("Japan")
                }

                "Japan" -> {
                    fetchWeatherDataAndPopulateUI("Pristina")
                }
            }
            true
        }
    }

    private fun fetchWeatherDataAndPopulateUI(countryName: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                showProgressBarAndHideViews(false)
                delay(100)
                val currentWeather = viewModel.getWeatherDetails(countryName)
                populateUIWithData(currentWeather!!)
                val currentTime = Calendar.getInstance().time
                determineWetherDayOrNight(
                    currentTime,
                    if (countryName == "Pristina") Locale.getDefault() else Locale(
                        countryName,
                        countryName
                    )
                )
                showProgressBarAndHideViews(true)
            } catch (e: Exception) {
                Log.e("Error while fetching weather data", "fetchWeatherDataAndPopulateUI: $e", e)
                binding.connectionFailed.apply {
                    alpha = 0f
                    isVisible = true
                    animate().alpha(1f).duration = 500
                }
                binding.progressBar.isVisible = false
                binding.retryConnectionButton.apply {
                    alpha = 0f
                    isVisible = true
                    animate().alpha(1f).duration = 500
                    setOnClickListener {
                        fetchWeatherDataAndPopulateUI(countryName)
                    }
                }
            }
        }
    }

    private fun determineWetherDayOrNight(date: Date?, countryCode: Locale) {
        val formatter = SimpleDateFormat("HH:mm", countryCode)
        val timeFormatted = formatter.format(date)
        val timeToInt = timeFormatted.removeRange(2, timeFormatted.length).toInt()
        if (timeToInt < 6 || timeToInt > 18) {
            binding.weatherImageIv.setImageResource(R.drawable.moon)
            binding.homeFragment.setBackgroundResource(R.drawable.ic_rec2)
        } else {
            binding.weatherImageIv.setImageResource(R.drawable.ic_image_partlycloudy)
            binding.homeFragment.setBackgroundResource(R.drawable.ic_rec1)
        }
        Log.d("Formatted Time", "determineWetherDayOrNight: $timeToInt")
    }

    private fun populateUIWithData(weather: Weather) {
        binding.apply {
            weather.let { weather ->
                cityNameTv.text = weather.name
                tempTv.text = kelvinToCelsius(weather.main.temp)
                maxTempTv.text = kelvinToCelsius(weather.main.temp_max)
                minTempTv.text = kelvinToCelsius(weather.main.temp_min)
                humidityTv.text = "${weather.main.humidity}%"
                pressureTv.text = "${weather.main.pressure}mm"
                windTv.text = "${weather.wind.speed}m/s"
                weatherDescriptionTv.text = weather.weather.first().main
                weatherDetailTv.text = weather.weather.first().description
            }
        }
    }

    private fun showProgressBarAndHideViews(isCompleted: Boolean) {
        binding.apply {
            progressBar.isVisible = !isCompleted
            cityNameTv.isVisible = isCompleted
            weatherImageIv.isVisible = isCompleted
            weatherDescriptionTv.isVisible = isCompleted
            weatherDetailTv.isVisible = isCompleted
            tempTv.isVisible = isCompleted
            celsiusTv.isVisible = isCompleted
            weatherLogoTv.isVisible = isCompleted
            maxTempTv.isVisible = isCompleted
            minTempTv.isVisible = isCompleted
            linearLayoutMaxMinTemp.isVisible = isCompleted
            linearLayoutWindHumidityPrecipitation.isVisible = isCompleted
        }
    }
}

private fun kelvinToCelsius(value: Double): String = ((value - 273.15f).toInt()).toString()

