package com.guicarneirodev.taxiapp.presentation.screens.request

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.presentation.screens.request.components.RideRequestForm
import com.guicarneirodev.taxiapp.presentation.screens.request.components.RideRequestTopBar
import com.guicarneirodev.taxiapp.presentation.screens.request.viewmodel.RideRequestViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideRequestScreen(
    viewModel: RideRequestViewModel = koinViewModel(),
    onEstimateSuccess: (RideEstimateResponse, String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.navigateToOptions.collect { response ->
            onEstimateSuccess(response, state.customerId)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { RideRequestTopBar(scrollBehavior) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                RideRequestForm(
                    state = state,
                    onCustomerIdChanged = viewModel::onCustomerIdChanged,
                    onOriginChanged = viewModel::onOriginChanged,
                    onDestinationChanged = viewModel::onDestinationChanged,
                    onEstimateClick = viewModel::onEstimateClick
                )
            }
        }
    }
}