package ru.crashdev.nasa.repository.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.crashdev.nasa.repository.remote.NasaApi.Companion.API_KEY
import ru.crashdev.nasa.repository.remote.NasaApi.Companion.CAMERA
import ru.crashdev.nasa.repository.remote.NasaApi.Companion.SOL
import ru.crashdev.nasa.repository.model.PhotosResponse

interface NasaApiInterface {
    @GET("rovers/curiosity/photos")
    suspend fun getRovers(
        @Query("sol") sol: Int = SOL,
        @Query("camera") camera : String = CAMERA,
        @Query("api_key") api_key: String = API_KEY,
        @Query("page") page : Int = 1
    ): Response<PhotosResponse>
}