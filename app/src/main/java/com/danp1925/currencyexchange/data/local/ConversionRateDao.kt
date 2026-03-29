package com.danp1925.currencyexchange.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ConversionRateDao {

    @Transaction
    @Query("SELECT * FROM ConversionRateMetaData WHERE metaDataId = :id")
    suspend fun getConversionRatesWithMetaData(id: String = "current_list"): ConversionRateWithTimestamp

    @Transaction
    suspend fun updateList(timestamp: Long, newItems: List<LocalConversionRate>) {
        insertConversionRateMetaData(
            ConversionRateMetaData(metaDataId = "current_list", lastDownloadedAt = timestamp)
        )

        deleteConversionRates("current_list")
        insertAllItems(newItems)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversionRateMetaData(metaData: ConversionRateMetaData)

    @Query("DELETE FROM LocalConversionRate WHERE metaDataId = :id")
    suspend fun deleteConversionRates(id: String)

    @Insert
    suspend fun insertAllItems(conversionRates: List<LocalConversionRate>)

}