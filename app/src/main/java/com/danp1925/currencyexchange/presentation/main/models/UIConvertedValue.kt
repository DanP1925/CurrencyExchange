package com.danp1925.currencyexchange.presentation.main.models

import androidx.compose.runtime.Immutable
import java.math.BigDecimal

@Immutable
data class UIConvertedValue(
    val value: BigDecimal,
    val currency: String,
)
