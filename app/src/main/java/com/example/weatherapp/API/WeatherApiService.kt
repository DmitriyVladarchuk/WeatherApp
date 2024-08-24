package com.example.weatherapp.API

import com.example.weatherapp.model.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("/v1/forecast")
    fun getForecast(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("hourly") hourly: String = "temperature_2m,weather_code",
        @Query("timezone") timezone: String = "GMT",
        @Query("forecast_days") days: Int = 1
    ): Call<Forecast>
}