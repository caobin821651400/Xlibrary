package com.example.cb.test.jetpack.hilt.ok

import cn.sccl.xlibrary.utils.XLogUtils
import javax.inject.Inject

/**
 * @author: cb
 * @date: 2024/5/30 18:21
 * @desc: 描述
 */
class OkHttpIntercept @Inject constructor() {

    fun intercept(){
        XLogUtils.e("joker 拦截器工作")
    }
}