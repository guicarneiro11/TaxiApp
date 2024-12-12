package com.guicarneirodev.taxiapp.domain.validators

class DriverValidator {
    fun validateDistance(driverId: Int, distance: Double): Boolean =
        when (driverId) {
            1 -> distance >= 1.0
            2 -> distance >= 5.0
            3 -> distance >= 10.0
            else -> false
        }
}