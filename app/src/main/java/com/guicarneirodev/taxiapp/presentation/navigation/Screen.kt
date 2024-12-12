package com.guicarneirodev.taxiapp.presentation.navigation

sealed class Screen(val route: String) {
    data object RideRequest : Screen("ride_request")
    data object RideOptions : Screen("ride_options?customerId={customerId}") {
        fun createRoute(customerId: String) = "ride_options?customerId=$customerId"
    }
    data object RideHistory : Screen("ride_history/{customerId}") {
        fun createRoute(customerId: String) = "ride_history/$customerId"
    }
}