package ru.crashdev.nasa.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.crashdev.nasa.R
import ru.crashdev.nasa.repository.model.Latest_photos
import ru.crashdev.nasa.ui.zoom.zoomImage
import ru.crashdev.nasa.utils.ApiException
import ru.crashdev.nasa.utils.ItemViewClickListener
import ru.crashdev.nasa.utils.NoInternetException
import ru.crashdev.nasa.utils.isOnline

/*
1. сделать 1 страницу с recicleview
2. настроить ретрофит с урлом наса + apikey
3. сделать модели из apikey
4. подключить glide
5. сделать адаптер с текстом+фото
6. настроить адаптер на данные из ретрофита, брать картинку+возможно текст
7. подключить репозиторий с 2 запросами, 1 на локальный бд, 2 на запрос к апи
8. настроить репозиторий с проверкой по ид или по другому уникальному параметру
9. обработка ошибок, если нет интернета
10. по длительному нажатию удалять картинку из базы
11. заменить рефтрофит на okhttp3
12. по нажатию раскрывать картинку на весь экран.
--пока здесь
13. реф и красота
 */

class MainActivity : AppCompatActivity(), ItemViewClickListener {

    private lateinit var viewModel: NasaViewModel
    private val _adapter = NasaAdapter(mutableListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(NasaViewModel::class.java)
        rv_main.layoutManager = GridLayoutManager(this, 2)
        rv_main.adapter = _adapter

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
                    throw NoInternetException("Нет подключений")
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

        Log.d("qwe", "delete ${latestphotos.photos_id}")
    }

    override fun onItemViewClick(latestphotos: Latest_photos) {
        val intent = Intent(this, zoomImage::class.java)
        intent.putExtra("image", latestphotos.img_src)
        startActivity(intent)

        Log.d("qwe", "open ${latestphotos.photos_id}")
    }
}