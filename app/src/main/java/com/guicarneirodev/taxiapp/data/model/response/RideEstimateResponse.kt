package com.guicarneirodev.taxiapp.data.model.response

import android.os.Parcelable
import com.guicarneirodev.taxiapp.domain.model.Driver
import com.guicarneirodev.taxiapp.domain.model.Location
import kotlinx.parcelize.Parcelize

@Parcelize
data class RideEstimateResponse(
    val origin: Location,
    val destination: Location,
    val distance: Int,
    val duration: String,
    val options: List<Driver>,
    val routeResponse: RouteResponse
) : Parcelable
