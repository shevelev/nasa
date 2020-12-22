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

class NasaViewModel : ViewModel() {
    lateinit var repository: DataRepository

    @JvmName("setRepository1")
    fun setRepository(repository: DataRepository) {
        this.repository = repository
    }

    suspend fun loadRemote(): Response<PhotosResponse> {
        return repository.getRemoteData()
    }

    suspend fun loadLocal(): List<Photos>? {
        return repository.getLocalData()
    }

    suspend fun saveToLocal(it: List<Photos>) {
        repository.saveAllToLocal(it)

    }
}