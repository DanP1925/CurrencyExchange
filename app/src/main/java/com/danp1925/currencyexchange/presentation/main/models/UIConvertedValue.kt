package com.danp1925.currencyexchange.presentation.main.models

import androidx.compose.runtime.Immutable
import com.danp1925.currencyexchange.domain.models.ConvertedValue
import java.math.BigDecimal

@Immutable
data class UIConvertedValue(
    val value: BigDecimal,
    val currency: String,
)

fun ConvertedValue.toUI() =
    UIConvertedValue(
        value = value,
        currency = currency,
    )
