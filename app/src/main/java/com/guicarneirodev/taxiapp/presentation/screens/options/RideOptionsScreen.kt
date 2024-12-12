package com.guicarneirodev.taxiapp.presentation.screens.options

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import com.guicarneirodev.taxiapp.presentation.screens.options.viewmodel.RideOptionsEvent
import com.guicarneirodev.taxiapp.presentation.screens.options.viewmodel.RideOptionsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import com.guicarneirodev.taxiapp.presentation.common.components.RouteMap
import com.guicarneirodev.taxiapp.presentation.screens.options.components.DriverCard
import com.guicarneirodev.taxiapp.presentation.common.components.EmptyState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideOptionsScreen(
    viewModel: RideOptionsViewModel = koinViewModel(),
    onNavigateToHistory: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is RideOptionsEvent.NavigateToHistory -> onNavigateToHistory()
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Escolha seu Motorista",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Mapa Animado
                AnimatedContent(
                    targetState = state.origin to state.destination,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "map_animation"
                ) { (origin, destination) ->
                    if (origin != null && destination != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(vertical = 16.dp)
                                .animateContentSize(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            RouteMap(
                                originLocation = origin,
                                destinationLocation = destination,
                                polyline = state.polyline,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                AnimatedContent(
                    targetState = state.drivers to state.isLoading,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "drivers_animation"
                ) { (drivers, isLoading) ->
                    when {
                        isLoading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        drivers.isEmpty() -> {
                            EmptyState()
                        }
                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(
                                    items = drivers,
                                    key = { driver -> driver.id }
                                ) { driver ->
                                    DriverCard(
                                        driver = driver,
                                        onChooseClick = { viewModel.confirmRide(driver) },
                                        enabled = !state.isLoading
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}