package com.example.cb.test.utils

import com.google.gson.Gson

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/13 17:37
 * @Desc :Kotlin 扩展方法
 * ====================================================
 */

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