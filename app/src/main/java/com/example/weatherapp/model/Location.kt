package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey val name: String,
    val latitude: Float,
    val longitude: Float,
    val country: String
)
