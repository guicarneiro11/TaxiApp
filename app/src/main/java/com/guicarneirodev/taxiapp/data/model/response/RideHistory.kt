package com.guicarneirodev.taxiapp.data.model.response

import com.guicarneirodev.taxiapp.domain.model.RideHistoryItem

data class RideHistory(
    val customerId: String,
    val rides: List<RideHistoryItem>
)
