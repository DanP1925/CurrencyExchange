package com.danp1925.currencyexchange.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class ConversionRateWithTimestamp(
    @Embedded val conversionRateMetaData: ConversionRateMetaData,
    @Relation(
        parentColumn = "metaDataId",
        entityColumn = "metaDataId",
    )
    val localConversionRates: List<LocalConversionRate>,
)
