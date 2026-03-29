package com.danp1925.currencyexchange.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalConversionRate::class, ConversionRateMetaData::class],
    version = 2
)
abstract class ConversionRateDb : RoomDatabase() {
    abstract val conversionRateDao: ConversionRateDao
}