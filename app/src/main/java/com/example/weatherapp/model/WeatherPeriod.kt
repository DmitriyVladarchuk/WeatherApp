package com.example.weatherapp.model


import com.example.weatherapp.R
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


data class WeatherPeriod(
    val time: String,
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("weather_code") val weatherCode: Int?,
    @SerializedName("relative_humidity_2m") val humidity: Int? = null,
    @SerializedName("precipitation") val precipitation: Double? = null,
    @SerializedName("pressure_msl") val pressure: Double? = null,
    @SerializedName("wind_speed_10m") val windSpeed: Double? = null
) {
    fun getDate(): Long {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val localDateTime = LocalDateTime.parse(time, formatter)
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun getIconResourceId(): Int {
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

    fun getDescriptionResourceId(): Int  {
        return when(weatherCode) {
            0 -> R.string.weather_clear_sky
            1 -> R.string.weather_mainly_clear
            2 -> R.string.weather_variable_cloudiness
            3 -> R.string.weather_overcast
            45 -> R.string.weather_fog
            48 -> R.string.weather_hoarfrost
            51, 53, 55 -> R.string.weather_light_drizzle
            56, 57 -> R.string.weather_drizzle
            61, 63, 65 -> R.string.weather_rain
            66, 67 -> R.string.weather_cold_rain
            71 -> R.string.weather_light_snow
            73 -> R.string.weather_moderate_snow
            75 -> R.string.weather_heavy_snow
            77 -> R.string.weather_snow
            80 -> R.string.weather_light_thunderstorm_rain
            81 -> R.string.weather_moderate_thunderstorm_rain
            82 -> R.string.weather_heavy_thunderstorm_rain
            85 -> R.string.weather_light_snowfall
            86 -> R.string.weather_heavy_snowfall
            95 -> R.string.weather_thunderstorm
            96 -> R.string.weather_thunderstorm_with_hail
            99 -> R.string.weather_thunderstorm_with_heavy_hail
            else -> R.string.error
        }
    }
}
