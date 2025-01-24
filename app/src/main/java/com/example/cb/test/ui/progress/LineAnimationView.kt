package com.example.cb.test.ui.progress

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
//流星效果
//https://huaweicloud.csdn.net/6507df2b6b896f66024cc1e7.html?dp_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MzU3NDMwNSwiZXhwIjoxNzM3NzA1OTQ1LCJpYXQiOjE3MzcxMDExNDUsInVzZXJuYW1lIjoiY2FvYmluX3N0dWR5In0.v0o16u3f04mr48gToqx8cJQ4wxdH5gQXm9wBoSkcP74&spm=1001.2101.3001.6650.3&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Eactivity-3-120594726-blog-107024887.235%5Ev43%5Epc_blog_bottom_relevance_base4&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Eactivity-3-120594726-blog-107024887.235%5Ev43%5Epc_blog_bottom_relevance_base4&utm_relevant_index=5
class LineAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 10f
        isAntiAlias = true
    }

    private val path = Path() // 定义路径
    private val pathMeasure = PathMeasure() // 测量路径
    private var pathLength = 0f // 路径总长度

    private val animatedPath = Path() // 动画路径
    private var animationProgress = 0f // 动画进度（0.0 - 1.0）

    init {
        // 初始化路径
        path.moveTo(100f, 100f) // 起点
        path.lineTo(500f, 100f) // 水平线
        path.lineTo(500f, 500f) // 垂直线
        path.lineTo(100f, 500f) // 返回起点形成矩形

        // 初始化路径测量
        pathMeasure.setPath(path, false)
        pathLength = pathMeasure.length

        postDelayed({startAnimation()},3000)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 重置动画路径
        animatedPath.reset()

        // 获取路径的片段
        pathMeasure.getSegment(0f, animationProgress * pathLength, animatedPath, true)

        // 绘制路径
        canvas.drawPath(animatedPath, paint)
    }

    fun startAnimation() {
        // 使用 ObjectAnimator 控制动画进度
        val animator = ObjectAnimator.ofFloat(this, "animationProgress", 0f, 1f)
        animator.duration = 2000 // 动画时长 2 秒
        animator.interpolator = LinearInterpolator() // 线性插值器
        animator.start()
    }

    // 提供属性用于动画
    fun setAnimationProgress(progress: Float) {
        animationProgress = progress
        invalidate() // 触发重绘
    }

    fun getAnimationProgress(): Float = animationProgress
}
