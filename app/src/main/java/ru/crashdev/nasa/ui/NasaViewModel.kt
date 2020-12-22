package ru.crashdev.nasa.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.crashdev.nasa.api.NasaApi.Companion.retrofit
import ru.crashdev.nasa.api.NasaApiInterface
import ru.crashdev.nasa.model.Photos

class NasaViewModel : ViewModel() {
    val listPhotos: MutableLiveData<List<Photos>> = MutableLiveData()

    init {
        load()
    }

    fun load() {

        val api = retrofit().create(NasaApiInterface::class.java)
        Log.d("qwe", "load() from scope")
        viewModelScope.launch {
            val result = api.getRovers()
            Log.d("qwe", "load() in scope")
            result.body()?.photos?.let {
                Log.d("qwe", "load() in body scope ${it.size}")
                listPhotos.postValue(it.sortedBy { it.earth_date })
            }
        }
    }
}