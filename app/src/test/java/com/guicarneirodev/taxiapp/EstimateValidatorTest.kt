package com.guicarneirodev.taxiapp

import com.guicarneirodev.taxiapp.data.model.request.EstimateRequest
import com.guicarneirodev.taxiapp.domain.validators.EstimateValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EstimateValidatorTest {
    private val validator = EstimateValidator()

    @Test
    fun `when all fields are valid, returns true`() {
        val request = EstimateRequest(
            customerId = "CT01",
            origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            destination = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200"
        )

        assertTrue(validator.validate(request))
    }

    @Test
    fun `when customerId is blank, returns false`() {
        val request = EstimateRequest(
            customerId = "",
            origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            destination = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200"
        )

        assertFalse(validator.validate(request))
    }

    @Test
    fun `when origin and destination are same, returns false`() {
        val request = EstimateRequest(
            customerId = "CT01",
            origin = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031",
            destination = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031"
        )

        assertFalse(validator.validate(request))
    }
}