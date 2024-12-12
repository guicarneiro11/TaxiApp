package com.guicarneirodev.taxiapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.presentation.screens.history.RideHistoryScreen
import com.guicarneirodev.taxiapp.presentation.screens.options.RideOptionsScreen
import com.guicarneirodev.taxiapp.presentation.screens.request.RideRequestScreen
import com.guicarneirodev.taxiapp.presentation.screens.options.viewmodel.RideOptionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RideNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.RideRequest.route
    ) {
        composable(Screen.RideRequest.route) {
            RideRequestScreen(
                onEstimateSuccess = { response, customerId ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "ride_estimate",
                        value = response
                    )
                    navController.navigate(Screen.RideOptions.createRoute(customerId))
                }
            )
        }

        composable(
            route = Screen.RideOptions.route,
            arguments = listOf(
                navArgument("customerId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val customerId = backStackEntry.arguments?.getString("customerId") ?: ""
            val estimate = navController.previousBackStackEntry?.savedStateHandle?.get<RideEstimateResponse>("ride_estimate")
            val viewModel: RideOptionsViewModel = koinViewModel()

            LaunchedEffect(Unit) {
                if (estimate != null) {
                    viewModel.setInitialData(estimate, customerId)
                }
            }

            RideOptionsScreen(
                viewModel = viewModel,
                onNavigateToHistory = {
                    navController.navigate(Screen.RideHistory.createRoute(customerId))
                }
            )
        }

        composable(
            route = Screen.RideHistory.route,
            arguments = listOf(
                navArgument("customerId") { type = NavType.StringType }
            )
        ) {
            RideHistoryScreen(
                customerId = it.arguments?.getString("customerId") ?: ""
            )
        }
    }
}