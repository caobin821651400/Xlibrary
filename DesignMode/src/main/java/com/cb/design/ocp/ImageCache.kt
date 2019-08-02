package com.cb.design.ocp

import android.graphics.Bitmap

/**
 * ====================================================
 * @User :caobin
 * @Date :2019/7/29 16:46
 * @Desc :缓存类抽象出来
 * ====================================================
 */
interface ImageCache {

    fun get(url: String): Bitmap?

    fun put(url: String, bitmap: Bitmap): Unit
}