package com.danp1925.currencyexchange.presentation.main

import androidx.lifecycle.ViewModel
import com.danp1925.currencyexchange.presentation.main.models.UIConvertedValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                MainScreenState(
                    baseValue = UIConvertedValue(BigDecimal(DEFAULT_BASE_VALUE), DEFAULT_BASE_CURRENCY),
                ),
            )
        val uiState = _uiState.asStateFlow()

        fun onValueChanged(newValue: String) {
            _uiState.update {
                it.copy(
                    baseValue = uiState.value.baseValue.copy(value = BigDecimal(newValue)),
                    convertedValues = getDummyData(),
                )
            }
        }

        companion object {
            fun getDummyData() =
                listOf(
                    UIConvertedValue(BigDecimal(345.95), "PEN"),
                    UIConvertedValue(BigDecimal(86.61), "EUR"),
                    UIConvertedValue(BigDecimal(16029.90), "JPY"),
                )

            const val DEFAULT_BASE_VALUE = 0
            const val DEFAULT_BASE_CURRENCY = "USD"
        }
    }
