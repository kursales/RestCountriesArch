package com.kursales.data.remote

import com.kursales.core.result.ResponseResult
import com.kursales.domain.entities.Country
import retrofit2.http.GET
import retrofit2.http.Query

interface RestCountriesApi {

    @GET("all")
    suspend fun getAllCountries(
        @Query("fields") fields: Array<String> = arrayOf("capital","name","flags", "currencies", "languages")
    ): ResponseResult<List<Country>>

}