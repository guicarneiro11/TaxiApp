package com.guicarneirodev.taxiapp.presentation.screens.request.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.guicarneirodev.taxiapp.presentation.screens.request.state.RideRequestState

@Composable
fun RideRequestForm(
    state: RideRequestState,
    onCustomerIdChanged: (String) -> Unit,
    onOriginChanged: (String) -> Unit,
    onDestinationChanged: (String) -> Unit,
    onEstimateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Solicitar Viagem",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            RideTextField(
                value = state.customerId,
                onValueChange = onCustomerIdChanged,
                label = "ID do Cliente",
                leadingIcon = Icons.Default.Person,
                isError = state.error?.contains("cliente") == true,
                enabled = !state.isLoading
            )

            RideTextField(
                value = state.origin,
                onValueChange = onOriginChanged,
                label = "Origem",
                leadingIcon = Icons.Default.LocationOn,
                isError = state.error?.contains("origem") == true,
                enabled = !state.isLoading
            )

            RideTextField(
                value = state.destination,
                onValueChange = onDestinationChanged,
                label = "Destino",
                leadingIcon = Icons.Default.LocationOn,
                isError = state.error?.contains("destino") == true,
                enabled = !state.isLoading
            )

            ErrorMessage(error = state.error)

            EstimateButton(
                isLoading = state.isLoading,
                onClick = onEstimateClick
            )
        }
    }
}