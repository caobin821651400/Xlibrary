package com.example.cb.test.utils

import android.graphics.Color
import android.widget.TextView


/****
 * 扩展函数
 */
fun TextView.txt1(txt: String) {
    text = txt
    setTextColor(Color.parseColor("#ff0000"))
}