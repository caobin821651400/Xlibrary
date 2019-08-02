package com.cb.design.ocp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.FileOutputStream

/**
 * ====================================================
 * @User :caobin
 * @Date :2019/7/30 14:34
 * @Desc :磁盘缓存
 * ====================================================
 */
class DiskCache : ImageCache {

    /**
     * 相当于JAVA的静态常亮
     */
    companion object {
        val cacheDir = Environment.getExternalStorageDirectory().absolutePath + "/data"
    }


    override fun put(url: String, bitmap: Bitmap) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(cacheDir)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileOutputStream?.close()
        }
    }

    override fun get(url: String): Bitmap? {
        return BitmapFactory.decodeFile(cacheDir)
    }
}