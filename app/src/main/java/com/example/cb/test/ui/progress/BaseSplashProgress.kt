package com.example.cb.test.ui.progress

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.animation.addListener
import cn.sccl.xlibrary.kotlin.lazyNone

/**
 *
 * @author cb
 * @date 2025/4/9
 */
class BaseSplashProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val progressBar by lazyNone { SplashProgressBar(context) }

    companion object {
        const val START_PROGRESS = 5
        const val MAX_PROGRESS = 100

        const val STEP1_PROGRESS = 80
        const val STEP1_TIME = 3000L

        const val STEP2_PROGRESS = 95
        const val STEP2_TIME = 3000L
    }

    private var animator: ValueAnimator? = null

    init {
        // TODO: caobin 2025/4/9
        val params = LayoutParams(300, 15)
        addView(progressBar, params)
        progressBar.setMaxProgress(MAX_PROGRESS)
        progressBar.setProgress(START_PROGRESS)
    }

    fun startStep1() {
        animator = ValueAnimator.ofInt(START_PROGRESS, STEP1_PROGRESS)
        animator?.duration = STEP1_TIME
        animator?.interpolator = LinearInterpolator()
        animator?.addUpdateListener {
            val progress = it.animatedValue as Int
            progressBar.setProgress(progress)
        }
        animator?.addListener(onEnd = {
            startStep2()
        })
        animator?.start()
    }


    fun startStep2() {
        animator = ValueAnimator.ofInt(STEP1_PROGRESS, STEP2_PROGRESS)
        animator?.duration = STEP2_TIME
        animator?.interpolator = LinearInterpolator()
        animator?.addUpdateListener {
            val progress = it.animatedValue as Int
            progressBar.setProgress(progress)
        }
        animator?.addListener(onEnd = {

        })
        animator?.start()
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        release()
    }

    fun release() {
        animator?.cancel()
        animator = null
    }
}