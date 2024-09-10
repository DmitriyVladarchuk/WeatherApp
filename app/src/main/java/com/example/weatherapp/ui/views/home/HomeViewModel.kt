package com.example.weatherapp.ui.views.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.repositories.WeatherRepository
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.Location
import com.example.weatherapp.model.WeatherPeriod
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class HomeViewModel : ViewModel() {

    val currentLocation: LiveData<Location> = WeatherRepository.getInstance().currentLocation

    val inSync: LiveData<Boolean> = WeatherRepository.getInstance().inSync

    private val forecastWeather: MutableLiveData<Forecast> = WeatherRepository.getInstance().forecastWeather

    val forecastForWeek: MutableState<Daily?> = mutableStateOf(null)

    val weatherPeriod: MutableState<List<WeatherPeriod>> = mutableStateOf(emptyList())

    val currentWeather: MutableState<WeatherPeriod?> = mutableStateOf(null)

    init {
        fetchWeather()
    }

    private fun fetchWeather() {

        viewModelScope.launch {

            forecastWeather.observeForever { forecast ->
                weatherPeriod.value = forecast.getForecastFor24hourly()
                currentWeather.value = forecast.current
                forecastForWeek.value = forecast.daily
            }

        }
    }

    fun getMinTemperatureToday(): String = forecastForWeek.value?.minTemperature?.first()?.roundToInt()?.toString() ?: "N/A"

    fun getMaxTemperatureToday(): String = forecastForWeek.value?.maxTemperature?.first()?.roundToInt()?.toString() ?: "N/A"

    fun getSunrise(): String = forecastForWeek.value?.sunrise?.first()?.substring(11, 16) ?: "N/A"

    fun getSunset(): String = forecastForWeek.value?.sunset?.last()?.substring(11, 16) ?: "N/A"

}