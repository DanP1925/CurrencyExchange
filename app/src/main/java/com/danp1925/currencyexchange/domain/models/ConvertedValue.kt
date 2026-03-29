package com.danp1925.currencyexchange.domain.models

import java.math.BigDecimal

data class ConvertedValue(
    val value: BigDecimal,
    val currency: String,
)
