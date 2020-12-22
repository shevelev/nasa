package ru.crashdev.nasa.model

data class Photos (
    val id : Int,
    val sol : Int,
    val camera : Camera,
    val img_src : String,
    val earth_date : String,
    val rover : Rover
)