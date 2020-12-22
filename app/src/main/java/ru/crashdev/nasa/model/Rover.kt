package ru.crashdev.nasa.model

data class Rover (
    val id : Int,
    val name : String,
    val landing_date : String,
    val launch_date : String,
    val status : String
)