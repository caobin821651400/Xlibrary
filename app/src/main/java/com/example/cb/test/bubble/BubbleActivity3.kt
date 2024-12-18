package com.example.cb.test.bubble

import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import cn.sccl.xlibrary.kotlin.lazyNone
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity

/**
 *
 * @author cb
 * @date 2024/12/16
 */
class BubbleActivity3 : BaseActivity() {

    private val timeEdit: EditText by lazyNone { findViewById(R.id.time_edit2) }
    private val timeEdit1: EditText by lazyNone { findViewById(R.id.time_edit1) }
    private val timeEdit3: EditText by lazyNone { findViewById(R.id.time_edit5) }
    private val colorEdit: EditText by lazyNone { findViewById(R.id.time_edit3) }
    private val colorEdit2: EditText by lazyNone { findViewById(R.id.time_edit4) }
    private val rootView: LinearLayout by lazyNone { findViewById(R.id.root_view) }
    private val mBubbleView3: BubbleView3 by lazyNone { findViewById(R.id.view) }
    private val startBtn: Button by lazyNone { findViewById(R.id.start) }
    private val resetBtn: Button by lazyNone { findViewById(R.id.reset) }
    private val debugBtn: Button by lazyNone { findViewById(R.id.debug) }
    private val bgBtn: Button by lazyNone { findViewById(R.id.bgBtn) }
    private var showDebug = false

    override fun getLayoutId() = R.layout.activity_bubble_view3

    override fun initUI() {
        setHeaderTitle("动画3")
    }

    override fun initEvent() {
        debugBtn.setOnClickListener {
            showDebug = !showDebug
            BubbleView3.showDebug = showDebug
            debugBtn.text = "debug enable:$showDebug"
        }

        startBtn.setOnClickListener {
            val timeText1 = timeEdit1.text.toString()
            if (timeText1.isNotEmpty()) {
                val time1 = timeText1.toLong()
                BubbleView3.animHiddenDuration = time1
            }

            val timeText2 = timeEdit.text.toString()
            if (timeText2.isNotEmpty()) {
                val time2 = timeText2.toLong()
                BubbleView3.animTotalDuration = time2
            }

            val timeText3 = timeEdit3.text.toString()
            if (timeText3.isNotEmpty()) {
                val time3 = timeText3.toLong()
                BubbleView3.animTotalDuration = time3
            }

            val color = colorEdit.text.toString()
            if (color.isNotEmpty()) {
                try {
                    mBubbleView3.setCircleColor(Color.parseColor("#${color}"))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            mBubbleView3.startAnimation()
        }
        resetBtn.setOnClickListener {
            mBubbleView3.reset(true)
        }

        bgBtn.setOnClickListener {
            val color = colorEdit2.text.toString()
            if (color.isNotEmpty()) {
                try {
                    rootView.setBackgroundColor(Color.parseColor("#${color}"))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}