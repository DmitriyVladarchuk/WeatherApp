package com.example.weatherapp.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.model.Location

@Database(
    entities = [Location::class],
    version = 3,
    exportSchema = false
)
abstract class LocalDB: RoomDatabase() {
    abstract fun dao(): LocationDAO

    companion object{
        @Volatile
        private var INSTANCE: LocalDB? = null

        fun getBataBase(context: Context): LocalDB{
            return INSTANCE ?: synchronized(this){
                buildDataBase(context).also{ INSTANCE = it }
            }
        }

        private fun buildDataBase(context: Context) = Room
            .databaseBuilder(context, LocalDB::class.java, "LocationWeatherDB")
            .fallbackToDestructiveMigration()
            .build()

    }
}