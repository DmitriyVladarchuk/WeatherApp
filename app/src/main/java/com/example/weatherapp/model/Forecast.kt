package com.example.weatherapp.model

data class Forecast(
    val latitude: Float,
    val longitude: Float,
    val timezone: String,
    val hourly: Hourly,
    val daily: Daily
) {
    override fun toString(): String {
        return """
            Forecast(
                Latitude: $latitude
                Longitude: $longitude
                Timezone: $timezone
                Hourly:
                ${hourly.toString().prependIndent("    ")}
            )
        """.trimIndent()
    }
}
