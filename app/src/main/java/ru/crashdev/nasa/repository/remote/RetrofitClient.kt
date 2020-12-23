package ru.crashdev.nasa.repository.remote

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.crashdev.nasa.repository.model.PhotosResponse

class RetrofitClient {

    private val nasaApi: NasaApi

    companion object {

        //https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?api_key=cdmjSNiSg058NkQ8FsSaXlaUkNO8Eve3wexNhnwg&camera=NAVCAM
        //https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/latest_photos?api_key=cdmjSNiSg058NkQ8FsSaXlaUkNO8Eve3wexNhnwg&camera=NAVCAM
        const val BASE_URL: String = "https://api.nasa.gov/mars-photos/api/v1/"
        const val API_KEY: String = "cdmjSNiSg058NkQ8FsSaXlaUkNO8Eve3wexNhnwg"
        const val CAMERA: String = "NAVCAM"
    }

    init {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(okHttpClient)
            .build()
        nasaApi = retrofit.create(NasaApi::class.java)
    }

    suspend fun getRovers(): Response<PhotosResponse> {
        return nasaApi.getRovers()
    }
}