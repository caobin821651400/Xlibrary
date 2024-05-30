package com.example.cb.test.jetpack.hilt.provider

import javax.inject.Inject

/**
 * @author: cb
 * @date: 2024/5/30 16:49
 * @desc: 描述
 */
class HiltProvider @Inject constructor(
    private val hiltChildProvider: HiltChildProvider
) {
    fun getValue1(): String {
        return "我是value1"
    }

    fun getChildValue1():String{
        return hiltChildProvider.getChildValue1()
    }
}