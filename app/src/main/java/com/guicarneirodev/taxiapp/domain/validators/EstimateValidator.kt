package com.guicarneirodev.taxiapp.domain.validators

import com.guicarneirodev.taxiapp.data.model.request.EstimateRequest

class EstimateValidator {
    fun validate(request: EstimateRequest): Boolean =
        request.customerId.isNotBlank() &&
                request.origin.isNotBlank() &&
                request.destination.isNotBlank() &&
                request.origin != request.destination
}