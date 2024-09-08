package com.example.weatherapp.repositories

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

const val TAG_SHARED_PREFERENCES = "shared_preferences"
const val TAG_DARK_MODE = "dark_mode"
const val MODE_DARK = AppCompatDelegate.MODE_NIGHT_YES
const val MODE_LIGHT = AppCompatDelegate.MODE_NIGHT_NO

class SettingsRepository private constructor(){

    companion object {
        private var INSTANCE: SettingsRepository? = null
        fun getInstance(): SettingsRepository {
            if (INSTANCE == null) {
                INSTANCE = SettingsRepository()
            }
            return INSTANCE ?: throw IllegalStateException("SettingsRepository не инициализирован.")
        }
    }

    private val sharedPreferences = App.context.getSharedPreferences(TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    private val _isDarkMode = MutableStateFlow(sharedPreferences.getBoolean(TAG_DARK_MODE, false))
    val isDarkMode: StateFlow<Boolean> get() = _isDarkMode

    fun changeDarkMode() {
        val newMode = !_isDarkMode.value
        val editor = sharedPreferences.edit()
        editor.putBoolean(TAG_DARK_MODE, newMode)
        editor.apply()
        _isDarkMode.value = newMode

        setTheme()
    }

    fun setTheme() {
        if (_isDarkMode.value) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}