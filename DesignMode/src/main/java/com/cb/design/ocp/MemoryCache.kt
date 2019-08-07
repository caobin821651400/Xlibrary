package com.cb.design.ocp

import android.graphics.Bitmap
import android.util.LruCache

/**
 * ====================================================
 * @User :caobin
 * @Date :2019/7/29 16:47
 * @Desc :内存缓存
 * ====================================================
 */
class MemoryCache : ImageCache {

    /**
     * LRU缓存集合
     */
    private var mMemoryCache: LruCache<String, Bitmap>? = null

    /**
     * 构造
     */
    init {
        //计算最大内存空间
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        //取四分之一
        val cacheSize = maxMemory / 4
        mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.rowBytes * bitmap.height / 1024
            }
        }
    }

    override fun get(url: String): Bitmap? {
        return mMemoryCache?.get(url)
    }

    override fun put(url: String, bitmap: Bitmap) {
        mMemoryCache!!.put(url, bitmap)
    }
}