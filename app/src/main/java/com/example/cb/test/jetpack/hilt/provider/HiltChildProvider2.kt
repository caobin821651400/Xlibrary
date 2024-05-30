package com.example.cb.test.jetpack.hilt.provider

import javax.inject.Inject

/**
 * @author: cb
 * @date: 2024/5/30 17:02
 * @desc: 描述
 */
class HiltChildProvider2 @Inject constructor() {

    fun getChildValue1(): String {
        return "我是第二个子value1"
    }

}