package ru.crashdev.nasa.repository.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
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

    abstract fun nasaDao(): NasaDao

    companion object {
        private const val DB_NAME = "nasa.db"
        private var INSTANCE: NasaDatabase? = null

        fun getInstance(application: Application): NasaDatabase {
            if (INSTANCE == null) {
                synchronized(NasaDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        application,
                        NasaDatabase::class.java, DB_NAME
                    )
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}