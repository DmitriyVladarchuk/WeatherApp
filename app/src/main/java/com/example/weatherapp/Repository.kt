package com.example.weatherapp

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.API.GeocodingResponse
import com.example.weatherapp.API.RetrofitClient
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.Location
import com.example.weatherapp.model.WeatherPeriod
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

    private var weatherApi = RetrofitClient.weatherApiService
    private var geocodingApi = RetrofitClient.geocodingApiService

    var inSync: MutableLiveData<Boolean> = MutableLiveData(false)
    val forecastWeather: MutableLiveData<Forecast> = MutableLiveData()
    val locations: MutableLiveData<List<Location>> = MutableLiveData()


    fun fetchWeather() {
        //getCurrentWeather()
        getWeather()
    }

    private fun getWeather() {
        val call = weatherApi.getForecast(45.0448f, 38.976f)

        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    forecastWeather.value = response.body()
                    inSync.value = true

                    Log.d(TAG_API, "Ответ получен")
                    Log.d(TAG_RESULT_API, forecastWeather.value.toString())
                } else {
                    inSync.value = false

                    Log.d(TAG_API,"Ошибка: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                inSync.value = false

                Log.d(TAG_API,"Ошибка: ${t.message}")
            }
        })
    }

    fun fetchLocations(name: String) {
        val call = geocodingApi.searchLocation(name)

        call.enqueue(object : Callback<GeocodingResponse> {
            override fun onResponse(call: Call<GeocodingResponse>, response: Response<GeocodingResponse>) {
                if (response.isSuccessful) {
                    locations.value = response.body()?.results
                    Log.d(TAG_API, "Локации получены")
                } else {
                    Log.d(TAG_API, "Ошибка: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
                Log.d(TAG_API, "Ошибка: ${t.message}")
            }
        })
    }
    
}