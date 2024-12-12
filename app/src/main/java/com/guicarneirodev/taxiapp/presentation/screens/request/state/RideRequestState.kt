package com.guicarneirodev.taxiapp.presentation.screens.request.state

import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse

data class RideRequestState(
    val customerId: String = "",
    val origin: String = "",
    val destination: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val estimateResponse: RideEstimateResponse? = null
)