package ru.crashdev.nasa.utils

import ru.crashdev.nasa.repository.model.Latest_photos

interface ItemViewClickListener {
    fun onItemViewLongClick(latestphotos: Latest_photos)
    fun onItemViewClick(latestphotos: Latest_photos)
}