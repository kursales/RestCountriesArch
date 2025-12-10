package com.kursales.data.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kursales.data.local.AppDatabase
import com.kursales.data.remote.RestCountriesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val networkJson = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType())
        ).build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): RestCountriesApi {
        return retrofit.create(RestCountriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "APP_DATABASE")
//            .addTypeConverter(LocalDateTimeConverter())
            .build()
    }
}