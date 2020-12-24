package ru.crashdev.nasa.repository

import androidx.lifecycle.LiveData
import retrofit2.Response
import ru.crashdev.nasa.repository.local.NasaDao
import ru.crashdev.nasa.repository.model.Latest_photos
import ru.crashdev.nasa.repository.model.PhotosResponse
import ru.crashdev.nasa.repository.remote.RetrofitClient
import ru.crashdev.nasa.utils.db

class DataRepository {

    private val nasaDao: NasaDao = db.nasaDao()
    private val retrofitClient = RetrofitClient()
    private val allLatestphotos: LiveData<List<Latest_photos>>

    init {
        allLatestphotos = nasaDao.getAllPhotos()
    }

    suspend fun getRemoteData(): Response<PhotosResponse> {
        return retrofitClient.getRovers()
    }

    fun getLocalData(): LiveData<List<Latest_photos>> {
        return nasaDao.getAllPhotos()
    }

    suspend fun saveAllToLocal(it: List<Latest_photos>) {
        nasaDao.insertAll(it)
    }

    fun deleteImage(photoId: Int) {
        nasaDao.deleteImage(photoId)
    }

    fun undoDeleteImage(tmpPhotos: Latest_photos) {
        nasaDao.undoDeleteImage(tmpPhotos.photos_id.toString(), tmpPhotos.img_src)
    }
}