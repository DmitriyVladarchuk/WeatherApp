package com.example.weatherapp.ui.views.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.Typography


@Composable
fun Settings(navController: NavController, modifier: Modifier = Modifier, viewModel: ThemeViewModel = viewModel()) {

    val darkMode by viewModel.darkMode.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Settings")

        ItemTheme(text = "Dark mode", isSelected = darkMode) {
            if (!darkMode) viewModel.changeDarkMode()
        }

        ItemTheme(text = "Light Mode", isSelected = !darkMode) {
            if (darkMode) viewModel.changeDarkMode()
        }
    }
}

@Composable
fun ItemTheme(text: String, isSelected: Boolean, clickable: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp)
            .clickable { clickable() }
    ) {
        Text(
            text = text,
            style = Typography.bodyMedium,
            color = colorResource(id = R.color.translucent),
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isSelected)
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.add),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(24.dp),
                tint = colorResource(id = R.color.content),
                contentDescription = ""
            )
    }
}