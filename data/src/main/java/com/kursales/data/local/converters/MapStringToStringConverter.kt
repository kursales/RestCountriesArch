package com.kursales.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

@ProvidedTypeConverter
internal class MapStringToStringConverter(private val json: Json) {

    private val serializer
        get() = MapSerializer(String.serializer(), String.serializer())

    @TypeConverter
    fun toString(items: Map<String, String>?): String? {
        if (items == null) return null
        return json.encodeToString(serializer, items)
    }

    @TypeConverter
    fun fromString(src: String?): Map<String, String>? {
        if (src.isNullOrBlank()) return null
        return json.decodeFromString(serializer, src)
    }

}