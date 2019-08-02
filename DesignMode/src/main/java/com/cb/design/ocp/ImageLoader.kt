package com.cb.design.ocp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class ImageLoader {
    //默认为内存缓存
    private var imageCache: ImageCache = MemoryCache()
    //线程池
    val mExecutorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors())
    val handler = Handler(Looper.getMainLooper())

    private fun updateImageImageView(imageView: ImageView, bitmap: Bitmap) {
        handler.post(object : Runnable {
            override fun run() {
                imageView.setImageBitmap(bitmap)
            }

        })
    }

    /**
     * 设置缓存方法
     */
    fun setImageCache(cache: ImageCache) {
        imageCache = cache
    }

    fun displayImage(imageUrl: String, imageView: ImageView) {
        val bitmap = imageCache.get(imageUrl)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
            return
        }
        submitLoadRequest(imageUrl, imageView)
    }

    private fun submitLoadRequest(imageUrl: String, imageView: ImageView) {
        imageView.tag = imageUrl
        mExecutorService.submit(object : Runnable {
            override fun run() {
                val bitmap: Bitmap = downloadImage(imageUrl) ?: return
                if (imageView.tag == imageUrl) {
                    updateImageImageView(imageView, bitmap)
                }
                imageCache.put(imageUrl, bitmap)
            }
        })
    }

    /**
     * 下载图片
     */
    private fun downloadImage(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        val url = URL(url)
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        bitmap = BitmapFactory.decodeStream(conn.inputStream)
        conn.disconnect()
        return bitmap
    }

}