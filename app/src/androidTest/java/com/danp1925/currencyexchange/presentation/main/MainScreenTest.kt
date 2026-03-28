package com.danp1925.currencyexchange.presentation.main

import androidx.compose.ui.test.junit4.createComposeRule
import com.danp1925.currencyexchange.ui.theme.CurrencyExchangeTheme
import org.junit.Rule
import org.junit.Test

class MainScreenTest {
    private val mainViewModel = MainViewModel()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenMainScreen_whenEnteringAmount_thenRatesAreUpdated() {
        composeTestRule.setContent {
            CurrencyExchangeTheme {
                MainScreen(mainViewModel)
            }
        }

        val originalCurrency = "100"
        val expectedCurrencies = listOf("345.95", "86.61", "16029.90")

        with(MainScreenRobot(composeTestRule)) {
            inputValue(newValue = originalCurrency)
            expectedCurrencies.forEachIndexed { index, expected ->
                assertExchangeRateAtIndex(index, expected)
            }
        }
    }
}
