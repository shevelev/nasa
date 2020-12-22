package ru.crashdev.nasa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.crashdev.nasa.R

/*
1. сделать 1 страницу с recicleview
2. настроить ретрофит с урлом наса + apikey
3. сделать модели из apikey
4. подключить glide
5. сделать адаптер с текстом+фото
6. настроить адаптер на данные из ретрофита, брать картинку+возможно текст
--пока здесь
7. подключить репозиторий с 2 запросами, 1 на локальный бд, 2 на запрос к апи
8. настроить репозиторий с проверкой по ид или по другому уникальному параметру
9. обработка ошибок, если нет интернета
10. по длительному нажатию раскрывать картинку на весь экран.
11. обновление по кнопке?
12. реф и красота
 */

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NasaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(NasaViewModel::class.java)
        rv_main.layoutManager = GridLayoutManager(this,2)
        Log.d("qwe", "start")

        viewModel.load()

        viewModel.listPhotos.observe(this, {
            Log.d("qwe", "size: ${it.size}")
            rv_main.adapter = NasaAdapter(it)
        })
    }
}