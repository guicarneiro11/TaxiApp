package com.guicarneirodev.taxiapp.presentation.common.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.guicarneirodev.taxiapp.domain.model.Location

@SuppressLint("UnrememberedMutableState")
@Composable
fun RouteMap(
    originLocation: Location,
    destinationLocation: Location,
    polyline: String,
    modifier: Modifier = Modifier
) {
    val origin = LatLng(originLocation.latitude, originLocation.longitude)
    val destination = LatLng(destinationLocation.latitude, destinationLocation.longitude)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLngBounds.Builder()
                .include(origin)
                .include(destination)
                .build()
                .center,
            10f
        )
    }

    val decodedPolyline = remember(polyline) {
        PolyUtil.decode(polyline).map { LatLng(it.latitude, it.longitude) }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = false
        )
    ) {
        Marker(
            state = MarkerState(position = origin),
            title = "Origem",
            snippet = "Ponto de partida"
        )

        Marker(
            state = MarkerState(position = destination),
            title = "Destino",
            snippet = "Ponto de chegada"
        )

        Polyline(
            points = decodedPolyline,
            color = Color.Blue,
            width = 5f
        )

        LaunchedEffect(origin, destination) {
            val bounds = LatLngBounds.Builder()
                .include(origin)
                .include(destination)
                .build()
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    100
                ),
                durationMs = 1000
            )
        }
    }
}