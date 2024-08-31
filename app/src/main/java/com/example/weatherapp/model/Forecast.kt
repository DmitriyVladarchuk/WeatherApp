package com.example.weatherapp.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

data class Forecast(
    val latitude: Float,
    val longitude: Float,
    val timezone: String,
    val hourly: Hourly,
    val daily: Daily,
    val current: WeatherPeriod
) {
    override fun toString(): String {
        return """
            Forecast(
                Latitude: $latitude
                Longitude: $longitude
                Timezone: $timezone
                Hourly:
                ${hourly.toString().prependIndent("    ")}
            )
        """.trimIndent()
    }

    fun getForecastFor24hourly(): List<WeatherPeriod> {

        val list: MutableList<WeatherPeriod> = mutableListOf()

        val currentTime = getCurrentTime()

        val startIndex = hourly.time.indexOfFirst { it.substring(11, 16) >= currentTime }

        if (startIndex == -1) {
            println("No forecast data available for the current time")
            return list
        }

        for (index in startIndex..< startIndex+24) {
            val weatherPeriod = WeatherPeriod(
                hourly.time[index],
                hourly.temperature[index].toDouble(),
                hourly.weatherCode[index].toInt()
            )
            list.add(weatherPeriod)
        }

        return list
    }


    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm")

        return timeFormat.format(Date())
    }
}
