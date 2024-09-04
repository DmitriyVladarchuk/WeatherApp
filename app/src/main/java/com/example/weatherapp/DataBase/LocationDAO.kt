package com.example.weatherapp.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {

    @Query("SELECT * FROM locations")
    fun getLocations(): Flow<List<Location>>

    @Insert(entity = Location::class)
    fun saveLocation(location: Location)

    @Update(entity = Location::class)
    fun updateLocation(location: Location)

    @Delete(entity = Location::class)
    fun deleteLocation(location: Location)

    @Query("DELETE FROM locations")
    fun deleteAllLocations()

}