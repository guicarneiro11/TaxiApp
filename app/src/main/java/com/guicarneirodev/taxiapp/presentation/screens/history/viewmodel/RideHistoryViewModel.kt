package com.guicarneirodev.taxiapp.presentation.screens.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guicarneirodev.taxiapp.presentation.screens.history.state.RideHistoryState
import com.guicarneirodev.taxiapp.domain.usecase.GetRideHistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException
import com.guicarneirodev.taxiapp.domain.utils.Result
import com.guicarneirodev.taxiapp.presentation.common.mapper.HistoryMapper
import com.guicarneirodev.taxiapp.presentation.base.ViewState

class RideHistoryViewModel(
    private val getRideHistoryUseCase: GetRideHistoryUseCase
) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Idle)

    private val _uiState = MutableStateFlow(RideHistoryState())
    val uiState = _uiState.asStateFlow()

    private var currentJob: Job? = null

    fun setCustomerId(customerId: String) {
        _uiState.update { it.copy(customerId = customerId) }
        loadRideHistory()
    }

    fun clearError() {
        _viewState.value = ViewState.Idle
        _uiState.update { it.copy(error = null) }
    }

    fun onDriverFilterChanged(driverId: Int?) {
        if (_uiState.value.selectedDriverId == driverId) return

        viewModelScope.launch {
            delay(300)
            _uiState.update { it.copy(selectedDriverId = driverId) }
            loadRideHistory()
        }
    }

    private fun loadRideHistory() {
        currentJob?.cancel()

        currentJob = viewModelScope.launch {
            _viewState.value = ViewState.Loading

            try {
                val currentState = _uiState.value
                val result = getRideHistoryUseCase(
                    customerId = currentState.customerId,
                    driverId = currentState.selectedDriverId
                )

                when (result) {
                    is Result.Success -> {
                        val filteredRides = HistoryMapper.filterRidesByDriver(
                            result.data.rides,
                            currentState.selectedDriverId
                        )

                        _viewState.value = ViewState.Success
                        _uiState.update {
                            it.copy(
                                rides = filteredRides,
                                hasNoRidesForDriver = filteredRides.isEmpty() && currentState.selectedDriverId != null
                            )
                        }
                    }
                    is Result.Error -> {
                        _viewState.value = ViewState.Error(result.message)
                        _uiState.update {
                            it.copy(
                                error = result.message,
                                rides = emptyList(),
                                hasNoRidesForDriver = true
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    _viewState.value = ViewState.Error(e.message ?: "Erro ao carregar histórico")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
    }
}