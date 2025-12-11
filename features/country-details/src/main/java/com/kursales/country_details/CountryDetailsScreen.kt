package com.kursales.country_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import coil3.compose.AsyncImage
import com.kursales.core.navigation.AppBackStack
import com.kursales.country_details.ui.FieldDescription
import com.kursales.country_details.ui.TextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailsScreen(
    appBackStack: AppBackStack,
    id: Int,
) {

    val viewModel: CountryDetailsViewModel = hiltViewModel(
        key = id.toString(),
        creationCallback = { factory: CountryDetailsViewModel.Factory ->
            factory.create(id)
        }
    )
    val state = viewModel.countryDetailsState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = MaterialTheme.typography.headlineSmall,
                        text = stringResource(R.string.details)
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            appBackStack.remove()
                        },
                        tint = MaterialTheme.colorScheme.secondary,
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            state.value?.let { stateValue ->
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(2f),
                        text = stateValue.name.official,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    AsyncImage(
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape),
                        model = stateValue.flag.png,
                        contentDescription = null
                    )
                }
                FieldDescription(
                    modifier = Modifier.padding(start = 16.dp, top = 32.dp),
                    text = stringResource(R.string.continent)
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = stateValue.capital.firstOrNull() ?: "-"
                )


                FieldDescription(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Languages"
                )
                LazyColumn(
                    userScrollEnabled = false
                ) {
                    items(stateValue.languages.map { it.value }) { language ->
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            text = language
                        )
                    }
                }
                FieldDescription(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(R.string.currencies)
                )
                LazyColumn(
                    userScrollEnabled = false
                ) {
                    items(stateValue.currencies.map { it.value }) { currency ->
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            text = "${currency.name} (${currency.symbol})"
                        )
                    }
                }
            }

        }
    }
}
