package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.views.locations.Locations
import com.example.weatherapp.ui.views.Main
import com.example.weatherapp.ui.views.Routes
import com.example.weatherapp.ui.views.setting.Settings
import com.example.weatherapp.ui.views.setting.ThemeViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WeatherAppTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Routes.Home.route) {

                        composable(Routes.Home.route) {
                            Main(navController, Modifier.padding(innerPadding))
                        }
                        composable(Routes.Locations.route) { Locations(navController, Modifier.padding(innerPadding)) }
                        composable(Routes.Settings.route) { Settings(navController, Modifier.padding(innerPadding)) }

                    }
                }
            }
        }
    }
}