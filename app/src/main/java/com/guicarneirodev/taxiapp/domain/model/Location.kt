package com.guicarneirodev.taxiapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val latitude: Double,
    val longitude: Double
) : Parcelable {
    override fun toString(): String = "$latitude,$longitude"
}
