package com.guicarneirodev.taxiapp.presentation.screens.options.state

import com.guicarneirodev.taxiapp.domain.model.Driver
import com.guicarneirodev.taxiapp.domain.model.Location

data class RideOptionsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val customerId: String = "",
    val origin: Location? = null,
    val destination: Location? = null,
    val distance: Double = 0.0,
    val duration: String = "",
    val drivers: List<Driver> = emptyList(),
    val polyline: String = "",
    val selectedDriver: Driver? = null,
    val isInitializing: Boolean = false
)
