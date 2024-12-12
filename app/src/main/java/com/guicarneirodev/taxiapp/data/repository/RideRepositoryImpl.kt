package com.guicarneirodev.taxiapp.data.repository

import com.guicarneirodev.taxiapp.data.api.RideApi
import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.data.model.request.ConfirmRideRequest
import com.guicarneirodev.taxiapp.data.model.request.EstimateRequest
import com.guicarneirodev.taxiapp.data.model.response.ConfirmRideResponse
import com.guicarneirodev.taxiapp.data.model.response.RideHistory

class RideRepositoryImpl(private val api: RideApi) : RideRepository {
    override suspend fun estimateRide(request: EstimateRequest): RideEstimateResponse {
        return api.estimateRide(request)
    }

    override suspend fun confirmRide(request: ConfirmRideRequest): ConfirmRideResponse {
        return api.confirmRide(request)
    }

    override suspend fun getRideHistory(customerId: String, driverId: Int?): RideHistory {
        return api.getRideHistory(customerId, driverId)
    }
}