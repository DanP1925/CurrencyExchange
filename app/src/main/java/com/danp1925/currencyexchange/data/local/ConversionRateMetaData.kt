package com.danp1925.currencyexchange.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConversionRateMetaData(
    @PrimaryKey val metaDataId: String = "current_list",
    val lastDownloadedAt: Long,
)
