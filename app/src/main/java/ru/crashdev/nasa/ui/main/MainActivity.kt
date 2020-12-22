package ru.crashdev.nasa.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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

/*
1. сделать 1 страницу с recicleview
2. настроить ретрофит с урлом наса + apikey
3. сделать модели из apikey
4. подключить glide
5. сделать адаптер с текстом+фото
6. настроить адаптер на данные из ретрофита, брать картинку+возможно текст
7. подключить репозиторий с 2 запросами, 1 на локальный бд, 2 на запрос к апи
8. настроить репозиторий с проверкой по ид или по другому уникальному параметру
--пока здесь
9. обработка ошибок, если нет интернета
10. по длительному нажатию раскрывать картинку на весь экран.
11. обновление по кнопке?
12. реф и красота
 */

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NasaViewModel
    val listPhotos: MutableLiveData<List<Photos>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(NasaViewModel::class.java)
        rv_main.layoutManager = GridLayoutManager(this, 2)
        Log.d("qwe", "start")

        val localRepository = LocalRepository(applicationContext)
        val remoteRepository = RemoteRepository(applicationContext)
        val repository = DataRepository(localRepository, remoteRepository)
        viewModel.setRepository(repository)

        loadData()

    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {

            val local = viewModel.loadLocal()
            local.let {
                if (it != null) {
                    listPhotos.postValue(it.sortedBy { it.earth_date })

                    withContext(Dispatchers.Main) {
                        rv_main.adapter = NasaAdapter(it)
                        Toast.makeText(
                            applicationContext,
                            "загружено из локальной",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }


            val remote = viewModel.loadRemote()

            remote.body()?.photos.let {
                if (it != null) {
                    listPhotos.postValue(it.sortedBy { it.earth_date })
                    withContext(Dispatchers.Main) {
                        rv_main.adapter = NasaAdapter(it)
                        viewModel.saveToLocal(it)
                        Toast.makeText(
                            applicationContext,
                            "загружено из удаленной",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

        }
    }
}