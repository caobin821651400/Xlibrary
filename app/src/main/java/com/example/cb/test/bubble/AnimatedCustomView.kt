package com.example.cb.test.bubble

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.animation.addListener
import cn.sccl.xlibrary.kotlin.lazyNone
import com.example.cb.test.BuildConfig
import kotlin.random.Random

/**
 * 动效3  建议打开
 * @author: cb
 * @date: 2024/12/18
 * @desc: 描述
 */
class AnimatedCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // debug
    private val debugOrangeRectPaint by lazyNone {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(255, 165, 0)
        }
    }
    private val debugYellowRectPaint by lazyNone {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.YELLOW
        }
    }

    companion object {
        var showDebug = BuildConfig.DEBUG

        // TODO: caobin 2024/12/18 varl
        var animationDelay = 80L // 每个圆动画启动的间隔时间（单位：ms）
        var animTotalDuration = 480L // 每个圆的动画持续时间
        var animHiddenDuration = 200L // 每个圆消失动画
    }

    // 定义矩形和圆的尺寸（px 值）
    private val orangeRectWidth = 300f // 橙色矩形的宽度
    private val orangeRectHeight = orangeRectWidth / 9f // 橙色矩形的高度
    private val yellowRectHeight = orangeRectWidth * 5 / 9 // 黄色矩形的高度
    private val minCircleRadius = orangeRectHeight * 0.15f // 绿色圆的最小半径
    private val maxCircleRadius = orangeRectHeight * 0.225f // 绿色圆的最大半径
    private val minHorizontalSpacing = orangeRectHeight / 2 // 圆之间的最小水平间距

    // 动画相关
    private val hiddenAnimProportion =
        (animTotalDuration - animHiddenDuration) / animTotalDuration * 1.0f//气泡消失时机占比
    private val hiddenAlphaTotalProgress = 1f - hiddenAnimProportion//气泡消失时开始时机

    // TODO: caobin 2024/12/18 val
    private var circleCount = Random.nextInt(5, 9)
    private val animList = arrayListOf<ValueAnimator>()

    /**
     * 气泡圆的颜色
     */
    private var mCircleColor = Color.GREEN

    // 圆的数据列表
    private val circles = mutableListOf<Circle>()

    /**
     * 设置气泡圆的颜色
     * @param color Int
     */
    fun setCircleColor(@ColorInt color: Int) {
        mCircleColor = BubbleUtils.calculateColor(color)
        circles.forEach { it.paint.color = mCircleColor }
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //强制设置宽高比为9：5
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val calculatedHeight = yellowRectHeight.toInt()
        MeasureSpec.makeMeasureSpec(calculatedHeight, widthMode)
        setMeasuredDimension(widthSize, calculatedHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        reset(false)
    }

    private fun drawDebug(canvas: Canvas) {
        if (!showDebug) return
        // 绘制黄色矩形
        canvas.drawRect(0f, 0f, orangeRectWidth, yellowRectHeight, debugYellowRectPaint)

        // 绘制橙色矩形，紧贴黄色矩形的底部
        canvas.drawRect(
            0f,
            height - orangeRectHeight,
            orangeRectWidth,
            height.toFloat(),
            debugOrangeRectPaint
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDebug(canvas)

        // 绘制绿色圆
        for (circle in circles) {
            if (!circle.enableShow) {
                continue
            }
            val adjustedY = circle.currentCy + (yellowRectHeight - orangeRectHeight)// 动态更新圆的垂直位置
            canvas.drawCircle(circle.cx, adjustedY, circle.currentRadius, circle.paint)
        }
    }

    /**
     * 随机生成绿色圆数据
     */
    private fun generateRandomCircles() {
        circles.clear()
        var currentX = 0f

        //动画过程中Y轴可以移动的最大距离,UI说明（位移距离为 12～16px）
        // orangeRectHeight=4,也就是【orangeRectHeight*3，orangeRectHeight*4】
        val minDistance = orangeRectHeight * 3

        val tempList = mutableListOf<Circle>()
        for (i in 0 until circleCount) {
            // 随机生成圆的半径
            val radius = Random.nextFloat() * (maxCircleRadius - minCircleRadius) + minCircleRadius

            // 随机生成圆的垂直位置，确保不会超出橙色矩形的边界
            val cy = radius + Random.nextFloat() * (orangeRectHeight - 2 * radius)

            // 如果当前圆起始点加上圆的半径超出矩形宽度，则停止生成
            if (currentX + radius > orangeRectWidth) break

            // 确保圆的水平间距至少为 minHorizontalSpacing,随机一下
            val maxSpace = (minHorizontalSpacing * 1.5f).toInt()
            val spaceRandom = Random.nextInt(minHorizontalSpacing.toInt(), maxSpace)
            currentX += radius + spaceRandom

            // 如果当前圆超出宽度限制，终止生成
            if (currentX + radius > orangeRectWidth) break

            //判断一下如果超出最大矩形区域的话，适当减少距离
            val maxY = yellowRectHeight - orangeRectHeight - radius
            val randomY = minDistance + Random.nextFloat() * orangeRectHeight
            //终点在随机一下,
            val moveY = maxY.coerceAtLeast(randomY)

            //缩放随机,UI说明3-4倍
            val scale = Random.nextInt(3, 5) * radius

            //每个圆的画笔
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = mCircleColor
                style = Paint.Style.FILL
                alpha = 255
            }

            // 将生成的圆添加到列表中
            tempList.add(
                Circle(i, currentX, cy, radius, moveY, scale, false, cy, radius, paint)
            )

            // 更新水平位置，准备绘制下一个圆
            currentX += radius
        }

        //打乱生成的圆顺序
        tempList.shuffled().forEachIndexed { index, circle ->
            circle.delayStart = index * animationDelay
            circles.add(circle)

            randomStartAnim(circle)
        }
    }

    /**
     * 每个圆的动画都是单独的
     */
    fun startAnimation() {
        animList.forEach { it.start() }
    }

    private fun randomStartAnim(circle: Circle) {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = animTotalDuration
        animator.startDelay = circle.delayStart
        animator.addUpdateListener { animation ->
            val progress = animation.animatedFraction

            // 移动和放大逻辑
            circle.currentCy = circle.cy - circle.moveY * progress
            circle.currentRadius = circle.originRadius + circle.scale * progress

            //移动和放大动画执行到一半多时，气泡开始消失
            if (progress > hiddenAnimProportion) {
                val alphaProgress = (1f - progress) / hiddenAlphaTotalProgress
                //气泡要消失了
                circle.paint.alpha = (255 * alphaProgress).toInt()
            }
            invalidate()
        }
        animator.addListener(onStart = { circle.enableShow = true })
        animList.add(animator)
    }


    /**
     * 数据类存储每个圆的信息
     */
    private data class Circle(
        var index: Int,
        var cx: Float,
        var cy: Float,
        val originRadius: Float,
        var moveY: Float,
        var scale: Float,
        var enableShow: Boolean,
        var currentCy: Float,
        var currentRadius: Float,
        val paint: Paint
    ) {
        var delayStart = 0L
    }

    fun reset(needInvalidate: Boolean = false) {
        cancelAllAnim()
        circleCount = Random.nextInt(5, 9)
        generateRandomCircles()
        if (needInvalidate) {
            invalidate()
        }
    }

    fun cancelAllAnim() {
        animList.forEach { it.cancel() }
        animList.clear()
    }
}