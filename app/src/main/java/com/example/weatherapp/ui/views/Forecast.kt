package com.example.weatherapp.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.Utils.getIconResourceId
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.WeatherPeriod
import com.example.weatherapp.ui.theme.Typography
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun Forecast(viewModel: HomeViewModel = viewModel()) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = stringResource(R.string.hourly_forecast),
            style = Typography.bodyMedium,
            color = colorResource(R.color.translucent),
            modifier = Modifier.padding(top = 50.dp, start = 30.dp)
        )

        ShowHourlyForecast(viewModel.weatherPeriod.value)

        Text(
            text = stringResource(R.string.daily_forecast),
            style = Typography.bodyMedium,
            color = colorResource(R.color.translucent),
            modifier = Modifier.padding(top = 50.dp, start = 30.dp)
        )

        ShowDailyForecast(viewModel.forecastForWeek.value)

    }

}

@Composable
fun ShowHourlyForecast(forecast: List<WeatherPeriod>) {
    LazyRow(
        modifier = Modifier
            .padding(start = 15.dp, top = 20.dp, end = 15.dp)
            .fillMaxWidth()
    ) {
        items(forecast) { weather ->
            ItemHourlyForecast(weather)
        }
    }
}

@Composable
fun ItemHourlyForecast(weather: WeatherPeriod) {
    Column(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp)
    ) {
        Text(
            text = weather.time.substring(11, 16),
            style = Typography.labelSmall,
            fontSize = 14.sp,
            color = colorResource(R.color.translucent),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Icon(
            imageVector = ImageVector.vectorResource(weather.getIconResourceId()),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 10.dp)
                .size(32.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = weather.temperature.roundToInt().toString() + stringResource(id = R.string.units_temperature),
            style = Typography.labelSmall,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ShowDailyForecast(dailyForecast: Daily?) {
    LazyRow(
        modifier = Modifier
            .padding(start = 15.dp, top = 20.dp, end = 15.dp)
            .fillMaxWidth()
    ) {
        if (dailyForecast != null) {
            itemsIndexed(dailyForecast.time) { index, time ->
                ItemDailyForecast(
                    time,
                    getIconResourceId(dailyForecast.weatherCode[index]),
                    dailyForecast.maxTemperature[index].roundToInt().toString(),
                    dailyForecast.minTemperature[index].roundToInt().toString()
                )
            }
        }
    }
}

@Composable
fun ItemDailyForecast(time: String, weatherCode: Int, maxTemperature: String, minTemperature: String) {

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(time, inputFormatter)
    val formattedDate = date.format(dateFormatter)

    Column(
        modifier = Modifier.padding(start = 15.dp, end = 15.dp)
    ) {
        Text(
            text = formattedDate,
            style = Typography.labelSmall,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Icon(
            imageVector = ImageVector.vectorResource(weatherCode),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 10.dp)
                .size(32.dp)
                .align(Alignment.CenterHorizontally)
        )
        
        Row(
            modifier = Modifier.padding(top = 10.dp).align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_up),
                contentDescription = "",
                tint = colorResource(id = R.color.translucent),
                modifier = Modifier.padding(end = 2.dp).size(12.dp),
            )
            
            Text(
                text = maxTemperature + stringResource(id = R.string.units_temperature),
                style = Typography.labelSmall,
                fontSize = 12.sp,
                color = colorResource(id = R.color.translucent)
            )
        }

        Row(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_down),
                contentDescription = "",
                tint = colorResource(id = R.color.translucent),
                modifier = Modifier.padding(end = 2.dp).size(12.dp),
            )

            Text(
                text = minTemperature + stringResource(id = R.string.units_temperature),
                style = Typography.labelSmall,
                fontSize = 12.sp,
                color = colorResource(id = R.color.translucent)
            )
        }
    }
}