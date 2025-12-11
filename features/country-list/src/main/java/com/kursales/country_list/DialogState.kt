package com.kursales.country_list

sealed interface DialogState {
    class Logic(val text: String): DialogState
    data object Connection: DialogState
    data object None: DialogState
}