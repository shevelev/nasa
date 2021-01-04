package ru.crashdev.nasa.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.crashdev.nasa.R
import ru.crashdev.nasa.databinding.NasaItemGridPhotosBinding
import ru.crashdev.nasa.repository.model.Latest_photos

class NasaAdapter(private val data: MutableList<Latest_photos>) : RecyclerView.Adapter<NasaListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<NasaItemGridPhotosBinding>(inflater,
            R.layout.nasa_item_grid_photos,
            parent,
            false)
        return  NasaListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NasaListViewHolder, position: Int) {
        val r = data[position]
        holder.bind(r)
    }

    override fun getItemCount() = data.size

    fun setPhotos(list: List<Latest_photos>) {
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }
}