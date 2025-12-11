package com.kursales.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationScreens: NavKey {

    @Serializable
    data object List: NavigationScreens()
    @Serializable
    data class Details(val id: Int): NavigationScreens()

}