package ru.crashdev.nasa.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_rover")
data class Rover (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val rover_id : Int,
    @SerializedName("name")
    val rover_name : String,
    val landing_date : String,
    val launch_date : String,
    val status : String
)