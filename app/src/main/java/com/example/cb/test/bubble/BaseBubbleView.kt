package com.example.cb.test.bubble

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import cn.sccl.xlibrary.kotlin.lazyNone
import kotlin.math.cos
import kotlin.math.sqrt
import kotlin.random.Random

/**
 *
 * @author cb
 * @date 2024/12/17
 */
abstract class BaseBubbleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    companion object {
        // TODO: caobin 2024/12/17
//        const val ALPHA_ANIM_TIME = 40L
//        const val TRANSLATE_ANIM_TIME = 440L

        var ALPHA_ANIM_TIME = 200L
        var TRANSLATE_ANIM_TIME = 600L
        var showDebug = false
    }

    protected val debugCirclePaint by lazyNone { Paint(Paint.ANTI_ALIAS_FLAG) }
    protected val debugLinePaint by lazyNone { Paint(Paint.ANTI_ALIAS_FLAG) }

    protected var alphaAnim: ValueAnimator? = null
    protected var translateAnimator: ValueAnimator? = null

    protected var centerX = 0f
    protected var centerY = 0f
    protected var enableDraw = false


    /**
     * 画笔初始透明度
     * @return Int
     */
    abstract fun getCirclePaintStartAlpha(): Int

    /**
     * 随机圆的颜色
     */
    private var mCircleColor = Color.GRAY

    /**
     * 随机圆画笔
     */
    protected val mCirclePaint by lazyNone {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            alpha = getCirclePaintStartAlpha()
            color = mCircleColor
        }
    }

    /**
     * 扇形等分
     */
    protected val segmentCount by lazyNone { Random.nextInt(8, 12) }

    /**
     * 每个扇形角度
     */
    protected val angleStep by lazyNone { 360f / segmentCount }


    /**
     * 圆移动过程中最大移动距离限制
     */
    protected val maxCircleTranslateDistance = mutableListOf<Float>()

    /**
     * 随机圆的半径集合
     */
    protected val randomCircleRadius = mutableListOf<Float>()


    /**
     * 设置随机圆的颜色
     * @param color Int
     */
    fun setCircleColor(@ColorInt color: Int) {
        mCircleColor = BubbleUtils.calculateColor(color)
        mCirclePaint.color = mCircleColor
        invalidate()
    }



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2f
        centerY = height / 2f
    }

    /**
     * 等腰三角形，计算第三条边长度
     * @param length         Float 等边的长度
     * @param angleInDegrees Float  角度
     * @return Int
     */
    protected fun calculateLevelMaxRadius(length: Float, angleInDegrees: Float): Int {
        // 将角度转换为弧度
        val angleInRadians = Math.toRadians(angleInDegrees.toDouble())
        // 计算第三条边长度
        return ((length * sqrt(2 * (1 - cos(angleInRadians)))).toInt())
    }

    /**
     * 动画结束后消失
     */
    protected fun animEnd() {
        (parent as? ViewGroup)?.removeView(this)
    }

    protected fun cancelAllAnim() {
        alphaAnim?.cancel()
        translateAnimator?.cancel()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAllAnim()
    }
}