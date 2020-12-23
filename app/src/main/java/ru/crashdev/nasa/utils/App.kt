package ru.crashdev.nasa.utils

import android.app.Application
import ru.crashdev.nasa.repository.local.NasaDatabase

lateinit var db: NasaDatabase

class App : Application() {
    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        db = NasaDatabase.getInstance(this)
        INSTANCE = this
    }
}