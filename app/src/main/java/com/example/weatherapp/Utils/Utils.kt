package com.example.weatherapp.Utils

import com.example.weatherapp.R

fun getIconResourceId(weatherCode: Int): Int {
    return when(weatherCode) {
        0 -> R.drawable.sun
        1, 2 -> R.drawable.partly_cloudy_day
        3 -> R.drawable.cloud
        45, 48 -> R.drawable.mist
        51, 53, 55 -> R.drawable.cloud_drizzle
        56, 57 -> R.drawable.cloud_drizzle
        61, 63, 65 -> R.drawable.cloud_rain
        66, 67 -> R.drawable.cloud_rain
        80, 81, 82 -> R.drawable.cloud_rain
        71, 73, 75, 77 -> R.drawable.cloud_snow
        85, 86 -> R.drawable.cloud_snow
        95, 96, 99 -> R.drawable.cloud_lightning
        else -> { R.drawable.sun }
    }
}