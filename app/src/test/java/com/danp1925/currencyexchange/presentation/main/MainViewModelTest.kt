package com.danp1925.currencyexchange.presentation.main

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MainViewModelTest {
    private val sut by lazy {
        MainViewModel()
    }

    @Test
    fun `WHEN initiated THEN default value is assigned`() {
        val expectedValue = MainViewModel.DEFAULT_BASE_VALUE.toString()
        val expectedCurrency = MainViewModel.DEFAULT_BASE_CURRENCY

        val state = sut.uiState.value
        val actualDisplayValue = state.displayValue
        val actual = state.baseValue

        assertEquals(expectedValue, actualDisplayValue)
        assertEquals(expectedValue, actual.value.toPlainString())
        assertEquals(expectedCurrency, actual.currency)
    }

    @Nested
    inner class OnValueChanged {
        @Test
        fun `GIVEN valid value WHEN onValueChanged THEN update state`() {
            val expectedValue = "100"

            sut.onValueChanged(expectedValue)
            val result = sut.uiState.value.baseValue

            assertEquals(expectedValue, result.value.toString())
        }

        @Test
        fun `GIVEN invalid regex WHEN onValueChanged THEN early return`() {
            val invalidRegex = "invalid_regex"
            val expectedValue = "100"

            sut.onValueChanged(expectedValue)
            sut.onValueChanged(invalidRegex)
            val result = sut.uiState.value.baseValue

            assertEquals(expectedValue, result.value.toString())
        }

        @Test
        fun `GIVEN value ends with dot WHEN onValueChanged THEN update displayValue`() {
            val input = "100."
            val expectedValue = "100"

            sut.onValueChanged(expectedValue)
            sut.onValueChanged(input)
            val result = sut.uiState.value.baseValue

            assertEquals(expectedValue, result.value.toString())
        }

        @Test
        fun `GIVEN value is empty WHEN onValueChanged THEN update state `() {
            val input = ""
            val expectedValue = "0"

            sut.onValueChanged(input)
            val result = sut.uiState.value.baseValue

            assertEquals(expectedValue, result.value.toString())
        }
    }
}
