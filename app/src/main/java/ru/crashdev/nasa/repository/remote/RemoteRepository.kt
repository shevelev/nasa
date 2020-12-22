package ru.crashdev.nasa.repository.remote

import android.content.Context
import retrofit2.Response
import ru.crashdev.nasa.repository.model.PhotosResponse

class RemoteRepository(context: Context) {

    private val api = NasaApi.retrofit().create(NasaApiInterface::class.java)

    suspend fun load() : Response<PhotosResponse> {
        return api.getRovers()
    }
}