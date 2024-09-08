package com.example.weatherapp.ui.views.setting

import androidx.lifecycle.ViewModel
import com.example.weatherapp.repositories.SettingsRepository
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {

    private val settingsRepository = SettingsRepository.getInstance()
    private val _darkMode = settingsRepository.isDarkMode
    val darkMode: StateFlow<Boolean> get() = _darkMode

    fun changeDarkMode() {
        settingsRepository.changeDarkMode()
    }

}