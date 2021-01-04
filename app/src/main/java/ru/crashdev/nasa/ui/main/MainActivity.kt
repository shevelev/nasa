package ru.crashdev.nasa.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.crashdev.nasa.R
import ru.crashdev.nasa.databinding.ActivityMainBinding
import ru.crashdev.nasa.repository.model.Latest_photos
import ru.crashdev.nasa.ui.zoom.zoomImage
import ru.crashdev.nasa.utils.ApiException
import ru.crashdev.nasa.utils.ItemViewClickListener
import ru.crashdev.nasa.utils.NoInternetException
import ru.crashdev.nasa.utils.isOnline

class MainActivity : AppCompatActivity(), ItemViewClickListener {

    private lateinit var viewModel: NasaViewModel
    private val _adapter = NasaAdapter(mutableListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(NasaViewModel::class.java)

        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = _adapter
        }

        viewModel.getSavedPhotos().observe(this, { photos ->
            photos?.let {
                _adapter.setPhotos(photos)
            }
        })

        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (isOnline()) {
                    val remote = viewModel.loadRemote()
                    if (remote.latest_photos.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            viewModel.saveToLocal(remote.latest_photos)
                        }
                    }
                } else {
                    throw NoInternetException(getString(R.string.noInternet))
                }
            } catch (error: ApiException) {
                Snackbar.make(root, "${error.message}", Snackbar.LENGTH_SHORT).show()
            } catch (error: NoInternetException) {
                Snackbar.make(root, "${error.message}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    override fun onItemViewLongClick(latestphotos: Latest_photos) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.deleteImage(latestphotos.photos_id)
        }
        Snackbar.make(rv_main, getString(R.string.deleteOrNot), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.deleteYes)) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.undo(latestphotos)
                }
            }
            .show()
    }

    override fun onItemViewClick(latestphotos: Latest_photos) {
        val intent = Intent(this, zoomImage::class.java)
        intent.putExtra("image", latestphotos.img_src)
        startActivity(intent)
    }
}