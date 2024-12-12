package com.guicarneirodev.taxiapp.presentation.screens.history.state

import com.guicarneirodev.taxiapp.domain.model.RideHistoryItem

data class RideHistoryState(
    val customerId: String = "CT01",
    val rides: List<RideHistoryItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedDriverId: Int? = null,
    val hasNoRidesForDriver: Boolean = false
)
