package com.example.weatherapp.API

import com.example.weatherapp.model.Location

data class GeocodingResponse(
    val results: List<Location>
)
