package com.kursales.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

object ListConverter {

    private const val ITEM_LIST_SEPARATOR = "┊"

//    @JvmStatic
//    @TypeConverter
//    fun fromLongGroups(groups: List<Long>?): String? {
//        return groups?.joinToString(separator = ITEM_LIST_SEPARATOR)
//    }
//
//    @TypeConverter
//    @JvmStatic
//    fun toLongGroups(groups: String?): List<Long> {
//        return if (groups.isNullOrEmpty()) {
//            emptyList()
//        } else {
//            groups.split(ITEM_LIST_SEPARATOR).map { it.toLong() }
//        }
//    }
//
//    @JvmStatic
//    @TypeConverter
//    fun fromIntGroups(groups: List<Int>?): String? {
//        return groups?.joinToString(separator = ITEM_LIST_SEPARATOR)
//    }
//
//    @TypeConverter
//    @JvmStatic
//    fun toIntGroups(groups: String?): List<Int> {
//        return if (groups.isNullOrEmpty()) {
//            emptyList()
//        } else {
//            groups.split(ITEM_LIST_SEPARATOR).map { it.toInt() }
//        }
//    }

    @JvmStatic
    @TypeConverter
    fun fromStringGroups(src: List<String>): String {
        return src.joinToString(separator = ITEM_LIST_SEPARATOR)
    }

    @TypeConverter
    @JvmStatic
    fun toStringGroups(src: String): List<String> {
        return if (src.isEmpty()) {
            emptyList()
        } else {
            src.split(ITEM_LIST_SEPARATOR)
        }
    }

}
