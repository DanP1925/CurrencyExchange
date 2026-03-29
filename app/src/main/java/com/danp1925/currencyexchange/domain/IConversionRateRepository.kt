package com.danp1925.currencyexchange.domain

import java.math.BigDecimal

interface IConversionRateRepository {
    suspend fun getConversionRates(): Map<String, BigDecimal>
}
