package com.example.weatherapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.weatherapp.App
import com.example.weatherapp.DataBase.LocalDB
import com.example.weatherapp.model.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class DataBaseRepository private constructor() {

    companion object {
        private var INSTANCE: DataBaseRepository? = null
        fun getInstance(): DataBaseRepository {
            if (INSTANCE == null) {
                INSTANCE = DataBaseRepository()
            }
            return INSTANCE ?: throw IllegalStateException("DataBaseRepository не инициализирован.")
        }
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun onDestroy() {
        coroutineScope.cancel()
    }

    private val localDB by lazy {
        LocalDB.getBataBase(App.context).dao()
    }

    val locations: LiveData<List<Location>> = localDB.getLocations().asLiveData()

    fun addLocation(location: Location) {
        coroutineScope.launch {
            localDB.saveLocation(location)
        }
    }

    fun editLocation(location: Location) {
        coroutineScope.launch {
            localDB.updateLocation(location)
        }
    }

    fun deleteLocation(location: Location) {
        coroutineScope.launch {
            localDB.deleteLocation(location)
        }
    }

    fun clearLocation(location: Location) {
        coroutineScope.launch {
            localDB.deleteAllLocations()
        }
    }

}