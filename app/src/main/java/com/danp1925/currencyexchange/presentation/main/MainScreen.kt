package com.danp1925.currencyexchange.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        expanded = uiState.isMenuOpen,
        onCurrencySelected = mainViewModel::onCurrencySelected,
        convertedCurrencies = uiState.convertedValues,
        onValueChanged = mainViewModel::onValueChanged,
        onExpandedChange = mainViewModel::onToggleMenu,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MainScreenContent(
    baseValue: String,
    baseCurrency: String,
    expanded: Boolean,
    onCurrencySelected: (String) -> Unit,
    convertedCurrencies: List<UIConvertedValue>,
    onValueChanged: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = { MainTopAppBar() },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding),
        ) {
            ValueInput(
                baseValue,
                onValueChanged,
                modifier = Modifier.padding(all = 16.dp),
            )
            CurrencyDropdownMenu(
                expanded,
                onExpandedChange,
                onCurrencySelected,
                baseCurrency,
                convertedCurrencies,
                modifier = Modifier.padding(start = 16.dp),
            )
            ConvertedCurrencies(
                convertedCurrencies,
                modifier =
                    Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxHeight(),
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MainTopAppBar() {
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
}

@Composable
private fun ValueInput(
    baseValue: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = baseValue,
        onValueChange = onValueChanged,
        label = { Text(stringResource(R.string.input_label)) },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done,
            ),
        textStyle = TextStyle(fontSize = 20.sp),
        modifier =
            modifier
                .fillMaxWidth()
                .testTag(tag = MainTestTag.CURRENCY_INPUT),
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CurrencyDropdownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onCurrencySelected: (String) -> Unit,
    baseCurrency: String,
    convertedCurrencies: List<UIConvertedValue>,
    modifier: Modifier = Modifier,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = baseCurrency,
            onValueChange = { },
            modifier =
                Modifier
                    .width(100.dp)
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    ),
            readOnly = true,
            label = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                )
            },
            textStyle = TextStyle(fontSize = 16.sp),
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            modifier = Modifier.width(100.dp),
            expanded = expanded,
            onDismissRequest = { onExpandedChange(true) },
        ) {
            convertedCurrencies.forEach { convertedCurrency ->
                DropdownMenuItem(
                    text = { Text(convertedCurrency.currency) },
                    onClick = {
                        onCurrencySelected(convertedCurrency.currency)
                        onExpandedChange(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
private fun ConvertedCurrencies(
    convertedCurrencies: List<UIConvertedValue>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.FixedSize(size = 120.dp),
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.testTag(tag = MainTestTag.EXCHANGE_RATES),
    ) {
        items(convertedCurrencies) { convertedCurrency ->
            Card(
                modifier = Modifier.width(120.dp).padding(all = 8.dp),
            ) {
                Text(
                    text =
                        convertedCurrency.value
                            .setScale(2, RoundingMode.HALF_EVEN)
                            .toString(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                )
                Text(
                    text = convertedCurrency.currency,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

private fun dummyData() =
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

@Preview
@Composable
fun MainScreenDropdownPreview() {
    MainScreenContent(
        baseValue = "100.0",
        baseCurrency = "USD",
        convertedCurrencies = dummyData(),
        expanded = true,
        onCurrencySelected = {},
        onValueChanged = {},
        onExpandedChange = {},
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreenContent(
        baseValue = "100.0",
        baseCurrency = "USD",
        convertedCurrencies = dummyData(),
        expanded = false,
        onCurrencySelected = {},
        onValueChanged = {},
        onExpandedChange = {},
    )
}
