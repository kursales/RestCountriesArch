package com.kursales.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kursales.data.local.converters.ListConverter
import com.kursales.data.local.converters.MapStringToCountryCurrencyConverter
import com.kursales.data.local.converters.MapStringToStringConverter
import com.kursales.data.local.dao.CountryDao
import com.kursales.domain.entities.Country

@Database(entities = [Country::class], version = 1)
@TypeConverters(
    ListConverter::class,
    MapStringToStringConverter::class,
    MapStringToCountryCurrencyConverter::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countriesDao(): CountryDao
}