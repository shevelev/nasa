package ru.crashdev.nasa.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.nasa_item_grid_photos.view.*
import ru.crashdev.nasa.repository.model.Latest_photos
import ru.crashdev.nasa.utils.ItemViewClickListener

class NasaListViewHolder(itemView: View, private val itemViewClickListener : ItemViewClickListener) : RecyclerView.ViewHolder(itemView) {
    fun bindView(latestphotos: Latest_photos) {
        itemView.tv_date.text = latestphotos.earth_date
        itemView.tv_photo_id.text = latestphotos.photos_id.toString()

        Glide.with(itemView.context)
            .load(latestphotos.img_src)
            .into(itemView.iv_photo)

        itemView.iv_photo.setOnLongClickListener {
            itemViewClickListener.onItemViewLongClick(latestphotos)
            return@setOnLongClickListener true
        }
        itemView.iv_photo.setOnClickListener {
            itemViewClickListener.onItemViewClick(latestphotos)
        }
    }
}