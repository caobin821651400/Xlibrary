package com.example.cb.test.jetpack.hilt.impl

import cn.sccl.xlibrary.utils.XLogUtils
import javax.inject.Inject

/**
 * @author: cb
 * @date: 2024/5/30 17:15
 * @desc: 描述
 */
class HiltImpl @Inject constructor() : HiltInterface {

    override fun doSomeThing() {
        XLogUtils.v("joker HiltImpl doSomeThing");
    }
}