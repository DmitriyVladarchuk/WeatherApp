package com.example.weatherapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.API.GeocodingResponse
import com.example.weatherapp.API.RetrofitClient
import com.example.weatherapp.model.Forecast
import com.example.weatherapp.model.ForecastSaveLocation
import com.example.weatherapp.model.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

const val TAG_API = "API"
const val TAG_RESULT_API = "RESULT_API"

class WeatherRepository private constructor() {

    companion object {
        private var INSTANCE: WeatherRepository? = null
        fun getInstance(): WeatherRepository {
            if (INSTANCE == null) {
                INSTANCE = WeatherRepository()
            }
            return INSTANCE ?: throw IllegalStateException("WeatherRepository не инициализирован.")
        }
    }

    private var weatherApi = RetrofitClient.weatherApiService
    private var geocodingApi = RetrofitClient.geocodingApiService

    var inSync: MutableLiveData<Boolean> = MutableLiveData(false)
    val forecastWeather: MutableLiveData<Forecast> = MutableLiveData()
    val locations: MutableLiveData<List<Location>> = MutableLiveData()

    val currentLocation: LiveData<Location> = DataBaseRepository.getInstance().currentLocation
    val forecastSavedLocations: MutableLiveData<List<ForecastSaveLocation>> = MutableLiveData(mutableListOf())
    private val saveLocations: LiveData<List<Location>> = DataBaseRepository.getInstance().locations

    init {
        currentLocation.observeForever { location ->
            location?.let {
                fetchWeatherForLocation(it)
            }
        }

        saveLocations.observeForever { locations ->
//            locations.let {
//                forecastSavedLocations.value = fetchWeatherForSaveLocations(saveLocations.value!!)
//            }
            if (locations != null)
                forecastSavedLocations.postValue(fetchWeatherForSaveLocations(saveLocations.value!!))
        }
    }

    fun fetchWeather() {
        currentLocation.value?.let {
            fetchWeatherForLocation(it)
        }

        saveLocations.value?.let {
            forecastSavedLocations.postValue(fetchWeatherForSaveLocations(saveLocations.value!!))
        }
    }

    private fun fetchWeatherForLocation(location: Location) {
        val call = weatherApi.getForecast(location.latitude, location.longitude)

        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.isSuccessful) {
                    forecastWeather.value = response.body()
                    inSync.value = true

                    Log.d(TAG_API, "Ответ получен")
                    Log.d(TAG_RESULT_API, forecastWeather.value.toString())
                } else {
                    inSync.value = false

                    Log.d(TAG_API, "Ошибка: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                inSync.value = false

                Log.d(TAG_API, "Ошибка: ${t.message}")
            }
        })
    }

    private fun fetchWeatherForSaveLocations(locations: List<Location>): List<ForecastSaveLocation> {
        val list: MutableList<ForecastSaveLocation> = mutableListOf()

        locations.forEach { item ->
            val call = weatherApi.getForecast(item.latitude, item.longitude)

            call.enqueue(object : Callback<Forecast> {
                override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                    if (response.isSuccessful) {
                        val forecast = ForecastSaveLocation(location = item, currentWeather = response.body()!!.current)
                        list.add(forecast)
                    } else {
                        Log.d(TAG_API, "Ошибка: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Forecast>, t: Throwable) {
                    Log.d(TAG_API, "Ошибка: ${t.message}")
                }

            })
        }

        return list
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

    fun clearLocations() {
        locations.value = mutableListOf()
    }
    
}