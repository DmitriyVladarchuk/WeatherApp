package com.example.weatherapp.ui.views.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.Typography

@Composable
fun AboutApp(navController: NavController, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
    ) {

        HeaderSettings(text = stringResource(id = R.string.about_app)) {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                //.padding(top = 30.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.about_app_description_one),
                style = Typography.bodyMedium,
                modifier = Modifier.padding(bottom = 5.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(id = R.string.about_app_description_two),
                style = Typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

    }

}