package com.kursales.restcountriestest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.kursales.core.navigation.AppBackStack
import com.kursales.core.navigation.NavigationScreens
import com.kursales.country_details.CountryDetailsScreen
import com.kursales.country_list.CountryListScreen
import com.kursales.restcountriestest.ui.theme.RestCountriesTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModelProvider by lazy {
        ViewModelProvider(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RestCountriesTestTheme {

                val appBackStack = remember {
                    AppBackStack(NavigationScreens.List)
                }
                NavDisplay(
                    backStack = appBackStack.backStack,
                    onBack = { appBackStack.remove() },
                    entryProvider = entryProvider {
                        entry<NavigationScreens.List> {
                            CountryListScreen(appBackStack)
                        }
                        entry<NavigationScreens.Details> { details ->
                            CountryDetailsScreen(appBackStack, details.id)
                        }
                    }
                )
            }
        }
    }
}