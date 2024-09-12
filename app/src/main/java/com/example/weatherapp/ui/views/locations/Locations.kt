package com.example.weatherapp.ui.views.locations

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.input.pointer.pointerInput
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
import com.example.weatherapp.ui.views.Routes
import java.util.Locale
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Locations(navController: NavController, modifier: Modifier = Modifier, viewModel: LocationsViewModel = viewModel()) {

    //var stateSearchHeader by remember { mutableStateOf(false) }
    val apiCityList by viewModel.returnApi.observeAsState(mutableListOf())
    //val forecastSavedLocations: List<ForecastSaveLocation> by viewModel.forecastSavedLocations.observeAsState(mutableListOf<ForecastSaveLocation>())
    val forecastSavedLocations: List<ForecastSaveLocation> by viewModel.forecastSavedLocations.observeAsState(emptyList())

    var openDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.asPaddingValues())
    ) {

        HeaderLocations(
            clickableBack = {
                navController.popBackStack()
                viewModel.clearLocations()
            },
            clickableSearchLocation = {
                //stateSearchHeader = !stateSearchHeader
                navController.navigate(Routes.LocationsSearch.route)
            }
        )

        forecastSavedLocations?.let {
            BodySaveLocations(
                locations = it,
                clickableChangeItem = {
                    viewModel.updateLocation(it.location)
                    navController.popBackStack()
                },
                longPress = { location ->
                    isSheetOpen = true

                    viewModel.clickLocations = location
                }
            )
        }

    }
    
    if (isSheetOpen) {
        ModalBottomSheet(sheetState = sheetState, onDismissRequest = { isSheetOpen = false }, containerColor = colorResource(id = R.color.main)) {
            Column(
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                BottomSheetItem(iconRes = R.drawable.info, text = stringResource(id = R.string.info)) {
                    //isSheetOpen = false
                }

                BottomSheetItem(iconRes = R.drawable.delete, text = stringResource(id = R.string.delete), colorRes = R.color.red) {
                    openDialog = true
                    //isSheetOpen = false
                }
            }
        }

        if (openDialog) {
            val buttonColors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main), contentColor = colorResource(id = R.color.content))
            AlertDialog(
                title = {
                    Text(
                        text = stringResource(id = R.string.dialog_title),
                        style = Typography.titleLarge
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.dialog_body),
                        style = Typography.bodyMedium
                    )
                },
                containerColor = colorResource(id = R.color.main),
                titleContentColor = colorResource(id = R.color.translucent),
                textContentColor = colorResource(id = R.color.translucent),
                onDismissRequest = {
                    openDialog = false
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog = false
                            viewModel.deleteLocation()
                            isSheetOpen = false
                        },
                        colors = buttonColors) {
                        Text(text = stringResource(id = R.string.delete), style = Typography.bodyMedium)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog = false
                            isSheetOpen = false
                        }, colors = buttonColors) {
                        Text(text = stringResource(id = R.string.cancel), style = Typography.bodyMedium)
                    }
                }
            )
        }
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
fun BodySaveLocations(locations: List<ForecastSaveLocation>, clickableChangeItem: (ForecastSaveLocation) -> Unit, longPress: (Location) -> Unit) {

    Box(modifier = Modifier.padding(top = 30.dp, start = 30.dp, end = 30.dp)) {
        locations.forEach { item ->
            if (item.location.isSelected)
                ItemSaveLocation(item = item, clickableChangeItem = {}, longPress = {})
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
                clickableChangeItem = { clickableChangeItem(item) },
                longPress = { longPress(item.location) }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemSaveLocation(item: ForecastSaveLocation, clickableChangeItem: (ForecastSaveLocation) -> Unit, longPress: (Location) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .combinedClickable(
                onClick = { clickableChangeItem(item) },
                onLongClick = { longPress(item.location) }
            )
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
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterVertically)
        )

    }

}

@Composable
fun BottomSheetItem(iconRes: Int, text: String, colorRes: Int = R.color.content, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, top = 10.dp, end = 30.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            tint = colorResource(id = colorRes),
            modifier = Modifier
                .padding(end = 20.dp)
                .size(32.dp),
            contentDescription = null
        )
        
        Text(
            text = text,
            style = Typography.bodyMedium,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun DialogDeleteLocation(location: Location, onClick: () -> Unit) {
    //var openDialog by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = {
            //openDialog = false
        },
        confirmButton = {
            Button(onClick = { onClick() }) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Cancel")
            }
        }
    )

}