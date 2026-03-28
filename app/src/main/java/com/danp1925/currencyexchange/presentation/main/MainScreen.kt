package com.danp1925.currencyexchange.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danp1925.currencyexchange.R

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContent(
        baseValue = uiState.baseValue,
        convertedCurrencies = uiState.currencyConverted,
        onValueChanged = mainViewModel::onValueChanged,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MainScreenContent(
    baseValue: String,
    convertedCurrencies: List<String>,
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
                modifier = Modifier.testTag(tag = MainTestTag.CURRENCY_INPUT),
            )
            LazyVerticalGrid(
                columns = GridCells.FixedSize(size = 120.dp),
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.testTag(tag = MainTestTag.EXCHANGE_RATES),
            ) {
                items(convertedCurrencies) { convertedCurrency ->
                    Column {
                        Text(
                            text = convertedCurrency,
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
    MainScreenContent(
        baseValue = "100",
        convertedCurrencies = listOf("345.95", "86.61", "16029.90"),
        onValueChanged = {},
    )
}
