package com.danp1925.currencyexchange.data.di

import com.danp1925.currencyexchange.data.ConversionRateRepository
import com.danp1925.currencyexchange.domain.IConversionRateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConversionRateModule {
    @Singleton
    @Binds
    abstract fun provideConversionRateRepository(conversionRateRepository: ConversionRateRepository): IConversionRateRepository
}
