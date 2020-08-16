package com.example.cb.test.net

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:08
 * @Desc :
 * ====================================================
 */
data class BaseResp<T>(
    var code: Int = 0,
    var msg: String = "",
    var `data`: T
)