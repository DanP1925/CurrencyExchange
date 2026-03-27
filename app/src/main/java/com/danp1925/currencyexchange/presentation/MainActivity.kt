package com.danp1925.currencyexchange.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.danp1925.currencyexchange.presentation.main.MainScreen
import com.danp1925.currencyexchange.ui.theme.CurrencyExchangeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyExchangeTheme {
                MainScreen()
            }
        }
    }
}