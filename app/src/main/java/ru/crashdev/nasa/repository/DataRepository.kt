package ru.crashdev.nasa.repository

import android.util.Log
import retrofit2.Response
import ru.crashdev.nasa.repository.local.LocalRepository
import ru.crashdev.nasa.repository.model.Photos
import ru.crashdev.nasa.repository.model.PhotosResponse
import ru.crashdev.nasa.repository.remote.RemoteRepository

class DataRepository(val localRepository: LocalRepository, val remoteRepository: RemoteRepository) {

    suspend fun getRemoteData() : Response<PhotosResponse> {
        Log.d("qwe","5")
        return remoteRepository.load()
    }

    suspend fun getLocalData() : List<Photos>? {
        return localRepository.load()
    }

    suspend fun saveAllToLocal(it: List<Photos>) {
        localRepository.save(it)

    }
}