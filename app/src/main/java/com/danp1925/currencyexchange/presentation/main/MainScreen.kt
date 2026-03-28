package com.danp1925.currencyexchange.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danp1925.currencyexchange.R
import com.danp1925.currencyexchange.presentation.main.models.UIConvertedValue
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContent(
        baseValue = uiState.displayValue,
        baseCurrency = uiState.baseValue.currency,
        convertedCurrencies = uiState.convertedValues,
        onValueChanged = mainViewModel::onValueChanged,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MainScreenContent(
    baseValue: String,
    baseCurrency: String,
    convertedCurrencies: List<UIConvertedValue>,
    onValueChanged: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors =
                    topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = {
                    Text(stringResource(R.string.main_title))
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding),
        ) {
            TextField(
                value = baseValue,
                onValueChange = onValueChanged,
                label = { Text("Insert your amount") },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag(tag = MainTestTag.CURRENCY_INPUT),
            )
            Box {
                Button(onClick = {}) {
                    Text(baseCurrency)
                }
            }
            LazyVerticalGrid(
                columns = GridCells.FixedSize(size = 120.dp),
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.testTag(tag = MainTestTag.EXCHANGE_RATES),
            ) {
                items(convertedCurrencies) { convertedCurrency ->
                    Column {
                        Text(
                            text =
                                convertedCurrency.value
                                    .setScale(2, RoundingMode.HALF_EVEN)
                                    .toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(120.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    val convertedCurrencies =
        listOf(
            UIConvertedValue(BigDecimal(345.95), "PEN"),
            UIConvertedValue(BigDecimal(86.61), "EUR"),
            UIConvertedValue(BigDecimal(16029.90), "JPY"),
            UIConvertedValue(BigDecimal(82.35), "GBP"),
            UIConvertedValue(BigDecimal(136.72), "CAD"),
            UIConvertedValue(BigDecimal(153.48), "AUD"),
            UIConvertedValue(BigDecimal(727.50), "MXN"),
            UIConvertedValue(BigDecimal(536.20), "INR"),
            UIConvertedValue(BigDecimal(92.15), "CHF"),
            UIConvertedValue(BigDecimal(1330.50), "KRW"),
            UIConvertedValue(BigDecimal(364.80), "BRL"),
            UIConvertedValue(BigDecimal(743.25), "CNY"),
        )
    MainScreenContent(
        baseValue = "100.0",
        baseCurrency = "USD",
        convertedCurrencies = convertedCurrencies,
        onValueChanged = {},
    )
}
