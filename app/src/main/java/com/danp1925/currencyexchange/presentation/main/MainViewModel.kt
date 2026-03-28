package com.danp1925.currencyexchange.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(MainScreenState())
        val uiState = _uiState.asStateFlow()

        fun onValueChanged(newValue: String) {
            _uiState.update { it.copy(baseValue = newValue, currencyConverted = getDummyData()) }
        }

        companion object {
            fun getDummyData() = listOf("345.95", "86.61", "16029.90")
        }
    }
