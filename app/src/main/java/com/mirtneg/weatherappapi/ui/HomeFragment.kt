package com.mirtneg.weatherappapi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mirtneg.weatherappapi.R
import com.mirtneg.weatherappapi.data.models.Weather
import com.mirtneg.weatherappapi.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
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

        fetchWeatherDataAndPopulateUI("Pristina")
    }

    private fun fetchWeatherDataAndPopulateUI(countryName : String) = viewLifecycleOwner.lifecycleScope.launch {
        val currentWeather = viewModel.getWeatherDetails(countryName)
        populateUIWithData(currentWeather!!)
        val currentTime = Calendar.getInstance().time
        determineWetherDayOrNight(currentTime)
    }
    private fun determineWetherDayOrNight(date : Date?) {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeFormatted = formatter.format(date)
        val timeToInt = timeFormatted.removeRange(2,timeFormatted.length).toInt()
        if(timeToInt < 6 || timeToInt > 18){
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
    private fun kelvinToCelsius(value: Double): String = ((value - 273.15f).toInt()).toString()
}


