package ru.crashdev.nasa.repository.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tbl_photos")
data class Photos (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val photos_id : Int,
    val sol : Int,
    @Embedded
    @NotNull
    val camera : Camera? = null,
    val img_src : String,
    val earth_date : String,
    @Embedded
    @NotNull
    val rover : Rover? = null
)