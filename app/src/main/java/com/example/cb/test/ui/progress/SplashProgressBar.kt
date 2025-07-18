package com.example.cb.test.ui.progress

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import cn.sccl.xlibrary.kotlin.lazyNone

/**
 * @author: cb
 * @date: 2025/1/17
 * @desc: 描述
 */
class SplashProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val bgRectF = RectF()
    private val progressRectF = RectF()
    private val mBackgroundPaint = Paint() //背景画笔
    private val mProgressPaint: Paint //进度画笔
    private val mProgressGradientPaint: Paint //进度渐变画笔

    private var mViewWidth = 0
    private var mViewHeight = 0
    private var mMaxProgress = 100 //最大进度
    private var mProgress = 0 //当前进度

    private val gradientMatrix = Matrix()
    private val progressGradient by lazyNone {
        LinearGradient(
            0f, 0f, mViewWidth.toFloat(), 0f,
            intArrayOf(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                Color.WHITE
            ),
            floatArrayOf(
                0f,
                0.6f,
                1f
            ),
            Shader.TileMode.CLAMP
        )
    }

    init {
        mBackgroundPaint.isAntiAlias = true
        mBackgroundPaint.color = Color.parseColor("#33FFFFFF")
        setBackgroundResource(0) //移除设置的背景资源

        mProgressPaint = Paint() //进度条画笔
        mProgressPaint.isAntiAlias = true
        mProgressPaint.color = Color.parseColor("#F090A1")

        mProgressGradientPaint = Paint() //进度条画笔
        mProgressGradientPaint.isAntiAlias = true
        mProgressGradientPaint.maskFilter = BlurMaskFilter(4f, BlurMaskFilter.Blur.INNER)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mViewWidth = measuredWidth
        mViewHeight = measuredHeight
        bgRectF.set(0f, 0f, mViewWidth.toFloat(), mViewHeight.toFloat())

        //测量完后再设置
        mProgressGradientPaint.shader = progressGradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画背景
        canvas.drawRoundRect(
            bgRectF,
            (mViewHeight / 2).toFloat(),
            (mViewHeight / 2).toFloat(),
            mBackgroundPaint
        )
        //画进度条
        val width = (mViewWidth * (mProgress * 1.0f / mMaxProgress) + 0.5).toInt()
        if (width <= mViewHeight) { //圆形
            canvas.drawCircle(
                (width / 2).toFloat(),
                (mViewHeight / 2).toFloat(),
                (width / 2).toFloat(),
                mProgressPaint
            )
        } else {
            progressRectF.set(0f, 0f, width.toFloat(), mViewHeight.toFloat())
            canvas.drawRoundRect(
                progressRectF,
                (mViewHeight / 2).toFloat(),
                (mViewHeight / 2).toFloat(),
                mProgressPaint
            )

            // 更新 shader matrix，控制渐变移动/缩放
            gradientMatrix.reset()
            val scaleX = width / mViewWidth.toFloat()
            gradientMatrix.setScale(scaleX, 1f)
            progressGradient.setLocalMatrix(gradientMatrix)
            canvas.drawRoundRect(
                progressRectF,
                (mViewHeight / 2).toFloat(),
                (mViewHeight / 2).toFloat(),
                mProgressGradientPaint
            )
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewHeight = h
        invalidate()
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    fun setProgress(progress: Int) {
        var progress = progress
        progress = if (progress >= 0) progress else 0
        progress = if (progress <= mMaxProgress) progress else mMaxProgress
        mProgress = progress
        invalidate()
    }

    /**
     * 设置最大进度
     *
     * @param maxProgress
     */
    fun setMaxProgress(maxProgress: Int) {
        mMaxProgress = maxProgress
    }

    /**
     * 设置背景颜色
     */
    fun setBgColor(@ColorInt color: Int) {
        mBackgroundPaint.color = color
    }

    /**
     * 设置进度条颜色
     */
    fun setProgressColor(@ColorInt color: Int) {
        mProgressPaint.color = color
    }
}
