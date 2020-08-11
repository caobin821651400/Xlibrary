package com.example.cb.test.jetpack.hilt.other

import javax.inject.Inject
/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 21:34
 * @Desc :
 * ====================================================
 */
class HiltParamsObject @Inject constructor(private val name: String) {

    fun getData(): String {
        return name
    }

}