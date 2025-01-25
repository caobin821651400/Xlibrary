package com.example.cb.test.bubble

import android.content.Intent
import android.widget.Button
import cn.sccl.xlibrary.kotlin.lazyNone
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.ui.StarShowActivity
import com.example.cb.test.ui.progress.ProgressActivity

/**
 *
 * @author cb
 * @date 2024/12/17
 */
class CustomizeViewActivity : BaseActivity() {

    private val button1: Button by lazyNone { findViewById(R.id.button1) }
    private val button2: Button by lazyNone { findViewById(R.id.button2) }
    private val button3: Button by lazyNone { findViewById(R.id.button3) }
    private val button4: Button by lazyNone { findViewById(R.id.button4) }
    private val button5: Button by lazyNone { findViewById(R.id.button5) }

    override fun getLayoutId() = R.layout.activity_customize_view

    override fun initUI() {
        setHeaderTitle("自定义view")
    }

    override fun initEvent() {
        button1.setOnClickListener {
            startActivity(Intent(this, BubbleActivity::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(this, BubbleActivity2::class.java))
        }
        button3.setOnClickListener {
            startActivity(Intent(this, BubbleActivity3::class.java))
        }

        button4.setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }
        button5.setOnClickListener {
            startActivity(Intent(this, StarShowActivity::class.java))
        }
    }
}