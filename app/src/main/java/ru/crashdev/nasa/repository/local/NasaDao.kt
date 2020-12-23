package ru.crashdev.nasa.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.crashdev.nasa.repository.model.Latest_photos

@Dao
interface NasaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(photos: List<Latest_photos>)

    @Query("select * from tbl_photos where img_src != 'none' order by photos_id desc")
    fun getAllPhotos(): LiveData<List<Latest_photos>>

    @Query("update tbl_photos set img_src='none' where photos_id = :photoId")
    fun deleteImage(photoId: Int)
}