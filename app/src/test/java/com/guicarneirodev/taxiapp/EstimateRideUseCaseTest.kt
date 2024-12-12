package com.guicarneirodev.taxiapp

import com.guicarneirodev.taxiapp.data.model.request.EstimateRequest
import com.guicarneirodev.taxiapp.data.model.response.RideEstimateResponse
import com.guicarneirodev.taxiapp.data.model.response.RouteResponse
import com.guicarneirodev.taxiapp.data.repository.RideRepository
import com.guicarneirodev.taxiapp.domain.model.Driver
import com.guicarneirodev.taxiapp.domain.model.Location
import com.guicarneirodev.taxiapp.domain.model.Review
import com.guicarneirodev.taxiapp.domain.usecase.EstimateRideUseCase
import com.guicarneirodev.taxiapp.domain.validators.EstimateValidator
import com.guicarneirodev.taxiapp.domain.utils.Result
import com.guicarneirodev.taxiapp.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class EstimateRideUseCaseTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val repository = mockk<RideRepository>()
    private val validator = mockk<EstimateValidator>()
    private val useCase = EstimateRideUseCase(repository, validator)

    @Test
    fun `when validation fails with invalid data, returns error`() = runTest {
        // Given
        val request = EstimateRequest(
            customerId = "CT01",
            origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            destination = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200"
        )
        every { validator.validate(request) } returns false

        // When
        val result = useCase(request)

        // Then
        assertTrue(result is Result.Error)
        assertEquals("Dados inválidos para estimativa", (result as Result.Error).message)
        verify { validator.validate(request) }
        coVerify(exactly = 0) { repository.estimateRide(any()) }
    }

    @Test
    fun `when request is successful with valid data, returns success`() = runTest {
        // Given
        val request = EstimateRequest(
            customerId = "CT01",
            origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            destination = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200"
        )
        val expectedResponse = RideEstimateResponse(
            origin = Location(latitude = -23.5505, longitude = -46.6333),
            destination = Location(latitude = -23.5676, longitude = -46.6932),
            distance = 5,
            duration = "20min",
            options = listOf(
                Driver(
                    id = 1,
                    name = "Homer",
                    description = "Motorista experiente",
                    vehicle = "Sedan",
                    review = Review(rating = 4.5f, comment = "Ótimo serviço"),
                    value = 25.0
                ),
                Driver(
                    id = 2,
                    name = "Dominic",
                    description = "Motorista rápido",
                    vehicle = "Esportivo",
                    review = Review(rating = 4.8f, comment = "Muito profissional"),
                    value = 30.0
                ),
                Driver(
                    id = 3,
                    name = "James",
                    description = "Motorista luxo",
                    vehicle = "Premium",
                    review = Review(rating = 5.0f, comment = "Excelente"),
                    value = 35.0
                )
            ),
            routeResponse = RouteResponse(routes = emptyList())
        )

        every { validator.validate(request) } returns true
        coEvery { repository.estimateRide(request) } returns expectedResponse

        // When
        val result = useCase(request)

        // Then
        assertTrue(result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(expectedResponse, successResult.data)
        assertEquals(expectedResponse.distance, successResult.data.distance)
        assertEquals(3, successResult.data.options.size)

        verify { validator.validate(request) }
        coVerify { repository.estimateRide(request) }
    }

    @Test
    fun `when repository throws exception with invalid destination, returns error`() = runTest {
        // Given
        val request = EstimateRequest(
            customerId = "CT01",
            origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            destination = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031"
        )

        every { validator.validate(request) } returns true
        coEvery { repository.estimateRide(request) } throws Exception("Erro do endereço de destino igual ao de origem")

        // When
        val result = useCase(request)

        // Then
        assertTrue(result is Result.Error)
        assertEquals("Erro do endereço de destino igual ao de origem", (result as Result.Error).message)
        verify { validator.validate(request) }
        coVerify { repository.estimateRide(request) }
    }
}