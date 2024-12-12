package com.guicarneirodev.taxiapp

import com.guicarneirodev.taxiapp.domain.validators.DriverValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DriverValidatorTest {
    private val validator = DriverValidator()

    @Test
    fun `when distance is valid for Homer driver, returns true`() {
        assertTrue(validator.validateDistance(1, 1.5))
    }

    @Test
    fun `when distance is valid for Dominic driver, returns true`() {
        assertTrue(validator.validateDistance(2, 6.0))
    }

    @Test
    fun `when distance is valid for James driver, returns true`() {
        assertTrue(validator.validateDistance(3, 15.0))
    }

    @Test
    fun `when distance is below minimum for Homer driver, returns false`() {
        assertFalse(validator.validateDistance(1, 0.5))
    }

    @Test
    fun `when distance is below minimum for Dominic driver, returns false`() {
        assertFalse(validator.validateDistance(2, 3.0))
    }

    @Test
    fun `when distance is below minimum for James driver, returns false`() {
        assertFalse(validator.validateDistance(3, 8.0))
    }
}