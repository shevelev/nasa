package ru.crashdev.nasa.repository.local

import android.content.Context
import ru.crashdev.nasa.repository.model.Photos

class LocalRepository(context: Context) {

    private val localDatabase = NasaDatabase.getInstance(context)?.nasaDao()

    suspend fun load(): List<Photos>? {
        return localDatabase?.getAllPhotos()
    }

    suspend fun save(it: List<Photos>) {
        localDatabase?.insertAll(it)
    }


}