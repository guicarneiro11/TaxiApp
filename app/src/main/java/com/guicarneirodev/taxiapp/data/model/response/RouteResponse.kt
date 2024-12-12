package com.guicarneirodev.taxiapp.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteResponse(val routes: List<Route>) : Parcelable
@Parcelize
data class Route(val polyline: Polyline) : Parcelable
@Parcelize
data class Polyline(val encodedPolyline: String) : Parcelable