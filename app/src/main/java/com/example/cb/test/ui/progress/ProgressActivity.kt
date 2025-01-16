package com.example.cb.test.ui.progress

import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.Gravity
import android.widget.Button
import android.widget.ProgressBar
import cn.sccl.xlibrary.kotlin.lazyNone
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity

/**
 *
 * @author cb
 * @date 2024/12/16
 */
class ProgressActivity : BaseActivity() {

    private val progressBar: ProgressBar by lazyNone { findViewById(R.id.ProgressBar) }

    private val mProgressDrawable by lazyNone {
        val background = ShapeDrawable(
            getRoundRectShape(100f)
        ).apply {
            paint.color = Color.parseColor("#EEECEA")
        }

        val secondary = ShapeDrawable()
        secondary.paint.color = Color.TRANSPARENT

        val progress = ScaleDrawable(
            ShapeDrawable(
                getRoundRectShape(100f)
            ).apply {
                paint.color = Color.parseColor("#4AB096")
            }, Gravity.START, 1f, -1f
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
        progressBar.progressDrawable = mProgressDrawable
    }

    override fun initEvent() {

    }

    private fun getRoundRectShape(size: Float): RoundRectShape {
        return RoundRectShape(
            floatArrayOf(
                size, size, size, size, size, size, size, size
            ), null, null
        )
    }
}