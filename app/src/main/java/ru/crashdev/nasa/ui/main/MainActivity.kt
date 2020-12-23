package ru.crashdev.nasa.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.crashdev.nasa.R
import ru.crashdev.nasa.repository.DataRepository
import ru.crashdev.nasa.repository.local.LocalRepository
import ru.crashdev.nasa.repository.model.Photos
import ru.crashdev.nasa.repository.remote.RemoteRepository
import ru.crashdev.nasa.utils.ApiException
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
--пока здесь
10. по длительному нажатию раскрывать картинку на весь экран.
11. обновление по кнопке?
12. реф и красота
 */

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NasaViewModel
    private val listPhotos: MutableLiveData<List<Photos>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(NasaViewModel::class.java)
        rv_main.layoutManager = GridLayoutManager(this, 2)
        rv_main.adapter = NasaAdapter(listOf())
        //need di
        val localRepository = LocalRepository(applicationContext)
        val remoteRepository = RemoteRepository(applicationContext)
        val repository = DataRepository(localRepository, remoteRepository)
        viewModel.setRepository(repository)
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {

            try {
                if (isOnline()) {
                    val remote = viewModel.loadRemote()
                    if (remote.photos.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            viewModel.saveToLocal(remote.photos)
                            configureAdapterRecyclerView("Load remote DB", remote.photos)
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

            val local = viewModel.loadLocal()
            local.let {
                if (it != null) {
                    listPhotos.postValue(it.sortedBy { it.earth_date })

                    withContext(Dispatchers.Main) {
                        configureAdapterRecyclerView("Load locale DB", it)
                    }
                }
            }

        }
    }

    private fun configureAdapterRecyclerView(message: String, list: List<Photos>) {
        rv_main.adapter = NasaAdapter(list)
       // Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
    }
}