package com.danp1925.currencyexchange.presentation.main

import junit.framework.TestCase.assertEquals
import org.junit.Test

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
}
