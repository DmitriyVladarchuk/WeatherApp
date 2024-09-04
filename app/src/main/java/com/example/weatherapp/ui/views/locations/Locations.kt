package com.example.weatherapp.ui.views.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.Typography


@Composable
fun Locations(navController: NavController, modifier: Modifier = Modifier,) {

    var stateSearchHeader by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        if (stateSearchHeader)
            SearchHeader()
        else
            HeaderLocations(
                clickableBack = { navController.popBackStack() },
                clickableSearchLocation = { stateSearchHeader = !stateSearchHeader }
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
            modifier = Modifier.clickable { clickableBack() }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                tint = colorResource(id = R.color.translucent),
                modifier = Modifier.size(21.dp),
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
                .clickable { clickableSearchLocation() },
            contentDescription = "Add new location"
        )

    }

}

@Composable
fun SearchHeader() {
    var searchCity by remember{ mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
            tint = colorResource(id = R.color.translucent),
            modifier = Modifier.size(21.dp),
            contentDescription = "Back"
        )

        TextField(
            value = searchCity,
            modifier = Modifier.weight(1f).height(34.dp),
            textStyle = Typography.bodyMedium,
            //contentPadding = PaddingValues(0.dp),
            //colors = TextFieldColors(),
            onValueChange = { newText ->
                searchCity = newText
            }
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.close),
            tint = colorResource(id = R.color.translucent),
            modifier = Modifier
                .size(24.dp)
                .clickable { },
            contentDescription = "Cancel"
        )
    }
}