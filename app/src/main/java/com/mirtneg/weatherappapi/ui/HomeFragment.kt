package com.mirtneg.weatherappapi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mirtneg.weatherappapi.R
import com.mirtneg.weatherappapi.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel

    val CITY_NAME = "Pristina"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getWeatherDetails(CITY_NAME)
        viewModel.mainData.observe(viewLifecycleOwner) {
            with(it) {
                binding.tempTv.text = kelvinToCelsius(temp)
                binding.maxTempTv.text = "${kelvinToCelsius(temp_max)}°"
                binding.minTempTv.text = "${kelvinToCelsius(temp_min)}°"
                binding.humidityTv.text = "$humidity%"
                binding.pressureTv.text = "${pressure}mb"
            }
        }

        viewModel.nameData.observe(viewLifecycleOwner) {
            binding.cityNameTv.text = it
        }

        viewModel.windData.observe(viewLifecycleOwner) {
            binding.windTv.text = "${(it.speed * 3.6).toInt()}km/h"
        }

        viewModel.descriptionData.observe(viewLifecycleOwner) {
            binding.weatherDescriptionTv.text = it[0].main
            binding.weatherDetailTv.text = it[0].description
        }
    }
    private fun kelvinToCelsius(value: Double): String = ((value - 273.15f).toInt()).toString()
}