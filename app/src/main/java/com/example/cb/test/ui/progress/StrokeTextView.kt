package com.example.cb.test.ui.progress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

open class StrokeTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        backGroundText = AppCompatTextView(context)
        backGroundText?.maxLines = 2
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        backGroundText = AppCompatTextView(context, attrs)
        backGroundText?.maxLines = 2
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        backGroundText = AppCompatTextView(context, attrs, defStyleAttr)
        backGroundText?.maxLines = 2
    }

    private var backGroundText: TextView? = null

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        //同步布局参数
        backGroundText?.layoutParams = params
        super.setLayoutParams(params)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val tt = backGroundText!!.text
        if (tt == null || tt != this.text) {
            backGroundText?.text = text
            this.postInvalidate()
        }
        backGroundText?.measure(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        backGroundText?.layout(left, top, right, bottom)
        super.onLayout(changed, left, top, right, bottom)
    }


    override fun onDraw(canvas: Canvas) {
        init()
        backGroundText?.draw(canvas)
        super.onDraw(canvas)
    }

    fun init() {
        val tp1 = backGroundText?.paint ?: return
        tp1.strokeWidth = 10F
        tp1.style = Paint.Style.FILL_AND_STROKE
        backGroundText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.textSize)
        backGroundText?.setTextColor(Color.WHITE)
        backGroundText?.gravity = gravity
    }
}