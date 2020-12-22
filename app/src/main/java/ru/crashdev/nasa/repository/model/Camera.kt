package ru.crashdev.nasa.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_camera")
data class Camera (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val camera_id : Int,
    @SerializedName("name")
    val camera_name : String,
    @SerializedName("rover_id")
    val camera_rover_id : Int,
    val full_name : String
)