package com.example.weatherapp.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.Typography

@Composable
fun Details(viewModel: HomeViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, top = 35.dp)
    ) {

        Text(
            text = stringResource(R.string.details),
            style = Typography.titleLarge,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 30.dp, bottom = 10.dp),
            fontWeight = FontWeight.Bold
        )

        TextInfo(
            title = stringResource(R.string.precipitation),
            content = viewModel.currentWeather.value?.precipitation.toString() + " " + stringResource(R.string.units_precipitation)
        )

        TextInfo(
            title = stringResource(id = R.string.se_wind),
            content = viewModel.currentWeather.value?.windSpeed.toString() + " " + stringResource(R.string.units_wind_speed)
        )

        TextInfo(
            title = stringResource(id = R.string.humidity),
            content = viewModel.currentWeather.value?.humidity.toString() + " " + stringResource(R.string.units_humidity)
        )

        TextInfo(
            title = stringResource(id = R.string.pressure),
            content = viewModel.currentWeather.value?.pressure.toString() + " " + stringResource(R.string.units_pressure_msl)
        )
        
    }
}

@Composable
fun TextInfo(title: String, content: String) {
    Text(
        text = title,
        style = Typography.labelSmall,
        fontSize = 14.sp,
        color = colorResource(id = R.color.translucent),
        modifier = Modifier.padding(top = 20.dp)
    )

    Text(
        text = content,
        style = Typography.bodyMedium,
        color = colorResource(id = R.color.content),
        modifier = Modifier.padding(top = 5.dp)
    )
}