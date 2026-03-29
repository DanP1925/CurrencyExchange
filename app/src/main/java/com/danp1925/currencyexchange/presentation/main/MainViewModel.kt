package com.danp1925.currencyexchange.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danp1925.currencyexchange.domain.models.ConvertedValue
import com.danp1925.currencyexchange.domain.usecases.GetConvertedValuesUseCase
import com.danp1925.currencyexchange.presentation.main.models.UIConvertedValue
import com.danp1925.currencyexchange.presentation.main.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getConvertedValues: GetConvertedValuesUseCase,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            MainScreenState(
                baseValue = UIConvertedValue(BigDecimal(DEFAULT_BASE_VALUE), DEFAULT_BASE_CURRENCY),
            ),
        )
    val uiState = _uiState.asStateFlow()

    init {
        onValueChanged(DEFAULT_BASE_VALUE.toString())
    }

    fun onValueChanged(inputValue: String) {
        if (!INPUT_REGEX.matches(inputValue)) return

        if (inputValue.endsWith(".")) {
            _uiState.update { it.copy(displayValue = inputValue) }
            return
        }

        val newValue = BigDecimal(inputValue.ifEmpty { "0" })
        val currency = uiState.value.baseValue.currency

        viewModelScope.launch {
            val convertedValues = getConvertedValues(newValue, currency).map(ConvertedValue::toUI)

            _uiState.update {
                it.copy(
                    baseValue = uiState.value.baseValue.copy(value = newValue),
                    displayValue = newValue.toPlainString(),
                    convertedValues = convertedValues,
                )
            }
        }
    }

    fun onToggleMenu(current: Boolean) {
        _uiState.update { it.copy(isMenuOpen = current) }
    }

    companion object {
        const val DEFAULT_BASE_VALUE = 0
        const val DEFAULT_BASE_CURRENCY = "USD"
        val INPUT_REGEX = Regex("^\\d*(\\.\\d{0,2})?$")
    }
}
