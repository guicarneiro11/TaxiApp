package com.guicarneirodev.taxiapp.presentation.screens.request.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guicarneirodev.taxiapp.data.model.request.EstimateRequest
import com.guicarneirodev.taxiapp.domain.usecase.EstimateRideUseCase
import com.guicarneirodev.taxiapp.presentation.screens.request.state.RideRequestState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.guicarneirodev.taxiapp.domain.utils.Result
import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.presentation.base.ViewState

class RideRequestViewModel(
    private val estimateRideUseCase: EstimateRideUseCase
) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Idle)
    val viewState = _viewState.asStateFlow()

    private val _uiState = MutableStateFlow(RideRequestState())
    val uiState = _uiState.asStateFlow()

    private val _navigateToOptions = Channel<RideEstimateResponse>()
    val navigateToOptions = _navigateToOptions.receiveAsFlow()

    fun onCustomerIdChanged(value: String) {
        _uiState.update { it.copy(customerId = value, error = null) }
    }

    fun onOriginChanged(value: String) {
        _uiState.update { it.copy(origin = value, error = null) }
    }

    fun onDestinationChanged(value: String) {
        _uiState.update { it.copy(destination = value, error = null) }
    }

    fun onEstimateClick() {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading
            _uiState.update { it.copy(isLoading = true, error = null) }

            val request = EstimateRequest(
                customerId = _uiState.value.customerId,
                origin = _uiState.value.origin,
                destination = _uiState.value.destination
            )

            when (val result = estimateRideUseCase(request)) {
                is Result.Success -> {
                    _viewState.value = ViewState.Success
                    _uiState.update { it.copy(
                        isLoading = false,
                        estimateResponse = result.data
                    )}
                    _navigateToOptions.send(result.data)
                }
                is Result.Error -> {
                    _viewState.value = ViewState.Error(result.message)
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = result.message
                    )}
                }
            }
        }
    }
}