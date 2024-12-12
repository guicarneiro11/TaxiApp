package com.guicarneirodev.taxiapp.domain.extensions

import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.data.model.request.ConfirmRideRequest

fun RideEstimateResponse.toConfirmRideRequest(customerId: String) = this.options.first().value?.let {
    ConfirmRideRequest(
        customerId = customerId,
        origin = this.origin.toString(),
        destination = this.destination.toString(),
        distance = this.distance.toDouble(),
        duration = this.duration,
        driver = this.options.first().toDriverRequest(),
        value = it
    )
}
