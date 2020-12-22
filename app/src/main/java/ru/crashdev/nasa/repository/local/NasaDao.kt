package ru.crashdev.nasa.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.crashdev.nasa.repository.model.Photos

@Dao
interface NasaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(photos: List<Photos>)

    @Query("select * from tbl_photos")
    suspend fun getAllPhotos(): List<Photos>
}