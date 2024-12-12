package com.guicarneirodev.taxiapp.data.model.request

import com.google.gson.annotations.SerializedName

data class ConfirmRideRequest(
    @SerializedName("customer_id") val customerId: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: DriverRequest,
    val value: Double
) {
    fun isValid(): Boolean =
        customerId.isNotBlank() &&
                origin.isNotBlank() &&
                destination.isNotBlank() &&
                distance > 0 &&
                duration.isNotBlank() &&
                value > 0
}

data class DriverRequest(
    val id: Int,
    val name: String
)

