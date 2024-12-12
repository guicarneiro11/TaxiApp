package com.guicarneirodev.taxiapp.domain.usecase

import com.guicarneirodev.taxiapp.data.repository.RideRepository
import com.guicarneirodev.taxiapp.data.model.request.ConfirmRideRequest
import com.guicarneirodev.taxiapp.data.model.response.ConfirmRideResponse
import com.guicarneirodev.taxiapp.domain.utils.Result
import com.guicarneirodev.taxiapp.domain.validators.ConfirmRideValidator
import com.guicarneirodev.taxiapp.domain.validators.DriverValidator

class ConfirmRideUseCase(
    private val repository: RideRepository,
    private val confirmValidator: ConfirmRideValidator,
    private val driverValidator: DriverValidator
) {
    suspend operator fun invoke(request: ConfirmRideRequest): Result<ConfirmRideResponse> {
        return try {
            if (!confirmValidator.validate(request)) {
                return Result.Error("Dados inválidos para confirmação")
            }

            if (!driverValidator.validateDistance(request.driver.id, request.distance)) {
                return Result.Error("Distância inválida para o motorista selecionado")
            }

            val response = repository.confirmRide(request)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Erro ao confirmar viagem")
        }
    }
}