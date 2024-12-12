package com.guicarneirodev.taxiapp

import com.guicarneirodev.taxiapp.data.model.response.RideHistory
import com.guicarneirodev.taxiapp.data.repository.RideRepository
import com.guicarneirodev.taxiapp.domain.error.ErrorHandler
import com.guicarneirodev.taxiapp.domain.model.Driver
import com.guicarneirodev.taxiapp.domain.model.RideHistoryItem
import com.guicarneirodev.taxiapp.domain.usecase.GetRideHistoryUseCase
import com.guicarneirodev.taxiapp.utils.MainCoroutineRule
import com.guicarneirodev.taxiapp.domain.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class GetRideHistoryUseCaseTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val repository = mockk<RideRepository>()
    private val errorHandler = mockk<ErrorHandler>()
    private val useCase = GetRideHistoryUseCase(repository, errorHandler)

    @Test
    fun `when request is successful for CT01, returns rides`() = runTest {
        // Given
        val customerId = "CT01"
        val expectedRides = RideHistory(
            customerId = customerId,
            rides = listOf(
                RideHistoryItem(
                    id = 1,
                    date = "2024-03-15T14:30:00",
                    driver = Driver(id = 1, name = "Homer"),
                    origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
                    destination = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200",
                    distance = 15.0,
                    duration = "30min",
                    value = 25.0
                ),
                RideHistoryItem(
                    id = 2,
                    date = "2024-03-15T15:30:00",
                    driver = Driver(id = 2, name = "Dominic"),
                    origin = "Av. Thomas Edison, 365 - Barra Funda, São Paulo - SP, 01140-000",
                    destination = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200",
                    distance = 8.0,
                    duration = "20min",
                    value = 30.0
                )
            )
        )

        coEvery { repository.getRideHistory(customerId, null) } returns expectedRides

        // When
        val result = useCase(customerId, null)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedRides, (result as Result.Success).data)
        assertEquals(2, result.data.rides.size)
        coVerify { repository.getRideHistory(customerId, null) }
    }

    @Test
    fun `when filtering by driver Homer, returns filtered rides`() = runTest {
        // Given
        val customerId = "CT01"
        val driverId = 1 // Homer
        val expectedRides = RideHistory(
            customerId = customerId,
            rides = listOf(
                RideHistoryItem(
                    id = 1,
                    date = "2024-03-15T14:30:00",
                    driver = Driver(id = 1, name = "Homer"),
                    origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
                    destination = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200",
                    distance = 15.0,
                    duration = "30min",
                    value = 25.0
                )
            )
        )

        coEvery { repository.getRideHistory(customerId, driverId) } returns expectedRides

        // When
        val result = useCase(customerId, driverId)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedRides, (result as Result.Success).data)
        assertEquals(1, result.data.rides.size)
        assertEquals("Homer", result.data.rides[0].driver.name)
        coVerify { repository.getRideHistory(customerId, driverId) }
    }
}