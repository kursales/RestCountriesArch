package com.kursales.domain.repositories

import com.kursales.core.result.ResultOf
import com.kursales.domain.entities.Country
import kotlinx.coroutines.flow.Flow

interface RestCountriesRepository {
    suspend fun refreshCountries(): ResultOf<Unit>
    fun observeCountries(): Flow<List<Country>>
    fun observeCountryDetails(id: Int): Flow<Country>
}