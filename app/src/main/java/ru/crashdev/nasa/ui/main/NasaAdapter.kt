package ru.crashdev.nasa.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.crashdev.nasa.R
import ru.crashdev.nasa.repository.model.Photos

class NasaAdapter(val data: List<Photos>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NasaListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.nasa_item_grid_photos, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nasaListViewHolder = holder as NasaListViewHolder
        nasaListViewHolder.bindView(data[position])
    }

    override fun getItemCount(): Int = data.size

}