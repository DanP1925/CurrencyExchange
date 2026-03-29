package com.danp1925.currencyexchange.data

import com.danp1925.currencyexchange.data.di.IoDispatcher
import com.danp1925.currencyexchange.data.local.ConversionRateDao
import com.danp1925.currencyexchange.data.local.LocalConversionRate
import com.danp1925.currencyexchange.domain.IConversionRateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

class ConversionRateRepository @Inject constructor(
    private val conversionRateDao: ConversionRateDao,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher,
) : IConversionRateRepository {
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

    override suspend fun getConversionRates(): Map<String, BigDecimal> =
        withContext(dispatcher) {
            val currentTimeStamp = System.currentTimeMillis()
            var conversionRateWithMetadata = try {
                conversionRateDao.getConversionRatesWithMetaData()
            } catch (exception: Exception) {
                conversionRateDao.updateList(
                    currentTimeStamp,
                    newItems = conversionRates.map { (currency, value) ->
                        LocalConversionRate(
                            value = value.toFloat(),
                            currency = currency
                        )
                    }
                )
                conversionRateDao.getConversionRatesWithMetaData()
            }

            val lastDownloadedAt =
                conversionRateWithMetadata.conversionRateMetaData.lastDownloadedAt
            if (currentTimeStamp - lastDownloadedAt > TIME_BETWEEN_UPDATES_IN_MILLISECONDS) {
                conversionRateDao.updateList(
                    currentTimeStamp,
                    newItems = conversionRates.map { (currency, value) ->
                        LocalConversionRate(
                            value = value.toFloat(),
                            currency = currency
                        )
                    }
                )
                conversionRateWithMetadata = conversionRateDao.getConversionRatesWithMetaData()
            }

            return@withContext conversionRateWithMetadata.localConversionRates.associate { conversionRate ->
                conversionRate.currency to BigDecimal(conversionRate.value.toLong())
            }
        }

    companion object {
        const val TIME_BETWEEN_UPDATES_IN_MILLISECONDS = 300000
    }
}
