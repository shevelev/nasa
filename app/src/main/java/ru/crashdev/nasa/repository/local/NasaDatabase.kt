package ru.crashdev.nasa.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.crashdev.nasa.repository.model.Camera
import ru.crashdev.nasa.repository.model.Latest_photos
import ru.crashdev.nasa.repository.model.Rover

@Database(
    entities = [Latest_photos::class, Camera::class, Rover::class],
    version = 1,
    exportSchema = false
)
abstract class NasaDatabase : RoomDatabase() {
    abstract val nasaDao: NasaDao
}