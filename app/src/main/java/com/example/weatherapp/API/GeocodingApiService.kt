package com.example.weatherapp.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("/v1/search")
    fun searchLocation(
        @Query("name") name: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json"
    ): Call<GeocodingResponse>
}