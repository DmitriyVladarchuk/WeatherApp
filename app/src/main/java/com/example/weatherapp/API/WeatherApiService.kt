package com.example.weatherapp.API

import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.WeatherPeriod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("/v1/forecast")
    fun getForecast(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("hourly") hourly: String = "temperature_2m,weather_code",
        @Query("daily") daily: String = "sunrise,sunset",
        @Query("timezone") timezone: String = "auto",
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,precipitation,weather_code,pressure_msl,wind_speed_10m",
        @Query("forecast_days") days: Int = 1
    ): Call<Forecast>

}