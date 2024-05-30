package com.example.cb.test.jetpack.hilt.provider

import cn.sccl.xlibrary.kotlin.lazyNone
import javax.inject.Inject

/**
 * @author: cb
 * @date: 2024/5/30 16:49
 * @desc: 描述
 */
class HiltProvider @Inject constructor(
    private val hiltChildProvider: HiltChildProvider,
    private val hiltChildProvider2: HiltChildProvider2
) {


    private val aa by lazyNone { "sadas" }

    fun getValue1(): String {
        return "我是value1"
    }

    fun getChildValue1(): String {
        return hiltChildProvider.getChildValue1()
    }

    fun getSecondChildValue1(): String {
        return hiltChildProvider2.getChildValue1()
    }
}