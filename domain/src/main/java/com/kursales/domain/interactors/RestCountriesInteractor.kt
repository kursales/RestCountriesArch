package com.kursales.domain.interactors

import com.kursales.core.result.ResultOf
import com.kursales.domain.entities.Country
import com.kursales.domain.repositories.RestCountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RestCountriesInteractor @Inject constructor(
    private val countriesRepository: RestCountriesRepository,
) {

    suspend fun refreshCountries(): ResultOf<Unit> {
        return countriesRepository.refreshCountries()
    }

    fun observeCountries(): Flow<List<Country>> {
        return countriesRepository.observeCountries()
    }

    fun observeCountryDetails(id: Int): Flow<Country> {
        return countriesRepository.observeCountryDetails(id)
    }
}