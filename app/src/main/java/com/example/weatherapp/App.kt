package com.example.weatherapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.weatherapp.repositories.DataBaseRepository
import com.example.weatherapp.repositories.WeatherRepository

class App : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null
        val context
            get() = applicationContext()
        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        WeatherRepository.getInstance().fetchWeather()
        Log.d("test", DataBaseRepository.getInstance().locations.value?.size.toString())
    }

}