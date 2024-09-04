package com.example.weatherapp.ui.views.locations

import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Location
import com.example.weatherapp.repositories.DataBaseRepository
import com.example.weatherapp.repositories.WeatherRepository

class LocationsViewModel : ViewModel() {

    private val apiRepository = WeatherRepository.getInstance()
    private val localDBRepository = DataBaseRepository.getInstance()

    val saveLocations = localDBRepository.locations
    val returnApi = apiRepository.locations

    fun searchLocations(inputText: String) {
        apiRepository.fetchLocations(inputText)
    }

    fun clearLocations() {
        apiRepository.clearLocations()
    }

    fun  saveLocation(newLocation: Location) {
        localDBRepository.addLocation(newLocation)
    }

}