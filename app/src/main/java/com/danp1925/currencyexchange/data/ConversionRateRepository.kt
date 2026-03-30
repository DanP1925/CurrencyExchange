package com.danp1925.currencyexchange.data

import com.danp1925.currencyexchange.BuildConfig
import com.danp1925.currencyexchange.data.di.IoDispatcher
import com.danp1925.currencyexchange.data.local.ConversionRateDao
import com.danp1925.currencyexchange.data.local.LocalConversionRate
import com.danp1925.currencyexchange.data.remote.ConversionRateService
import com.danp1925.currencyexchange.domain.IConversionRateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

class ConversionRateRepository @Inject constructor(
    private val conversionRateDao: ConversionRateDao,
    private val conversionRateService: ConversionRateService,
    @param:IoDispatcher private val dispatcher: CoroutineDispatcher,
) : IConversionRateRepository {
    private val apiKey = BuildConfig.API_KEY

    override suspend fun getConversionRates(): Map<String, BigDecimal> =
        withContext(dispatcher) {
            val currentTimeStamp = System.currentTimeMillis()
            var conversionRateWithMetadata =
                try {
                    conversionRateDao.getConversionRatesWithMetaData()
                } catch (exception: Exception) {
                    fetchConversionRatesAndUpdate(currentTimeStamp)
                    conversionRateDao.getConversionRatesWithMetaData()
                }

            val lastDownloadedAt =
                conversionRateWithMetadata.conversionRateMetaData.lastDownloadedAt
            if (currentTimeStamp - lastDownloadedAt > TIME_BETWEEN_UPDATES_IN_MILLISECONDS) {
                fetchConversionRatesAndUpdate(currentTimeStamp)
                conversionRateWithMetadata = conversionRateDao.getConversionRatesWithMetaData()
            }

            return@withContext conversionRateWithMetadata.localConversionRates.associate { conversionRate ->
                conversionRate.currency to BigDecimal(conversionRate.value.toDouble())
            }
        }

    private suspend fun fetchConversionRatesAndUpdate(currentTimeStamp: Long) {
        val response = conversionRateService.getLatest(apiKey)
        val newItems =
            response.rates.map { (currency, value) ->
                LocalConversionRate(
                    value = value,
                    currency = currency,
                )
            }
        conversionRateDao.updateList(currentTimeStamp, newItems = newItems)
    }

    companion object {
        const val TIME_BETWEEN_UPDATES_IN_MILLISECONDS = 300000
    }
}
