package ru.crashdev.nasa.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.crashdev.nasa.repository.model.Camera
import ru.crashdev.nasa.repository.model.Photos
import ru.crashdev.nasa.repository.model.Rover

@Database(entities = [Photos::class, Camera::class, Rover::class], version = 1, exportSchema = false)
abstract class NasaDatabase: RoomDatabase() {

    abstract fun nasaDao(): NasaDao

    companion object {
        private var INSTANCE: NasaDatabase? = null

        fun getInstance(context: Context): NasaDatabase? {
            if (INSTANCE == null) {
                synchronized(NasaDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NasaDatabase::class.java, "nasa.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}