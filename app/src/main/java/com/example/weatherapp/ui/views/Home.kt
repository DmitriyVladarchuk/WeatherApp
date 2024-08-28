package com.example.weatherapp.ui.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt


@Composable
fun Home(navController: NavController, modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {
    val inSync by viewModel.inSync.observeAsState(false)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Header(
            currentLocation = viewModel.currentLocation,
            clickableLocations = { navController.navigate(Routes.Locations.route) },
            clickableSettings = { navController.navigate(Routes.Settings.route) }
        )
        
        SyncInfo(inSync = inSync)

        TemperatureToday(
            temperature = viewModel.currentWeather.value?.temperature?.roundToInt().toString() ?: "N/A",
            minTemperature = viewModel.weatherPeriod.value.minOfOrNull { it.temperature }?.roundToInt().toString() ?: "N/A",
            maxTemperature = viewModel.weatherPeriod.value.maxOfOrNull { it.temperature }?.roundToInt().toString() ?: "N/A"
        )

        WeatherInfo(
            iconResource = viewModel.currentWeather.value?.getIconResourceId() ?: R.drawable.sun,
            descriptionResource = viewModel.currentWeather.value?.getDescriptionResourceId() ?: R.string.error
        )

        SunsetAndSunrise(
            sunrise = viewModel.getSunrise(),
            sunset = viewModel.getSunset()
        )

        //Text(text = viewModel.currentWeather.value?.time ?: "N/A")
    }
}

@Composable
fun Header(currentLocation: String, clickableLocations: () -> Unit, clickableSettings: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = currentLocation,
                style = Typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.current_location),
                style = Typography.labelSmall,
                color = colorResource(R.color.translucent),
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.map),
            modifier = Modifier
                .size(21.dp)
                .clickable { clickableLocations() },
            contentDescription = "Clickable icon locations",
            tint = colorResource(R.color.translucent)
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.settings),
            modifier = Modifier
                .padding(start = 20.dp)
                .size(21.dp)
                .clickable { clickableSettings() },
            contentDescription = "Clickable icon settings",
            tint = colorResource(R.color.translucent)
        )

    }
}

@Composable
fun SyncInfo(inSync: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (inSync) stringResource(R.string.in_sync) else stringResource(R.string.not_sync),
            style = Typography.labelSmall,
            modifier = Modifier.padding(top = 50.dp),
            color = colorResource(R.color.translucent)
        )

        val currentDate = Date()
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        
        Text(
            text = dateFormat.format(currentDate),
            style = Typography.bodyMedium,
            modifier = Modifier.padding(top = 30.dp),
            color = colorResource(R.color.translucent)
        )
    }
}

@Composable
fun TemperatureToday(temperature: String, minTemperature: String, maxTemperature: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 102.sp)) {
                append(temperature)
            }
            withStyle(style = SpanStyle(fontSize = 48.sp)) {
                append(" °C")
            }
        }

        Text(
            text = text,
            style = Typography.titleLarge
        )

        Row(
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_down),
                tint = colorResource(R.color.translucent),
                modifier = Modifier
                    .size(21.dp),
                contentDescription = "icon min temperature"
            )
            Text(
                text = minTemperature + " °C",
                style = Typography.bodyMedium,
                color = colorResource(R.color.translucent),
                modifier = Modifier.padding(start = 5.dp, end = 20.dp)
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_up),
                tint = colorResource(R.color.translucent),
                modifier = Modifier
                    .size(21.dp),
                contentDescription = "icon max temperature"
            )
            Text(
                text = maxTemperature + " °C",
                style = Typography.bodyMedium,
                color = colorResource(R.color.translucent),
                modifier = Modifier.padding(start = 5.dp)
            )
        }

    }
}

@Composable
fun WeatherInfo(iconResource: Int, descriptionResource: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconResource),
            contentDescription = "Weather icon representing ${stringResource(id = descriptionResource)}",
            modifier = Modifier
                .size(128.dp)
                .padding(bottom = 20.dp)
        )

        Text(
            text = stringResource(id = descriptionResource),
            style = Typography.bodyMedium,
            color = colorResource(R.color.translucent),
        )
    }
}

@Composable
fun SunsetAndSunrise(sunrise: String, sunset: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.sunrise),
            contentDescription = "Sunrise icon representing $sunrise",
            modifier = Modifier
                .padding(end = 10.dp)
                .size(21.dp),
            tint = colorResource(id = R.color.translucent)
        )

        Text(
            text = sunrise,
            style = Typography.bodyMedium,
            color = colorResource(R.color.translucent),
            //modifier = Modifier.padding(start = 10.dp, end = 30.dp)
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.sunset),
            contentDescription = "Sunrise icon representing $sunset",
            modifier = Modifier
                .padding(start = 30.dp, end = 10.dp)
                .size(21.dp),
            tint = colorResource(id = R.color.translucent)
        )

        Text(
            text = sunset,
            style = Typography.bodyMedium,
            color = colorResource(R.color.translucent)
        )
    }
}
