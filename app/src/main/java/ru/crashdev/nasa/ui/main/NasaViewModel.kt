package ru.crashdev.nasa.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.crashdev.nasa.repository.DataRepository
import ru.crashdev.nasa.repository.model.Latest_photos
import ru.crashdev.nasa.repository.model.PhotosResponse
import ru.crashdev.nasa.utils.ApiException

class NasaViewModel(private val repository: DataRepository = DataRepository()) : ViewModel() {

    private val allPhotos = MediatorLiveData<List<Latest_photos>>()

    var photos: Latest_photos? = null
        set (photos) {
            field = photos
        }

    val photoId: Int?
        get() = photos?.photos_id

    val earth: String?
        get() = photos?.earth_date

    val img : String?
        get() = photos?.img_src

    init {
        getAllPhotos()
    }

    fun getSavedPhotos() = allPhotos

    private fun getAllPhotos() {
        allPhotos.addSource(repository.getLocalData()) {
            allPhotos.postValue(it)
        }
    }

    suspend fun loadRemote(): PhotosResponse {
        val response = repository.getRemoteData()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw ApiException(response.code().toString())
        }
    }

    suspend fun saveToLocal(it: List<Latest_photos>) {
        repository.saveAllToLocal(it)
    }

    fun deleteImage(photoid: Int) {
        repository.deleteImage(photoid)
    }

    fun undo(tmpPhotos: Latest_photos) {
        repository.undoDeleteImage(tmpPhotos)

    }

}