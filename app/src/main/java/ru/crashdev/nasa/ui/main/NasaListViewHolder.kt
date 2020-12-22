package ru.crashdev.nasa.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.nasa_item_grid_photos.view.*
import ru.crashdev.nasa.repository.model.Photos

class NasaListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(photos: Photos) {
        itemView.tv_date.text = photos.earth_date

        Glide.with(itemView.context)
            .load(photos.img_src)
            .into(itemView.iv_photo)
    }
}