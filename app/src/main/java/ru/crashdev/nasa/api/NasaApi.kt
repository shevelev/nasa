package ru.crashdev.nasa.api

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaApi {
    companion object {

        //https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?api_key=cdmjSNiSg058NkQ8FsSaXlaUkNO8Eve3wexNhnwg&sol=1000
        const val BASE_URL: String = "https://api.nasa.gov/mars-photos/api/v1/"
        const val API_KEY: String = "cdmjSNiSg058NkQ8FsSaXlaUkNO8Eve3wexNhnwg"
        const val ROVER: String = "Curiosity"
        const val CAMERA: String = "NAVCAM"
        const val SOL: Int = 1000

        fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
}