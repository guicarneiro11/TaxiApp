package com.guicarneirodev.taxiapp.domain.usecase

import com.guicarneirodev.taxiapp.data.repository.RideRepository
import com.guicarneirodev.taxiapp.domain.utils.Result
import com.guicarneirodev.taxiapp.data.model.response.RideHistory
import com.guicarneirodev.taxiapp.domain.error.ErrorHandler

class GetRideHistoryUseCase(
    private val repository: RideRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(customerId: String, driverId: Int?): Result<RideHistory> {
        if (customerId.isBlank()) {
            return Result.Error("ID do cliente é obrigatório")
        }

        return try {
            val response = repository.getRideHistory(customerId, driverId)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(errorHandler.handle(e))
        }
    }
}