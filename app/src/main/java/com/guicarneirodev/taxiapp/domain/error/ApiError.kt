package com.guicarneirodev.taxiapp.domain.error

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("error_code") val errorCode: String,
    @SerializedName("error_description") val errorDescription: String
)
