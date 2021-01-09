package ru.crashdev.nasa.di

import android.app.Application
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.crashdev.nasa.repository.NasaRepository
import ru.crashdev.nasa.repository.local.NasaDao
import ru.crashdev.nasa.repository.local.NasaDatabase
import ru.crashdev.nasa.repository.remote.NasaApi
import ru.crashdev.nasa.ui.main.NasaViewModel

private const val BASE_URL: String = "https://api.nasa.gov/mars-photos/api/v1/"
const val API_KEY: String = "cdmjSNiSg058NkQ8FsSaXlaUkNO8Eve3wexNhnwg"
const val CAMERA: String = "NAVCAM"

val viewModelModule = module {
    single { NasaViewModel(get()) }
}

val apiModule = module {
    fun provideNasaApi(retrofit: Retrofit): NasaApi {
        return retrofit.create(NasaApi::class.java)
    }
    single { provideNasaApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): NasaDatabase {
        return Room.databaseBuilder(application, NasaDatabase::class.java, "nasa.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideDao(database: NasaDatabase): NasaDao {
        return database.nasaDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideUserRepository(api: NasaApi, dao: NasaDao): NasaRepository {
        return NasaRepository(api, dao)
    }

    single { provideUserRepository(get(), get()) }
}