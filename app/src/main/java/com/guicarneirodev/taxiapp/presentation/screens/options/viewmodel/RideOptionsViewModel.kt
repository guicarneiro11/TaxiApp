package com.guicarneirodev.taxiapp.presentation.screens.options.viewmodel

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guicarneirodev.taxiapp.domain.model.Driver
import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.presentation.screens.options.state.RideOptionsState
import com.guicarneirodev.taxiapp.domain.usecase.ConfirmRideUseCase
import com.guicarneirodev.taxiapp.domain.utils.Result
import com.guicarneirodev.taxiapp.presentation.common.mapper.RideMapper
import com.guicarneirodev.taxiapp.presentation.base.ViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedRideData(
    val customerId: String,
    val originLat: Double,
    val originLng: Double,
    val destLat: Double,
    val destLng: Double,
    val distance: Double,
    val duration: String,
    val polyline: String,
    val drivers: List<Driver>
) : Parcelable

sealed class RideOptionsEvent {
    data object NavigateToHistory : RideOptionsEvent()
}

class RideOptionsViewModel(
    private val confirmRideUseCase: ConfirmRideUseCase
) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Idle)

    private val _uiState = MutableStateFlow(RideOptionsState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<RideOptionsEvent>()
    val events = _events.receiveAsFlow()

    fun setInitialData(response: RideEstimateResponse, customerId: String) {
        _uiState.update {
            RideMapper.run { response.toOptionsState(customerId) }
        }
    }

    fun confirmRide(driver: Driver) {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading

            try {
                val request = RideMapper.run {
                    createConfirmRequest(_uiState.value, driver)
                }

                when (val result = request?.let { confirmRideUseCase(it) }) {
                    is Result.Success -> {
                        _viewState.value = ViewState.Success
                        _events.send(RideOptionsEvent.NavigateToHistory)
                    }
                    is Result.Error -> {
                        _viewState.value = ViewState.Error(result.message)
                        _uiState.update { it.copy(error = result.message) }
                    }
                    null -> _viewState.value = ViewState.Error("Erro ao criar requisição")
                }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e.message ?: "Erro ao confirmar viagem")
            }
        }
    }
}