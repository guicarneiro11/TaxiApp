package com.guicarneirodev.taxiapp.domain.model

data class RideHistoryItem(
    val id: Int,
    val date: String,
    val driver: Driver,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val value: Double
)
