package com.example.cb.test

import android.os.Bundle
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.base.BaseActivity

class KotlinActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        XLogUtils.d("${initView(1, 1)}  安安")
    }

    fun initView(a: Int, b: Int): Int = when {
        a > 1 -> b
        a < 1 -> a
        a < -1 -> a + 3
        else -> 0
    }
}
