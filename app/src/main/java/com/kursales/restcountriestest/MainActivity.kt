package com.kursales.restcountriestest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.arttttt.nav3router.Nav3Host
import com.kursales.core.navigation.AppBackStack
import com.kursales.core.navigation.NavigationScreens
import com.kursales.domain.interactors.RestCountriesInteractor
import com.kursales.restcountriestest.ui.theme.RestCountriesTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                        entry<NavigationScreens.List> { }
                        entry<NavigationScreens.Details> { }
                    }
                )
            }
        }
    }
}