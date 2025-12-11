package com.kursales.data.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kursales.data.local.AppDatabase
import com.kursales.data.local.converters.ListConverter
import com.kursales.data.local.converters.MapStringToCountryCurrencyConverter
import com.kursales.data.local.converters.MapStringToStringConverter
import com.kursales.data.remote.RestCountriesApi
import com.kursales.data.remote.retrofit.ResponseResultAdapterFactory
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
        val responseResultCallAdapterFactory = ResponseResultAdapterFactory()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(responseResultCallAdapterFactory)
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
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE)
            .addTypeConverter(MapStringToStringConverter(json))
            .addTypeConverter(MapStringToCountryCurrencyConverter(json))
            .fallbackToDestructiveMigration(false)
            .build()
    }

    companion object {
        private const val DATABASE = "APP_DATABASE"
        private const val BASE_URL = "https://restcountries.com/v3.1/"
    }
}