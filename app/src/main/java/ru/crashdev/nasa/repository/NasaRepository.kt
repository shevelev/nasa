package ru.crashdev.nasa.repository

import androidx.lifecycle.LiveData
import retrofit2.Response
import ru.crashdev.nasa.repository.local.NasaDao
import ru.crashdev.nasa.repository.model.Latest_photos
import ru.crashdev.nasa.repository.model.PhotosResponse
import ru.crashdev.nasa.repository.remote.NasaApi

class NasaRepository(
    private val retrofitClient: NasaApi, private val nasaDao: NasaDao
) {

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