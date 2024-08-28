package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Hourly(
    val time: List<String>,
    @SerializedName("temperature_2m") val temperature: List<String>,
    @SerializedName("weather_code") val weatherCode: List<String>
) {
    override fun toString(): String {
        return """
            Hourly(
                Time: ${time.joinToString(", ")}
                Temperature: ${temperature.joinToString(", ")}
                Weather Code: ${weatherCode.joinToString(", ")}
            )
        """.trimIndent()
    }

    fun toWeatherPeriods(): List<WeatherPeriod> {
        return time.indices.map { index ->
            val timeString = time[index]
            val temp = temperature[index].toDoubleOrNull() ?: 0.0
            val code = weatherCode[index].toIntOrNull() ?: 0
            WeatherPeriod(timeString, temp, code)
        }
    }
}
