package com.kursales.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryName(
    @SerialName("common")
    val common: String,
    @SerialName("official")
    val official: String,
)