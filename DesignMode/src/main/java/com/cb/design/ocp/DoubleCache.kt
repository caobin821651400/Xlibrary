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

    val diskCache = DiskCache()
    val memoryCache = MemoryCache()
    var isEnableDiskCache = true//允许SD缓存
    var isEnableMemoryCache = true//允许内存缓存

    override fun get(url: String): Bitmap? {
        if (isEnableDiskCache && isEnableMemoryCache) {
            return doubleCache(url)
        } else if (!isEnableDiskCache && isEnableMemoryCache) {
            return memoryCache.get(url)
        } else if (isEnableDiskCache && !isEnableMemoryCache) {
            return diskCache.get(url)
        } else {
            return null
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