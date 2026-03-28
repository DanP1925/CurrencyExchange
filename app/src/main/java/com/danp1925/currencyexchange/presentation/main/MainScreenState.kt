package com.danp1925.currencyexchange.presentation.main

import androidx.compose.runtime.Stable

@Stable
data class MainScreenState(
    val baseValue: String = "",
    val currencyConverted: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
