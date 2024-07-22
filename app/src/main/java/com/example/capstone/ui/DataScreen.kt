package com.example.capstone.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capstone.viewmodel.DataViewModel
import com.example.capstone.viewmodel.UiState
import kotlinx.coroutines.launch

@Composable
fun DataScreen(
    dataViewModel: DataViewModel = viewModel(),
) {
    val uiState by dataViewModel.uiState.observeAsState(UiState.Loading)
    val scope = rememberCoroutineScope()

    Column {
        Text(text = "Display data here")
        when(uiState) {
            is UiState.Loading -> Text(text = "Loading...")
            is UiState.Success -> {
                val data = (uiState as UiState.Success).data
                Text(text = data)
            }
            is UiState.Error -> Text(text = (uiState as UiState.Error).message)
        }
        Button(onClick = {
            scope.launch {
                dataViewModel.fetchData()
            }
        }) {
            Text(text = "Fetch data")
        }
    }
}