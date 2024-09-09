package com.example.weatherapp.ui.views.locations

import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Location
import com.example.weatherapp.repositories.DataBaseRepository
import com.example.weatherapp.repositories.WeatherRepository
import com.example.weatherapp.ui.views.Routes

class LocationsViewModel : ViewModel() {

    private val apiRepository = WeatherRepository.getInstance()
    private val localDBRepository = DataBaseRepository.getInstance()

    lateinit var clickLocations: Location
    val returnApi = apiRepository.locations
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
    }

}