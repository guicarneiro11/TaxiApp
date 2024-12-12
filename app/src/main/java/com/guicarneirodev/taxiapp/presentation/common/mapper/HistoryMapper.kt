package com.guicarneirodev.taxiapp.presentation.common.mapper

import com.guicarneirodev.taxiapp.domain.model.RideHistoryItem

object HistoryMapper {
    fun filterRidesByDriver(rides: List<RideHistoryItem>, driverId: Int?): List<RideHistoryItem> =
        when (driverId) {
            1 -> rides.filter { it.driver.name == "Homer Simpson" }
            2 -> rides.filter { it.driver.name == "Dominic Toretto" }
            3 -> rides.filter { it.driver.name == "James Bond" }
            else -> rides
        }
}