package com.example.cb.test.jetpack.hilt.context

import android.app.Application
import android.content.Context
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author: cb
 * @date: 2024/5/31 10:48
 * @desc: 描述
 */
class ContextA @Inject constructor(@ApplicationContext private val context: Context) {
    //构造方法也可以直接接收Activity,如下
//class ContextA @Inject constructor( private val context: Application) {

    fun logString() {
        val s = context.getString(R.string.app_name)
        XLogUtils.d("joker ApplicationContext:$s");
    }
}