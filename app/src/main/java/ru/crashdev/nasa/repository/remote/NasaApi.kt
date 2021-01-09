package ru.crashdev.nasa.repository.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.crashdev.nasa.di.API_KEY
import ru.crashdev.nasa.di.CAMERA
import ru.crashdev.nasa.repository.model.PhotosResponse

interface NasaApi {
    @GET("rovers/curiosity/latest_photos")
    suspend fun getRovers(
        @Query("api_key") api_key: String = API_KEY,
        @Query("camera") camera : String = CAMERA,
        @Query("page") page : Int = 1
    ): Response<PhotosResponse>
}