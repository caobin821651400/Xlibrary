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
import androidx.core.view.animation.PathInterpolatorCompat
import cn.sccl.xlibrary.kotlin.lazyNone
import com.example.cb.test.BuildConfig
import kotlin.random.Random

/**
 * 动效3  建议打开drawDebug
 * @author: cb
 * @date: 2024/12/18
 * @desc: 描述
 */
class BubbleView3 @JvmOverloads constructor(
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
    private val maxCircleRadius = orangeRectHeight * 0.20f // 绿色圆的最大半径
    private val minHorizontalSpacing = orangeRectHeight / 2 // 圆之间的最小水平间距

    // 动画相关
    private val hiddenAnimProportion =
        (animTotalDuration - animHiddenDuration) / animTotalDuration * 1.0f//气泡消失时机占比
    private val hiddenAlphaTotalProgress = 1f - hiddenAnimProportion//气泡消失时开始时机
    // 圆的数据列表
    private val circles = mutableListOf<Circle>()

    // TODO: caobin 2024/12/18 val
    private var circleCount = Random.nextInt(5, 9)
    private val animList = arrayListOf<ValueAnimator>()

    /**
     * 气泡圆的颜色
     */
    private var mCircleColor = Color.GREEN


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

        for (circle in circles) {
            if (!circle.enableShow) {
                continue
            }

            // 动态更新圆的垂直位置
            val adjustedY = circle.currenty + (yellowRectHeight - orangeRectHeight)
            canvas.drawCircle(circle.startX, adjustedY, circle.currentRadius, circle.paint)
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

            generateCircleAnim(circle)
        }
    }

    /**
     * 每个圆的动画都是单独的
     */
    fun startAnimation() {
        animList.forEach { it.start() }
    }

    /**
     * 创建每个气泡的动画
     * @param circle Circle
     */
    private fun generateCircleAnim(circle: Circle) {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = animTotalDuration
        animator.startDelay = circle.delayStart
        animator.interpolator= PathInterpolatorCompat.create(0.25f, 0.8f, 0.5f, 1f)
        animator.addUpdateListener { animation ->
            val progress = animation.animatedFraction

            // 移动和放大逻辑
            circle.currenty = circle.startY - circle.moveY * progress
            circle.currentRadius = circle.startRadius + (circle.scale - 1) * progress

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
        val index: Int,//第几个，目前没有用到
        val startX: Float,//起始X
        val startY: Float,//起始Y
        val startRadius: Float,//起始半径
        val moveY: Float,//Y轴移动的距离
        val scale: Float,//动画过程中总共放大多少
        var enableShow: Boolean,//是否画到Canvas上
        var currenty: Float,//动画过程中Y坐标
        var currentRadius: Float,//动画过程中的半径
        val paint: Paint//画笔，主要为了改alpha
    ) {
        var delayStart = 0L//延迟多久启动动画
    }

    /**
     * 重置
     * @param needInvalidate Boolean
     */
    fun reset(needInvalidate: Boolean = false) {
        cancelAllAnim()
        circleCount = Random.nextInt(5, 9)
        generateRandomCircles()
        if (needInvalidate) {
            invalidate()
        }
    }

    /**
     * 取消所有动画
     */
    fun cancelAllAnim() {
        animList.forEach { it.cancel() }
        animList.clear()
    }
}