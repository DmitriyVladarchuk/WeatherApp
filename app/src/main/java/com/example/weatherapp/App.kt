package com.example.weatherapp

import android.app.Application
import android.content.Context

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

        Repository.getInstance().fetchWeather()
    }

}