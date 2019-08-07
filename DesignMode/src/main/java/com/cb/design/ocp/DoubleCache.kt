package com.cb.design.ocp

import android.graphics.Bitmap

/**
 * ====================================================
 * @User :caobin
 * @Date :2019/8/1 10:06
 * @Desc :双重缓存
 * ====================================================
 */
class DoubleCache : ImageCache {

    private val diskCache = DiskCache()
    private val memoryCache = MemoryCache()
    private var isEnableDiskCache = true//允许SD缓存
    private var isEnableMemoryCache = true//允许内存缓存

    override fun get(url: String): Bitmap? {
        return if (isEnableDiskCache && isEnableMemoryCache) {
            doubleCache(url)
        } else if (!isEnableDiskCache && isEnableMemoryCache) {
            memoryCache.get(url)
        } else if (isEnableDiskCache && !isEnableMemoryCache) {
            diskCache.get(url)
        } else {
            null
        }
    }

    override fun put(url: String, bitmap: Bitmap) {
        memoryCache.put(url, bitmap)
        diskCache.put(url, bitmap)
    }

    /**
     * 两种缓存
     */
    private fun doubleCache(url: String): Bitmap? {
        var bitmap = memoryCache.get(url)
        if (bitmap == null) {
            bitmap = diskCache.get(url)
        }
        return bitmap
    }
}