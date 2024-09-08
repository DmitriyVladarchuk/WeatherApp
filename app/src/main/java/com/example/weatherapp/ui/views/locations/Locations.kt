package com.example.weatherapp.ui.views.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.Utils.getDescriptionResourceId
import com.example.weatherapp.Utils.getIconResourceId
import com.example.weatherapp.model.ForecastSaveLocation
import com.example.weatherapp.model.Location
import com.example.weatherapp.ui.theme.Typography
import java.util.Locale
import kotlin.math.roundToInt


@Composable
fun Locations(navController: NavController, modifier: Modifier = Modifier, viewModel: LocationsViewModel = viewModel()) {

    var stateSearchHeader by remember { mutableStateOf(false) }
    val apiCityList by viewModel.returnApi.observeAsState(mutableListOf())
    val forecastSavedLocations: List<ForecastSaveLocation> by viewModel.forecastSavedLocations.observeAsState(mutableListOf<ForecastSaveLocation>())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.asPaddingValues())
    ) {

        if (stateSearchHeader) {
            SearchHeader(
                clickableBack = {
                    navController.popBackStack()
                    viewModel.clearLocations()
                },
                search = { city -> viewModel.searchLocations(city) },
                clear = { viewModel.clearLocations() }
            )

            if (apiCityList != null && apiCityList.isNotEmpty()) {
                BodySearchLocations(locations = apiCityList)  { location ->
                    viewModel.saveLocation(location)
                    navController.popBackStack()
                }
            } else {
                EmptyCityMessage()
            }

        } else {
            HeaderLocations(
                clickableBack = {
                    navController.popBackStack()
                    viewModel.clearLocations()
                },
                clickableSearchLocation = { stateSearchHeader = !stateSearchHeader }
            )

            forecastSavedLocations?.let {
                BodySaveLocations(it) {
                    viewModel.updateLocation(it.location)
                    navController.popBackStack()
                }
            }

        }
    }
}

@Composable
fun EmptyCityMessage() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.enter_location_prompt),
            style = Typography.titleLarge,
            color = colorResource(id = R.color.translucent),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun HeaderLocations(clickableBack: () -> Unit, clickableSearchLocation: () -> Unit) {

    Row(
        modifier = Modifier
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
    ) {
        
        Row(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable { clickableBack() }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                tint = colorResource(id = R.color.translucent),
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(21.dp),
                contentDescription = "Back"
            )

            Text(
                text = stringResource(id = R.string.select_city),
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.translucent),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.add),
            tint = colorResource(id = R.color.translucent),
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
                .clickable { clickableSearchLocation() },
            contentDescription = "Add new location"
        )

    }

}

@Composable
fun BodySaveLocations(locations: List<ForecastSaveLocation>, clickableChangeItem: (ForecastSaveLocation) -> Unit) {

    Box(modifier = Modifier.padding(top = 30.dp, start = 30.dp, end = 30.dp)) {
        locations.forEach { item ->
            if (item.location.isSelected)
                ItemSaveLocation(item = item, clickableChangeItem = {})
        }
    }

    if (locations.isNotEmpty()) {
        Text(
            text = stringResource(id = R.string.other_places),
            style = Typography.bodyMedium,
            color = colorResource(id = R.color.translucent),
            modifier = Modifier.padding(start = 30.dp, top = 10.dp, end = 30.dp, bottom = 10.dp)
        )
    }

    LazyColumn(
        modifier = Modifier
            .padding(top = 10.dp, start = 30.dp, end = 30.dp, bottom = 10.dp)
            .fillMaxSize()
    ) {
        itemsIndexed(locations) { index, item ->
            ItemSaveLocation(
                item = item,
                clickableChangeItem = { clickableChangeItem(item) }
            )

            if (index != locations.lastIndex)
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .height(1.dp)
                    .background(colorResource(id = R.color.translucent))
                )
        }
    }
}

@Composable
fun ItemSaveLocation(item: ForecastSaveLocation, clickableChangeItem: (ForecastSaveLocation) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable { clickableChangeItem(item) }
    ) {

        Column {
            Text(
                text = item.location.name,
                style = Typography.titleLarge,
                fontSize = 24.sp
            )

            Text(
                text = item.currentWeather.temperature.roundToInt().toString() + stringResource(id = R.string.units_temperature),
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.translucent),
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )

            Text(
                text = stringResource(getDescriptionResourceId(item.currentWeather.weatherCode ?: 0)),
                style = Typography.labelSmall,
                fontSize = 14.sp,
                color = colorResource(id = R.color.translucent)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(id = getIconResourceId(item.currentWeather.weatherCode ?: 0)),
            contentDescription = "",
            modifier = Modifier.size(40.dp).align(Alignment.CenterVertically)
        )

    }

}

@Composable
fun SearchHeader(clickableBack: () -> Unit, search: (String) -> Unit, clear: () -> Unit) {
    var searchCity by remember{ mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
            tint = colorResource(id = R.color.translucent),
            modifier = Modifier
                .size(21.dp)
                .clickable { clickableBack() },
            contentDescription = "Back"
        )

        BasicTextField(
            value = searchCity,
            modifier = Modifier
                .weight(1f)
                .height(34.dp)
                .padding(start = 6.dp, top = 0.dp, end = 6.dp)
                .focusRequester(FocusRequester().also { focusRequester ->
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                }),
            textStyle = Typography.bodyMedium.copy(color = colorResource(id = R.color.content)),
            cursorBrush = SolidColor(colorResource(id = R.color.translucent)),
            onValueChange = { inputText ->
                searchCity = inputText.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

                search(inputText)
            }
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.close),
            tint = colorResource(id = R.color.translucent),
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    searchCity = ""

                    clear()
                },
            contentDescription = "Cancel"
        )
    }

}

@Composable
fun BodySearchLocations(locations: List<Location>, clickableSaveItem: (Location) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 10.dp, start = 30.dp, end = 30.dp)
            .fillMaxSize()
    ) {
        itemsIndexed(locations) { index, item ->
            if (item.country != null) {
                ItemSearchLocations(
                    location = item,
                    clickableSaveItem = { clickableSaveItem(item) }
                )

                if (index != locations.lastIndex)
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .height(1.dp)
                        .background(colorResource(id = R.color.translucent))
                    )
            }
        }
    }
}

@Composable
fun ItemSearchLocations(location: Location, clickableSaveItem: (Location) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { clickableSaveItem(location) }
    ) {
        Text(
            text = location.name,
            style = Typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = if (location.admin1 == null) location.country!! else "${location.admin1}, ${location.country}",
            style = Typography.bodyMedium,
            color = colorResource(id = R.color.translucent),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 10.dp),
        )

    }
}