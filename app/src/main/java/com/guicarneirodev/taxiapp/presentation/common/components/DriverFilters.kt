package com.guicarneirodev.taxiapp.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guicarneirodev.taxiapp.domain.model.enums.AvailableDriver

@Composable
fun DriverFilters(
    selectedDriverId: Int?,
    onDriverSelected: (Int?) -> Unit,
    enabled: Boolean,
    hasNoRidesForDriver: Boolean = false
) {
    Column {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedDriverId == null,
                    onClick = { if (enabled) onDriverSelected(null) },
                    enabled = enabled,
                    label = { Text("Todos Motoristas") }
                )
            }

            items(AvailableDriver.entries.toTypedArray(), key = { it.id }) { driver ->
                FilterChip(
                    selected = selectedDriverId == driver.id,
                    onClick = { if (enabled) onDriverSelected(driver.id) },
                    enabled = enabled,
                    label = { Text(driver.driverName) }
                )
            }
        }

        AnimatedVisibility(
            visible = hasNoRidesForDriver && selectedDriverId != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Nenhuma viagem encontrada para este motorista",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}