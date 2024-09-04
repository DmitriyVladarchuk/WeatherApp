package com.example.weatherapp

import android.app.Application
import android.content.Context
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

        WeatherRepository.getInstance().fetchLocations("Berlin")
    }

}