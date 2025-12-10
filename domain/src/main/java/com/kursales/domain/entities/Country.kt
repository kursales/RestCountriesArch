package com.kursales.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    @PrimaryKey
    private val id: Long = 0,
    @SerialName("capital")
    val capital: List<String>,
)