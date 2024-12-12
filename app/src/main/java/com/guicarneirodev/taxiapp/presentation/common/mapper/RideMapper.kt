package com.guicarneirodev.taxiapp.presentation.common.mapper

import com.guicarneirodev.taxiapp.domain.model.Driver
import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.data.model.request.ConfirmRideRequest
import com.guicarneirodev.taxiapp.presentation.screens.options.state.RideOptionsState

object RideMapper {
    fun RideEstimateResponse.toOptionsState(customerId: String) = RideOptionsState(
        customerId = customerId,
        origin = origin,
        destination = destination,
        distance = distance.toDouble(),
        duration = duration,
        drivers = options,
        polyline = routeResponse.routes.firstOrNull()?.polyline?.encodedPolyline.orEmpty()
    )

    fun createConfirmRequest(state: RideOptionsState, driver: Driver): ConfirmRideRequest? =
        driver.value?.let {
            ConfirmRideRequest(
                customerId = state.customerId,
                origin = state.origin.toString(),
                destination = state.destination.toString(),
                distance = state.distance,
                duration = state.duration,
                driver = driver.toDriverRequest(),
                value = it
            )
        }
}