package com.guicarneirodev.taxiapp.domain.validators

import com.guicarneirodev.taxiapp.data.model.request.ConfirmRideRequest

class ConfirmRideValidator {
    fun validate(request: ConfirmRideRequest): Boolean {
        return request.customerId.isNotBlank() &&
                request.origin.isNotBlank() &&
                request.destination.isNotBlank() &&
                request.origin != request.destination &&
                request.distance > 0 &&
                request.duration.isNotBlank() &&
                request.value > 0
    }
}
