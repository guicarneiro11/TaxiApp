package com.guicarneirodev.taxiapp.domain.error

import com.google.gson.Gson
import retrofit2.HttpException

class ErrorHandler {
    fun handle(exception: Exception): String {
        return when (exception) {
            is HttpException -> handleHttpException(exception)
            else -> exception.message ?: "Erro desconhecido"
        }
    }

    private fun handleHttpException(exception: HttpException): String {
        return when (exception.code()) {
            404 -> {
                try {
                    val errorBody = exception.response()?.errorBody()?.string()
                    val apiError = Gson().fromJson(errorBody, ApiError::class.java)
                    if (apiError.errorCode == "NO_RIDES_FOUND") {
                        apiError.errorDescription
                    } else {
                        "Nenhuma viagem encontrada"
                    }
                } catch (e: Exception) {
                    "Nenhuma viagem encontrada"
                }
            }
            else -> exception.message ?: "Erro ao processar requisição"
        }
    }
}