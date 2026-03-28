package com.danp1925.currencyexchange.presentation.main

import androidx.compose.runtime.Stable
import com.danp1925.currencyexchange.presentation.main.models.UIConvertedValue

@Stable
data class MainScreenState(
    val baseValue: UIConvertedValue,
    val convertedValues: List<UIConvertedValue> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
