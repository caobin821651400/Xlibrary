package com.example.cb.test.utils

import com.google.gson.Gson
import org.json.JSONArray

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/13 17:37
 * @Desc :Kotlin 扩展方法
 * ====================================================
 */

val AppGson=Gson()

/**
 * 全局共享方法
 */
fun getToken(): String {
    return TOKEM
}

/**
 * 静态常量
 */
const val TOKEM: String = "i am token";

/**
 * JSONArray为空
 */
fun JSONArray?.isNullOrEmpty(): Boolean {
    return this == null || this.length() == 0
}

/**
 * JSONArray 不为空并且有数据
 */
fun JSONArray?.isNoEmpty(): Boolean {
    return this != null && this.length() > 0
}

/**
 * JSONArray循环
 */
fun <T> JSONArray.forEach(action: (T) -> Unit): Unit {
    if (this.isNullOrEmpty()) return
    for (i in 0 until this.length()) action(this[i] as T)
}

/**
 * JSONArray循环 带位置
 */
fun <T> JSONArray.forEachIndex(action: (index: Int, T) -> Unit): Unit {
    if (this.isNullOrEmpty()) return
    for (i in 0 until this.length()) {
        action(i, this[i] as T)
    }
}