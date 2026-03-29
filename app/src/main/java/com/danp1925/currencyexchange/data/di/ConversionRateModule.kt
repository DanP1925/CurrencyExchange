package com.danp1925.currencyexchange.data.di

import android.content.Context
import androidx.room.Room
import com.danp1925.currencyexchange.data.ConversionRateRepository
import com.danp1925.currencyexchange.data.local.ConversionRateDao
import com.danp1925.currencyexchange.data.local.ConversionRateDb
import com.danp1925.currencyexchange.domain.IConversionRateRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConversionRateModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): ConversionRateDb {
        return Room.databaseBuilder(
            appContext,
            ConversionRateDb::class.java,
            "conversionrate_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRoomDao(database: ConversionRateDb): ConversionRateDao {
        return database.conversionRateDao
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class ConversionRateBindingModule {
    @Singleton
    @Binds
    abstract fun provideConversionRateRepository(conversionRateRepository: ConversionRateRepository): IConversionRateRepository
}
