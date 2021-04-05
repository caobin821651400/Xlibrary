package com.example.cb.test.ui.custom_view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
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
class CustomizeView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init { }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        XLogUtils.d("onLayout")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        XLogUtils.d("onMeasure")

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val screenWidth = 500//假设屏幕宽度

        var width = when (widthMode) {
            MeasureSpec.AT_MOST -> min(screenWidth, widthSize)
            MeasureSpec.UNSPECIFIED -> screenWidth
            MeasureSpec.EXACTLY -> widthSize
            else -> widthSize //不写要报错
        }
        setMeasuredDimension(width, width)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        XLogUtils.d("onDraw")
    }
}