package com.example.weatherapp.ui.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Repository
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.WeatherPeriod
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel : ViewModel() {

    val currentLocation by mutableStateOf("Краснодар")

    val inSync: LiveData<Boolean> = Repository.getInstance().inSync

    val currentDate: Date = Date()

    val forecastWeather: MutableLiveData<Forecast> = Repository.getInstance().weatherToday

    val weatherPeriod: MutableState<List<WeatherPeriod>> = mutableStateOf(emptyList())

    val currentWeather: MutableState<WeatherPeriod?> = mutableStateOf(null)

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {

            forecastWeather.observeForever { forecast ->
                weatherPeriod.value = forecast.hourly.toWeatherPeriods()

                for (weather in weatherPeriod.value) {
                    if (weather.getDate() >= currentDate.time) {
                        currentWeather.value = weather
                        break
                    }
                }
            }

//            Repository.getInstance().weatherToday.observeForever { forecast ->
//                weatherPeriod.value = forecast.hourly.toWeatherPeriods()
//
//                for (weather in weatherPeriod.value) {
//                    if (weather.getDate() >= currentDate.time) {
//                        currentWeather.value = weather
//                        break
//                    }
//                }
//            }
        }
    }

    fun getSunrise(): String {
        return forecastWeather.value?.daily?.sunrise?.first()?.substring(11, 16) ?: "N/A"
    }

    fun getSunset(): String {
        return forecastWeather.value?.daily?.sunset?.last()?.substring(11, 16) ?: "N/A"
    }

}