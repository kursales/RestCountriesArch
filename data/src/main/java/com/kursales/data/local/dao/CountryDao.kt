package com.kursales.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kursales.domain.entities.Country
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CountryDao {

    @Transaction
    open suspend fun updateCountries(countries: List<Country>) {
        deleteAllCountries()
        saveCountries(countries)
    }

    @Insert
    abstract suspend fun saveCountries(countries: List<Country>)

    @Query("DELETE FROM ${Country.TABLE_NAME}")
    abstract suspend fun deleteAllCountries()

    @Query("SELECT * FROM ${Country.TABLE_NAME}")
    abstract fun observeCountries(): Flow<List<Country>>

    @Query("SELECT * FROM ${Country.TABLE_NAME} WHERE ${Country.ID_NAME} LIKE :id")
    abstract fun observeCountryDetails(id: Int): Flow<Country>
}