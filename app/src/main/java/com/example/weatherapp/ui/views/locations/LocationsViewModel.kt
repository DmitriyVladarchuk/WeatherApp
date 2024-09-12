package com.example.weatherapp.ui.views.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.model.Location
import com.example.weatherapp.repositories.DataBaseRepository
import com.example.weatherapp.repositories.WeatherRepository
import com.example.weatherapp.ui.views.Routes
import kotlinx.coroutines.launch

class LocationsViewModel : ViewModel() {

    private val apiRepository = WeatherRepository.getInstance()
    private val localDBRepository = DataBaseRepository.getInstance()

    lateinit var clickLocations: Location
    val returnApi = apiRepository.locations
    val saveLocation = localDBRepository.locations
    val forecastSavedLocations = apiRepository.forecastSavedLocations

    fun searchLocations(inputText: String) {
        apiRepository.fetchLocations(inputText)
    }

    fun clearLocations() {
        apiRepository.clearLocations()
    }

    fun  saveLocation(newLocation: Location) {
        localDBRepository.addLocation(newLocation)
    }

    fun updateLocation(location: Location) {
        localDBRepository.editLocation(location)
    }

    fun deleteLocation() {
        localDBRepository.deleteLocation(clickLocations)
        //apiRepository.fetchWeather()
        saveLocation.observeForever {
            apiRepository.fetchWeather()
        }
    }

//    init {
//        viewModelScope.launch {
//            saveLocation.observeForever {
//                apiRepository.fetchWeather()
//            }
//        }
//    }

}