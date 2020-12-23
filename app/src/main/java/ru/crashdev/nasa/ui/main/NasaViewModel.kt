package ru.crashdev.nasa.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.crashdev.nasa.repository.DataRepository
import ru.crashdev.nasa.repository.remote.NasaApi.Companion.retrofit
import ru.crashdev.nasa.repository.remote.NasaApiInterface
import ru.crashdev.nasa.repository.model.Photos
import ru.crashdev.nasa.repository.model.PhotosResponse
import ru.crashdev.nasa.utils.ApiException

class NasaViewModel : ViewModel() {
    lateinit var repository: DataRepository

    @JvmName("setRepository1")
    fun setRepository(repository: DataRepository) {
        this.repository = repository
    }

    suspend fun loadRemote(): PhotosResponse {
        Log.d("qwe","4")
        val response = repository.getRemoteData()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw ApiException(response.code().toString())
        }
    }

    suspend fun loadLocal(): List<Photos>? {
        return repository.getLocalData()
    }

    suspend fun saveToLocal(it: List<Photos>) {
        repository.saveAllToLocal(it)

    }
}