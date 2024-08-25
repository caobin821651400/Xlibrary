package com.example.cb.test.ui.custom_view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import cn.sccl.xlibrary.utils.XLogUtils
import kotlin.math.min

/**
 * ====================================================
 * @User :caobin
 * @Date :2021/4/4 21:23
 * @Desc :自定义View
 * ====================================================
 */
class AView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onTouchEvent(event: MotionEvent): Boolean {
        XLogUtils.e("AView->$event")
        if (event.action==MotionEvent.ACTION_DOWN){
            return true
        }
        return super.onTouchEvent(event)
    }
}