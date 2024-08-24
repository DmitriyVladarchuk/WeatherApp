package com.example.weatherapp

import android.util.Log
import com.example.weatherapp.API.RetrofitClient
import com.example.weatherapp.model.Forecast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

const val TAG_API = "API"
const val TAG_RESULT_API = "RESULT_API"

class Repository private constructor() {

    companion object {
        private var INSTANCE: Repository? = null
        fun getInstance(): Repository {
            if (INSTANCE == null) {
                INSTANCE = Repository()
            }
            return INSTANCE ?: throw IllegalStateException("Repository не инициализирован.")
        }
    }

    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

    fun onDestroy() {
        myCoroutineScope.cancel()
    }

    private var apiService = RetrofitClient.weatherApiService

    fun getWeather() {
        val call = apiService.getForecast(45.0448f, 38.976f, "temperature_2m,weather_code", "GMT", 1)

        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    Log.d(TAG_API, "Ответ получен")
                    //currentWeather.value = response.body()
                    //Log.d(TAG_API, "Температура ${currentWeather.value!!.main.temp}")

                    val testData: Forecast? = response.body()
                    Log.d(TAG_RESULT_API, testData.toString())
                } else {
                    Log.d(TAG_API,"Ошибка: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d(TAG_API,"Ошибка: ${t.message}")
            }
        })
    }
}