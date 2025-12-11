package com.kursales.data.repositories

import com.kursales.core.result.ResultOf
import com.kursales.core.result.asResultOf
import com.kursales.core.result.map
import com.kursales.data.local.AppDatabase
import com.kursales.data.remote.RestCountriesApi
import com.kursales.domain.entities.Country
import com.kursales.domain.repositories.RestCountriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RestCountriesRepositoryImpl @Inject constructor(
    appDatabase: AppDatabase,
    private val restCountriesApi: RestCountriesApi,
) : RestCountriesRepository {

    private val countriesDao = appDatabase.countriesDao()

    override suspend fun refreshCountries(): ResultOf<Unit> {
       return restCountriesApi.getAllCountries().asResultOf().map {

            countriesDao.updateCountries(it)
        }
    }

    override fun observeCountries(): Flow<List<Country>> {
        return countriesDao.observeCountries()
    }

    override fun observeCountryDetails(id: Int): Flow<Country> {
        return countriesDao.observeCountryDetails(id)
    }

}