package com.danp1925.currencyexchange.domain.usecases

import com.danp1925.currencyexchange.domain.IConversionRateRepository
import com.danp1925.currencyexchange.domain.models.ConvertedValue
import java.math.BigDecimal
import javax.inject.Inject

class GetConvertedValuesUseCase @Inject constructor(
    private val repository: IConversionRateRepository,
) {
    suspend operator fun invoke(
        value: BigDecimal,
        currency: String,
    ): List<ConvertedValue> {
        val conversionRates = repository.getConversionRates()
        val originalValue = conversionRates[currency]
        return conversionRates.entries.toList().map { entry ->
            ConvertedValue(
                value = value.multiply(entry.value).divide(originalValue),
                currency = entry.key,
            )
        }
    }
}
