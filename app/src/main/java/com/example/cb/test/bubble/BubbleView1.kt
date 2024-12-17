package com.example.cb.test.bubble

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import com.example.cb.test.BuildConfig
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class BubbleView1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseBubbleView(context, attrs, defStyleAttr) {

    /**
     * 气泡位移区域的圆半径
     */
    private var purpleCircleRadius = 0f

    /**
     * 气泡生成区域的圆半径
     */
    private var bigCircleRadius = 0f

    /**
     * 用户点击区域的最大圆半径
     */
    private var smallCircleRadius = 0f

    /**
     * 每个随机圆的动画进度
     */
    private val animationProgress = mutableListOf<Float>()

    override fun getCirclePaintStartAlpha(): Int {
        return 0
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        purpleCircleRadius = centerX * 1.0f
        bigCircleRadius = centerX / 3f * 2
        smallCircleRadius = centerX / 3f

        reset(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (BuildConfig.DEBUG && showDebug) {
            drawDebug(canvas)
        }

        if (!enableDraw) return

        //画随机圆
        for (i in 0 until segmentCount) {
            val startAngle = i * angleStep
            val midAngle = Math.toRadians((startAngle + angleStep / 2).toDouble())
            // 加载固定的圆半径
            val randomRadius = randomCircleRadius[i]
            // 动画进度
            val progress = animationProgress[i]
            // 计算起始位置和最大移动距离
            val startDistance = smallCircleRadius + randomRadius // 起始位置
            val maxAllowedDistance = maxCircleTranslateDistance[i] - randomRadius // 最大允许距离
            // 根据动画进度计算移动距离
            val adjustedDistance =
                startDistance + (maxAllowedDistance - startDistance) * progress
            // 动态计算圆的半径,半径从 randomRadius 缩减到 0
            val currentRadius = randomRadius * (1 - progress)
            // 如果圆已经完全缩小到0，跳过绘制
            if (currentRadius <= 0) continue
            // 计算圆的中心位置
            val circleCenterX = centerX + adjustedDistance * cos(midAngle).toFloat()
            val circleCenterY = centerY + adjustedDistance * sin(midAngle).toFloat()
            canvas.drawCircle(circleCenterX, circleCenterY, currentRadius, mCirclePaint)
        }
    }

    /**
     * debug功能
     * @param canvas Canvas
     */
    private fun drawDebug(canvas: Canvas) {
        // 1. 绘制紫色的大圆（最底层）
        debugCirclePaint.color = Color.MAGENTA
        debugCirclePaint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, purpleCircleRadius, debugCirclePaint)

        // 2. 绘制黄色的大圆
        debugCirclePaint.color = Color.YELLOW
        canvas.drawCircle(centerX, centerY, bigCircleRadius, debugCirclePaint)

        // 3. 绘制白色的小圆
        debugCirclePaint.color = Color.WHITE
        canvas.drawCircle(centerX, centerY, smallCircleRadius, debugCirclePaint)

        // 4. 绘制红色分割线
        debugLinePaint.color = Color.RED
        debugLinePaint.style = Paint.Style.STROKE
        debugLinePaint.strokeWidth = 1f
        for (i in 0 until segmentCount) {
            val angle = Math.toRadians((i * angleStep).toDouble())
            val endX = centerX + bigCircleRadius * cos(angle).toFloat()
            val endY = centerY + bigCircleRadius * sin(angle).toFloat()
            canvas.drawLine(centerX, centerY, endX, endY, debugLinePaint)
        }
    }

    /**
     * 初始化等分圆半径
     */
    private fun initializeGreenCircleRadii() {
        randomCircleRadius.clear()
        maxCircleTranslateDistance.clear()
        //内圆和中间圆半径间距
        val bigSmallCircleSpace = bigCircleRadius - smallCircleRadius
        //垂直方向最大半径
        val maxVerticalRadius = (bigSmallCircleSpace / 2)
        //水平方向最大半径,扇形中线的长度，可以通过三角函数计算出来
        val maxLevelRadius =
            calculateLevelMaxRadius((bigCircleRadius - (smallCircleRadius / 2f)), angleStep) / 2
        //取两个方向中最小的，在稍微少几个像素防止圆叠加
        val maxRadius = maxVerticalRadius.coerceAtMost(maxLevelRadius.toFloat()) - 2
        for (i in 0 until segmentCount) {
            // 随机生成半径，范围 [maxRadius / 2, maxRadius]
            val randomRadius = Random.nextFloat() * (maxRadius / 2) + (maxRadius / 2)
            randomCircleRadius.add(randomRadius)

            //终点随机[75%,100%]
            val max = Random.nextInt(75, 100) / 100f * purpleCircleRadius
            maxCircleTranslateDistance.add(max)
        }
    }

    /**
     * 初始化动画进度
     */
    private fun initializeAnimationProgress() {
        animationProgress.clear()
        repeat(segmentCount) {
            // 每个动画初始进度为 0
            animationProgress.add(0f)
        }
    }

    /**
     * 开始动画
     */
    fun startAnimation() {
        enableDraw = true
        //气泡显示渐隐动画
        alphaAnim?.cancel()
        alphaAnim = ValueAnimator.ofInt(0, 255)
        alphaAnim?.duration = ALPHA_ANIM_TIME
        alphaAnim?.interpolator = LinearInterpolator()
        alphaAnim?.addUpdateListener {
            val progress = it.animatedValue as Int
            mCirclePaint.alpha = progress
            invalidate()
        }
        alphaAnim?.addListener(onEnd = {
            startTranslateAnim()
        })
        alphaAnim?.start()
    }

    /**
     * 气泡平移和缩放动画
     */
    private fun startTranslateAnim() {
        translateAnimator = ValueAnimator.ofFloat(0f, 1f)
        translateAnimator?.duration = TRANSLATE_ANIM_TIME
        translateAnimator?.interpolator = LinearInterpolator()
        translateAnimator?.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            for (i in animationProgress.indices) {
                animationProgress[i] = progress
            }
            invalidate()
        }
        translateAnimator?.addListener(onEnd = {
            // TODO: caobin 2024/12/17
            //animEnd()
        })
        translateAnimator?.start()
    }

    /**
     * 重置
     * @param needInvalidate Boolean
     */
    fun reset(needInvalidate: Boolean = false) {
        enableDraw = false
        cancelAllAnim()
        initializeGreenCircleRadii()
        initializeAnimationProgress()
        if (needInvalidate) {
            invalidate()
        }
    }
}