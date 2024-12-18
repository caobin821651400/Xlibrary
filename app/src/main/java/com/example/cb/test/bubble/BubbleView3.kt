package com.example.cb.test.bubble

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import cn.sccl.xlibrary.kotlin.lazyNone
import kotlin.random.Random

/**
 * 气泡动效3ø
 * @author: cb
 * @date: 2024/12/17
 * @desc: 描述
 */
class BubbleView3 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val WIDTH_PROPORTION = 9
        const val HEIGHT_PROPORTION = 5
    }

    private val debugBubbleCreatePaint by lazyNone {
        Paint().apply {
            style = Paint.Style.FILL
            color = Color.GREEN
        }
    }

    /**
     * 气泡产生的区域
     */
    private val bubbleCreateRect by lazyNone { Rect() }

    /**
     * 气泡数量 5-8
     */
    private val segmentCount by lazyNone { Random.nextInt(5, 8) }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获取宽度的测量模式和建议的宽度
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        // 使用宽度为准计算高度
        val calculatedHeight = widthSize * HEIGHT_PROPORTION / WIDTH_PROPORTION
        MeasureSpec.makeMeasureSpec(calculatedHeight, widthMode)
        // 强制设置宽高
        setMeasuredDimension(widthSize, calculatedHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        reset(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawDebug(canvas)
    }

    /**
     * debug功能
     * @param canvas Canvas
     */
    private fun drawDebug(canvas: Canvas) {
        //气泡产生的区域
        canvas.drawRect(bubbleCreateRect, debugBubbleCreatePaint)
    }

    /**
     * 初始化等分圆半径
     */
    private fun initializeCreateRect() {
        //UI是36*4的矩形区域内
        val top = height - width / 9
        bubbleCreateRect.set(0, top, width, height)
    }

    /**
     * 重置
     * @param needInvalidate Boolean
     */
    fun reset(needInvalidate: Boolean = false) {
        initializeCreateRect()
        if (needInvalidate) {
            invalidate()
        }
    }
}