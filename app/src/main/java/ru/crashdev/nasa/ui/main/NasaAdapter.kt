package ru.crashdev.nasa.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.crashdev.nasa.R
import ru.crashdev.nasa.repository.model.Latest_photos
import ru.crashdev.nasa.utils.ItemViewClickListener

class NasaAdapter(
    private val data: MutableList<Latest_photos>,
    private val itemViewClickListener: ItemViewClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NasaListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.nasa_item_grid_photos, parent, false),
            itemViewClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nasaListViewHolder = holder as NasaListViewHolder
        nasaListViewHolder.bindView(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setPhotos(latestphotosList: List<Latest_photos>) {
        this.data.clear()
        this.data.addAll(latestphotosList)
        notifyDataSetChanged()
    }

}