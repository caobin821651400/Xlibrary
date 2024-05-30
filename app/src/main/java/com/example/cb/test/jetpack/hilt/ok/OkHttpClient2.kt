package com.example.cb.test.jetpack.hilt.ok

import cn.sccl.xlibrary.utils.XLogUtils

/**
 * @author: cb
 * @date: 2024/5/30 18:12
 * @desc: 模拟OkHttpClient 不能修改其构造方法
 */
class OkHttpClient2 {

    private var mOkHttpIntercept: OkHttpIntercept? = null

    fun request() {
        XLogUtils.v("joker 发送请求")
        mOkHttpIntercept?.intercept()
    }

    fun addIntercept(intercept:OkHttpIntercept) {
        mOkHttpIntercept=intercept
    }
}