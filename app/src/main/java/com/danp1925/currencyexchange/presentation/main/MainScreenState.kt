package com.danp1925.currencyexchange.presentation.main

import com.danp1925.currencyexchange.presentation.main.models.UIConvertedValue

data class MainScreenState(
    val baseValue: UIConvertedValue,
    val displayValue: String = baseValue.value.toString(),
    val convertedValues: List<UIConvertedValue> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
