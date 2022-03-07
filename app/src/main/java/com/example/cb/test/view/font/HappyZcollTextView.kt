package com.example.cb.test.view.font

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import cn.sccl.xlibrary.utils.XDensityUtils


/**
 * 自定义字体TextView
 */
class HappyZcollTextView : AppCompatTextView {
    private lateinit var outlineTextView: TextView

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) {
        outlineTextView = TextView(context, attrs, defStyle)
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        super.setLayoutParams(params)
        outlineTextView.layoutParams = params
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //设置轮廓文字
        val outlineText = outlineTextView.text
        if (outlineText == null || outlineText != this.text) {
            outlineTextView.text = text
            outlineTextView.typeface = this.typeface
            postInvalidate()
        }
        outlineTextView.measure(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initStrokeTextView()
        applyCustomFont(context)
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        outlineTextView.layout(left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        val outlineText = outlineTextView.text
        //两个TextView上的文字必须一致
        if (outlineText == null || outlineText != this.text) {
            outlineTextView.text = text
        }
        outlineTextView.gravity = gravity
        outlineTextView.draw(canvas)
        super.onDraw(canvas)
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        outlineTextView.setTextSize(unit, size)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        outlineTextView.setPadding(left, top, right, bottom)
    }

    /**
     * 自定义字体
     * @param context Context
     */
    private fun applyCustomFont(context: Context) {
        var customFont: Typeface = Typeface.createFromAsset(context.getAssets(), "ZhanKuKuaiLeTi2016XiuDingBan.ttf");
        typeface = customFont
        outlineTextView.typeface = customFont
    }

    private fun initStrokeTextView(
    ) {
        val paint = outlineTextView.paint
        paint.isAntiAlias = true
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.strokeWidth = 25f //描边宽度
        paint.style = Paint.Style.STROKE
//        outlineTextView.setTextColor(Color.parseColor("#f00000")) //描边颜色


        val mLinearGradient = LinearGradient(
            0f,
            0f,
            0f,
//            mRectF!!.bottom,
            measuredHeight.toFloat()/3,
            Color.RED,
            Color.YELLOW,
            Shader.TileMode.MIRROR
        )
        paint.setShader(mLinearGradient)
        outlineTextView.gravity = gravity
    }
}