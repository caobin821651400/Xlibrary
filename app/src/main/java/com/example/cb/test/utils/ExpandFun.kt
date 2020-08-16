package com.example.cb.test.utils

import android.graphics.Color
import android.widget.TextView
import com.example.cb.test.net.BaseResp


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