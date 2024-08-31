package com.example.weatherapp.model

import com.example.weatherapp.R
import com.google.gson.annotations.SerializedName

data class Daily(
    val time: List<String>,
    val sunrise: List<String>,
    val sunset: List<String>,
    @SerializedName("weather_code") val weatherCode: List<Int>,
    @SerializedName("temperature_2m_min") val minTemperature: List<Double>,
    @SerializedName("temperature_2m_max") val maxTemperature: List<Double>
)