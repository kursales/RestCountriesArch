package com.kursales.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.kursales.domain.entities.CountryCurrency
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json


@ProvidedTypeConverter
internal class MapStringToCountryCurrencyConverter(private val json: Json) {

    private val serializer
        get() = MapSerializer(String.serializer(), CountryCurrency.serializer())

    @TypeConverter
    fun toString(items: Map<String, CountryCurrency>?): String? {
        if (items == null) return null
        return json.encodeToString(serializer, items)
    }

    @TypeConverter
    fun fromString(src: String?): Map<String, CountryCurrency>? {
        if (src.isNullOrBlank()) return null
        return json.decodeFromString(serializer, src)
    }

}