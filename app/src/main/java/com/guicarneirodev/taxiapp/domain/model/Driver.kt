package com.guicarneirodev.taxiapp.domain.model

import android.os.Parcelable
import com.guicarneirodev.taxiapp.data.model.request.DriverRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class Driver(
    val id: Int,
    val name: String,
    val description: String? = null,
    val vehicle: String? = null,
    val review: Review? = null,
    val value: Double? = null
) : Parcelable {
    fun toDriverRequest() = DriverRequest(id, name)
}
