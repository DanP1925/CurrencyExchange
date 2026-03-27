package com.danp1925.currencyexchange.presentation.main

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performTextInput
import com.danp1925.currencyexchange.ui.theme.CurrencyExchangeTheme
import org.junit.Rule
import org.junit.Test

class MainScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenMainScreen_whenEnteringAmount_thenRatesAreUpdated() {
        composeTestRule.setContent {
            CurrencyExchangeTheme {
                MainScreen()
            }
        }

        val originalCurrency = "100"
        val expectedCurrencies = listOf("345.95", "86.61", "16029.90")

        composeTestRule
            .onNode(hasTestTag(MainTestTag.CURRENCY_INPUT))
            .performTextInput(originalCurrency)

        expectedCurrencies.forEachIndexed { index, expected ->
            composeTestRule
                .onNode(hasTestTag(MainTestTag.CURRENCY_LIST))
                .onChildren()[index]
                .assertTextEquals(expected)
        }
    }
}
