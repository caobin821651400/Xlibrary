package com.example.cb.test.utils

import android.graphics.Color
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cb.test.net.BaseResp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/****
 * 扩展函数
 */
fun TextView.txt1(txt: String) {
    text = txt
    setTextColor(Color.parseColor("#ff0000"))
}

/**
 * 解析接口返回是否成功
 */
fun <T> BaseResp<T>.dataConvert(): T {
    if (code == 1000) {
        return data
    } else {
        throw Exception(msg)
    }
}

inline fun Fragment.launchAndRepeatWithLifeCycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(state) { block() }
    }
}