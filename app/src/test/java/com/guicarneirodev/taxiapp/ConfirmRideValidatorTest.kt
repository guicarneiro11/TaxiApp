package com.guicarneirodev.taxiapp

import com.guicarneirodev.taxiapp.data.model.request.ConfirmRideRequest
import com.guicarneirodev.taxiapp.data.model.request.DriverRequest
import com.guicarneirodev.taxiapp.domain.validators.ConfirmRideValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ConfirmRideValidatorTest {
    private val validator = ConfirmRideValidator()

    @Test
    fun `when all data is valid, returns true`() {
        val request = ConfirmRideRequest(
            customerId = "CT01",
            origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            destination = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200",
            distance = 10.0,
            duration = "30min",
            driver = DriverRequest(1, "Homer"),
            value = 25.0
        )
        assertTrue(validator.validate(request))
    }

    @Test
    fun `when customerId is blank, returns false`() {
        val request = ConfirmRideRequest(
            customerId = "",
            origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            destination = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200",
            distance = 10.0,
            duration = "30min",
            driver = DriverRequest(1, "Homer"),
            value = 25.0
        )
        assertFalse(validator.validate(request))
    }

    @Test
    fun `when same origin and destination, returns false`() {
        val sameAddress = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031"
        val request = ConfirmRideRequest(
            customerId = "CT01",
            origin = sameAddress,
            destination = sameAddress,
            distance = 10.0,
            duration = "30min",
            driver = DriverRequest(1, "Homer"),
            value = 25.0
        )
        assertFalse(validator.validate(request))
    }
}