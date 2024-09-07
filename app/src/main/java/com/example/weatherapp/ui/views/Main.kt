package com.example.weatherapp.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Main(navController: NavController, modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {

    val currentLocation by viewModel.currentLocation.observeAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Header(
            currentLocation = currentLocation?.name ?: "N/A",
            clickableLocations = { navController.navigate(Routes.Locations.route) },
            clickableSettings = { navController.navigate(Routes.Settings.route) }
        )

        val verticalPagerState = rememberPagerState { 2 }
        val horizontalPagerState = rememberPagerState { 2 }
        var targetPage by remember { mutableStateOf<Int?>(null) }

        LaunchedEffect(targetPage) {
            targetPage?.let {
                horizontalPagerState.animateScrollToPage(it)
                targetPage = null
            }
        }

        VerticalPager(state = verticalPagerState, Modifier.fillMaxWidth()) { page ->
            if (page == 0)
                Home(viewModel)
            else {

                DetailsHeader(
                    currentPage = horizontalPagerState.currentPage,
                    clickableDetails = { targetPage = 0 },
                    clickableForecast = { targetPage = 1 }
                )

                HorizontalPager(state = horizontalPagerState, modifier = Modifier.fillMaxSize()) { page ->
                    if (page == 0) Details(viewModel) else Forecast(viewModel)
                }
            }
        }
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
fun DetailsHeader(currentPage: Int, clickableDetails: () -> Unit, clickableForecast: () -> Unit) {
    Row(
        modifier = Modifier.padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.details),
            style = Typography.titleLarge,
            fontSize = 24.sp,
            fontWeight = if (currentPage == 0) FontWeight.Bold else FontWeight.Normal,
            color = if (currentPage == 0) colorResource(R.color.content) else colorResource(R.color.translucent),
            modifier = Modifier.clickable {
                clickableDetails()
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.forecast),
            style = Typography.titleLarge,
            fontSize = 24.sp,
            fontWeight = if (currentPage == 1) FontWeight.Bold else FontWeight.Normal,
            color = if (currentPage == 1) colorResource(R.color.content) else colorResource(R.color.translucent),
            modifier = Modifier.clickable {
                clickableForecast()
            }
        )
    }
}