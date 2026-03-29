package com.danp1925.currencyexchange.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ConversionRateMetaData::class,
            parentColumns = ["metaDataId"],
            childColumns = ["metaDataId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LocalConversionRate(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val metaDataId: String = "current_list",
    val value: Float,
    val currency: String
)
