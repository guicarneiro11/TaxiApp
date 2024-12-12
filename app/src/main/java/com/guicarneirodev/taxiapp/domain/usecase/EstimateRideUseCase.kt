package com.guicarneirodev.taxiapp.domain.usecase

import com.guicarneirodev.taxiapp.data.repository.RideRepository
import com.guicarneirodev.taxiapp.domain.utils.Result
import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.data.model.request.EstimateRequest
import com.guicarneirodev.taxiapp.domain.validators.EstimateValidator

class EstimateRideUseCase(
    private val repository: RideRepository,
    private val validator: EstimateValidator
) {
    suspend operator fun invoke(request: EstimateRequest): Result<RideEstimateResponse> {
        return try {
            if (!validator.validate(request)) {
                return Result.Error("Dados inválidos para estimativa")
            }

            val response = repository.estimateRide(request)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Erro ao estimar viagem")
        }
    }
}