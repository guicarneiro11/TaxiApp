package com.guicarneirodev.taxiapp.data.repository

import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.data.model.response.RideHistory
import com.guicarneirodev.taxiapp.data.model.request.ConfirmRideRequest
import com.guicarneirodev.taxiapp.data.model.request.EstimateRequest
import com.guicarneirodev.taxiapp.data.model.response.ConfirmRideResponse

interface RideRepository {
    suspend fun estimateRide(request: EstimateRequest): RideEstimateResponse
    suspend fun confirmRide(request: ConfirmRideRequest): ConfirmRideResponse
    suspend fun getRideHistory(customerId: String, driverId: Int?): RideHistory
}