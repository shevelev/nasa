package ru.crashdev.nasa.ui.main

import androidx.recyclerview.widget.RecyclerView
import ru.crashdev.nasa.databinding.NasaItemGridPhotosBinding
import ru.crashdev.nasa.repository.model.Latest_photos

class NasaListViewHolder(private val binding: NasaItemGridPhotosBinding, viewModel: NasaViewModel) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.viewModel = viewModel
    }

    fun bind(latestPhotos: Latest_photos) {
        binding.apply {
            viewModel?.photos = latestPhotos
            executePendingBindings()
        }
    }
}