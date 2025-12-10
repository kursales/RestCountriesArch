package com.kursales.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kursales.domain.entities.Country

@Database(entities = [CountryLocal::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
}