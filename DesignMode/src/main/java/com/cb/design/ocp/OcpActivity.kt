package com.cb.design.ocp

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class OcpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //
        val loader = ImageLoader()
        //内存
        loader.setImageCache(MemoryCache())
        loader.setImageCache(DiskCache())
        loader.setImageCache(DoubleCache())
        loader.setImageCache(object : ImageCache {
            override fun get(url: String): Bitmap? {
                return null
            }

            override fun put(url: String, bitmap: Bitmap) {
            }

        })

        loader.displayImage("", ImageView(this))

    }
}
