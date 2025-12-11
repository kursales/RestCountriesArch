package com.kursales.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CountryCurrency(
    @SerialName("name")
    val name: String,
    @SerialName("symbol")
    val symbol: String
) {
}