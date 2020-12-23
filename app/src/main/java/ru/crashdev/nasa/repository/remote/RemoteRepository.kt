package ru.crashdev.nasa.repository.remote

import android.content.Context
import android.util.Log
import retrofit2.Response
import ru.crashdev.nasa.repository.model.PhotosResponse
import ru.crashdev.nasa.utils.isOnline

class RemoteRepository(private val context: Context) {

    private val api = NasaApi.retrofit().create(NasaApiInterface::class.java)

    suspend fun load() : Response<PhotosResponse> {
        return api.getRovers()
    }
}