package ru.crashdev.nasa.ui.zoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_zoom_image.*
import ru.crashdev.nasa.R

class zoomImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_image)

        val image = intent.getStringExtra("image")

        Glide.with(this).load(image).into(imageView)
    }
}