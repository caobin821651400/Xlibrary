package com.example.cb.test.ui.progress

import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.widget.Button
import android.widget.ProgressBar
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XDensityUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity

/**
 *
 * @author cb
 * @date 2024/12/16
 */
class ProgressActivity : BaseActivity() {

    private val progressBar1: ProgressBar by lazyNone { findViewById(R.id.progressBar1) }
    private val progressBar2: RoundCornerProgressBar by lazyNone { findViewById(R.id.progressBar2) }
    private val progressBar3: CircleProgressBar by lazyNone { findViewById(R.id.progressBar3) }
    private val button: Button by lazyNone { findViewById(R.id.button) }
    private val button2: Button by lazyNone { findViewById(R.id.button2) }
    private val fireworksView: FireworksView by lazyNone { findViewById(R.id.fireworksView) }

    private val mProgressDrawable by lazyNone {
        val background = ShapeDrawable(
            getRoundRectShape(XDensityUtils.dp2px(this, 100f).toFloat())
        ).apply {
            paint.color = Color.parseColor("#EEECEA")
        }

        val secondary = ShapeDrawable()
        secondary.paint.color = Color.TRANSPARENT

        val progress = ClipDrawable(
            ShapeDrawable(
                getRoundRectShape(XDensityUtils.dp2px(this, 100f).toFloat())
            ).apply {
                paint.color = Color.parseColor("#4AB096")
            }, Gravity.START, ClipDrawable.HORIZONTAL
        )

        val layers = arrayOf(background, secondary, progress)
        val layerDrawable = LayerDrawable(layers)
        layerDrawable.setId(0, android.R.id.background)
        layerDrawable.setId(1, android.R.id.secondaryProgress)
        layerDrawable.setId(2, android.R.id.progress)
        layerDrawable
    }

    override fun getLayoutId() = R.layout.activity_progress

    override fun initUI() {
        setHeaderTitle("进度条")
        progressBar1.progressDrawable = mProgressDrawable
    }

    var mProgress = 0

    override fun initEvent() {
        button2.setOnClickListener {
            fireworksView.startPlay()
        }
        button.setOnClickListener {
            mProgress++
            progressBar1.setProgress(mProgress)
            progressBar2.setProgress(mProgress)
            progressBar3.setProgress(mProgress)
        }
    }

    private fun getRoundRectShape(size: Float): RoundRectShape {
        return RoundRectShape(
            floatArrayOf(
                size, size, size, size, size, size, size, size
            ), null, null
        )
    }
}