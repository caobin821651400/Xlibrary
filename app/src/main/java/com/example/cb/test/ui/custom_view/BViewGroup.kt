package com.example.cb.test.ui.custom_view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import cn.sccl.xlibrary.utils.XLogUtils
import kotlin.math.min

/**
 * ====================================================
 * @User :caobin
 * @Date :2021/4/4 21:23
 * @Desc :自定义View
 * ====================================================
 */
class BViewGroup @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    override fun onTouchEvent(event: MotionEvent): Boolean {


        post {  }
        XLogUtils.d("BViewGroup->$event")
        return super.onTouchEvent(event)
    }
}