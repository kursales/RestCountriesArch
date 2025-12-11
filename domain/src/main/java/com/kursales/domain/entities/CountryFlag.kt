package com.kursales.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryFlag(
    @SerialName("png")
    val png: String,
    @SerialName("svg")
    val svg: String,
)