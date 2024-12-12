package com.guicarneirodev.taxiapp.data.api

import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.data.model.response.RideHistory
import com.guicarneirodev.taxiapp.data.model.request.ConfirmRideRequest
import com.guicarneirodev.taxiapp.data.model.request.EstimateRequest
import com.guicarneirodev.taxiapp.data.model.response.ConfirmRideResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RideApi {
    @POST("ride/estimate")
    suspend fun estimateRide(@Body request: EstimateRequest): RideEstimateResponse

    @PATCH("ride/confirm")
    suspend fun confirmRide(@Body request: ConfirmRideRequest): ConfirmRideResponse

    @GET("ride/{customerId}")
    suspend fun getRideHistory(
        @Path("customerId") customerId: String,
        @Query("driver_id") driverId: Int?
    ): RideHistory
}