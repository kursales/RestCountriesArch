package com.kursales.data.repositories

import com.kursales.domain.repositories.RestCountriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {


    @Binds
    abstract fun bindsRestCountriesRepository(impl: RestCountriesRepositoryImpl): RestCountriesRepository

}