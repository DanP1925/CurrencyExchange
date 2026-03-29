package com.danp1925.currencyexchange.presentation.main

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performTextInput

class MainScreenRobot(
    val composeTestRule: ComposeTestRule,
) {
    fun inputValue(newValue: String) {
        composeTestRule
            .onNode(hasTestTag(MainTestTag.CURRENCY_INPUT))
            .performTextInput(text = newValue)
    }

    fun assertExchangeRateAtIndex(
        index: Int,
        converted: String,
    ) {
        composeTestRule
            .onNode(hasTestTag(MainTestTag.EXCHANGE_RATES))
            .onChildren()[index]
            .onChildren()[0]
            .assertTextEquals(converted)
    }
}
