package com.example.weatherapp.ui.views.locations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(viewModel: LocationsViewModel) {

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = { /*TODO*/ }) {

    }

}