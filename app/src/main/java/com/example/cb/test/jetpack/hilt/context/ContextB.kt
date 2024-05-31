package com.example.cb.test.jetpack.hilt.context

import android.content.Context
import androidx.fragment.app.FragmentActivity
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author: cb
 * @date: 2024/5/31 10:48
 * @desc: 描述
 */
class ContextB @Inject constructor(@ActivityContext private val context: Context) {
    //构造方法也可以直接接收Activity,如下
//class ContextB @Inject constructor( private val context: FragmentActivity) {

    fun logString() {
        val s = context.getString(R.string.app_name)
        XLogUtils.i("joker ActivityContext:$s");
    }
}