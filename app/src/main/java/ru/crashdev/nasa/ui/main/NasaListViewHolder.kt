package ru.crashdev.nasa.ui.main

import androidx.recyclerview.widget.RecyclerView
import ru.crashdev.nasa.databinding.NasaItemGridPhotosBinding
import ru.crashdev.nasa.repository.model.Latest_photos

class NasaListViewHolder(private val binding: NasaItemGridPhotosBinding) :
    RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = NasaViewModel()
        }

    fun bind(latestPhotos: Latest_photos) {
        binding.apply {
            viewModel?.photos = latestPhotos
            executePendingBindings()
        }
    }
}