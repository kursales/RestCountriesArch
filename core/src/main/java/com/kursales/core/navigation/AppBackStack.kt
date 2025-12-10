package com.kursales.core.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class AppBackStack(
    startRoute: NavigationScreens,
) {

    val backStack = mutableStateListOf(startRoute)

    fun add(route: NavigationScreens) {
        backStack.add(route)
    }

    fun remove() = backStack.removeLastOrNull()
}