package com.guicarneirodev.taxiapp.data.model.request

import com.google.gson.annotations.SerializedName

data class EstimateRequest(
    @SerializedName("customer_id") val customerId: String,
    val origin: String,
    val destination: String
)
