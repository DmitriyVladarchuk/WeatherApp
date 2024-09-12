package com.example.weatherapp.ui.views

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object Locations : Routes("locations")
    object LocationsSearch : Routes("locations_search")
    object Settings : Routes("settings")
    object AboutApp : Routes("about_app")

}