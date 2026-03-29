package com.danp1925.currencyexchange.data

import com.danp1925.currencyexchange.domain.IConversionRateRepository
import java.math.BigDecimal
import javax.inject.Inject

class ConversionRateRepository @Inject constructor() : IConversionRateRepository {
    private val conversionRates =
        mapOf(
            "USD" to BigDecimal(100.00),
            "PEN" to BigDecimal(345.95),
            "EUR" to BigDecimal(86.61),
            "JPY" to BigDecimal(16029.90),
            "GBP" to BigDecimal(82.35),
            "CAD" to BigDecimal(136.72),
            "AUD" to BigDecimal(153.48),
            "MXN" to BigDecimal(727.50),
            "INR" to BigDecimal(536.20),
            "CHF" to BigDecimal(92.15),
            "KRW" to BigDecimal(1330.50),
            "BRL" to BigDecimal(364.80),
            "CNY" to BigDecimal(743.25),
        )

    override suspend fun getConversionRates(): Map<String, BigDecimal> = conversionRates
}
