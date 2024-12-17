package com.example.cb.test.bubble

import android.content.Intent
import android.widget.Button
import cn.sccl.xlibrary.kotlin.lazyNone
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity

/**
 *
 * @author cb
 * @date 2024/12/17
 */
class BubbleMainActivity : BaseActivity() {

    private val button1: Button by lazyNone { findViewById(R.id.button1) }
    private val button2: Button by lazyNone { findViewById(R.id.button2) }

    override fun getLayoutId() = R.layout.activity_bubble_main

    override fun initUI() {
        setHeaderTitle("动画")
    }

    override fun initEvent() {
        button1.setOnClickListener {
            startActivity(Intent(this, BubbleActivity::class.java))
        }
        button2.setOnClickListener { }
    }
}