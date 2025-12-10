package com.kursales.data.repositories

import com.kursales.data.remote.RestCountriesApi
import com.kursales.domain.entities.Country
import com.kursales.domain.repositories.RestCountriesRepository
import kotlinx.serialization.Serializable
import javax.inject.Inject


class RestCountriesRepositoryImpl @Inject constructor(
    private val restCountriesApi: RestCountriesApi,
) : RestCountriesRepository {

    override suspend fun getAllCountries(): List<Country> {
        return restCountriesApi.getAllCountries()
    }

}