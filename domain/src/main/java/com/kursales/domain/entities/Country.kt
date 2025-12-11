package com.kursales.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(Country.TABLE_NAME)
data class Country(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID_NAME)
    var id: Int = 0,
    @SerialName("capital")
    @ColumnInfo("capital")
    val capital: List<String>,
    @SerialName("flags")
    @Embedded("flags")
    val flag: CountryFlag,
    @SerialName("name")
    @Embedded(prefix = "name")
    val name: CountryName,
    @SerialName("currencies")
    @ColumnInfo("currencies")
    val currencies: Map<String, CountryCurrency>,
    @ColumnInfo("languages")
    @SerialName("languages")
    val languages: Map<String, String>,
) {
    companion object {
        const val TABLE_NAME = "countries_table"
        const val ID_NAME = "id"
    }
}