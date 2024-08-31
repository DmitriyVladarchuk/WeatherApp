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
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Main(navController: NavController, modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Header(
            currentLocation = viewModel.currentLocation,
            clickableLocations = { navController.navigate(Routes.Locations.route) },
            clickableSettings = { navController.navigate(Routes.Settings.route) }
        )

        val pagerState = rememberPagerState { 2 }

        VerticalPager(state = pagerState, Modifier.fillMaxWidth()) { page ->
            if (page == 0)
                Home(viewModel)
            else {

                // Здесь создовать Pager и state

                Details(viewModel)
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