package com.kursales.country_list

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kursales.core.navigation.AppBackStack
import com.kursales.core.navigation.NavigationScreens
import com.kursales.core.ui.ErrorAlertDialog
import com.kursales.core.ui.NoConnectionDialog
import com.kursales.country_list.ui.CountryListField
import com.kursales.country_list.ui.StubView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    appBackStack: AppBackStack,
) {
    val viewModel: CountryListViewModel = hiltViewModel()
    val countriesState = viewModel.countriesState.collectAsState(emptyList())
    val errorState = viewModel.errorState.collectAsState()
    val refreshState = viewModel.refreshState.collectAsState()
    val isSearchEnabled = viewModel.isSearchEnabled.collectAsState()
    val queryState = viewModel.searchState.collectAsState()
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Crossfade(
                targetState = isSearchEnabled.value,
                animationSpec = tween()
            ) { isSearch ->
                if (isSearch) {
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            value = queryState.value,
                            onValueChange = { viewModel.changeSearch(it) }
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                                .clickable {
                                    viewModel.changeSearchState()
                                }
                        )
                    }
                } else {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.countries),
                                style = MaterialTheme.typography.headlineSmall
                            )
                        },
                        actions = {
                            Icon(
                                modifier = Modifier.clickable {
                                    viewModel.changeSearchState()
                                },
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            isRefreshing = refreshState.value,
            onRefresh = { viewModel.refresh() }
        ) {
            if (countriesState.value.isNotEmpty()) {
                LazyColumn {
                    items(countriesState.value) { country ->
                        CountryListField(
                            country = country,
                            onBack = { appBackStack.add(NavigationScreens.Details(country.id)) }
                        )
                    }
                }
            } else {
                StubView(
                    modifier = Modifier.fillMaxSize(),
                    text = if (isSearchEnabled.value) stringResource(R.string.found_nothing) else stringResource(
                        R.string.empty
                    ),
                    icon = if (isSearchEnabled.value) Icons.Filled.Search else Icons.Filled.Warning
                )


            }
            when (val error = errorState.value) {

                DialogState.Connection -> NoConnectionDialog(
                    onDismiss = { viewModel.hideErrorDialog() },
                    onRefresh = {
                        viewModel.hideErrorDialog()
                        viewModel.refresh()
                    }
                )

                is DialogState.Logic -> ErrorAlertDialog(
                    error.text,
                    onDismiss = { viewModel.hideErrorDialog() })

                DialogState.None -> {}
            }

        }
    }

}
