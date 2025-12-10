package com.kursales.domain.interactors

import com.kursales.domain.entities.Country
import com.kursales.domain.repositories.RestCountriesRepository
import javax.inject.Inject

class RestCountriesInteractor @Inject constructor(
    private val countriesRepository: RestCountriesRepository
) {

    suspend fun getAllCountries(): List<Country> {
        return countriesRepository.getAllCountries()
    }
}