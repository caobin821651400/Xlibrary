package com.example.cb.test.jetpack.hilt.single

import cn.sccl.xlibrary.utils.XLogUtils

/**
 * @author: cb
 * @date: 2024/5/31 10:31
 * @desc: 描述
 */
class SingleA {


    fun log() {
        XLogUtils.v("joker SingleA：${System.identityHashCode(this)}");
    }
}