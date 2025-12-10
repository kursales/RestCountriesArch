package com.kursales.domain.repositories

import com.kursales.domain.entities.Country

interface RestCountriesRepository {
    suspend fun getAllCountries(): List<Country>
}