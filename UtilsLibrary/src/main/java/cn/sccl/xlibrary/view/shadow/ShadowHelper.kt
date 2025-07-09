package cn.sccl.xlibrary.view.shadow

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.toColorInt
import cn.sccl.xlibrary.kotlin.lazyNone
import com.cb.xlibrary.R
import kotlin.math.abs

/**
 * 抽象出来通用方法，可以为不同的ViewGroup实现阴影
 * @author cb
 * @date 2025/2/6
 */
class ShadowHelper(private val viewGroup: ViewGroup) : ShadowLayoutImpl {

    private val mShadowPaint by lazyNone {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.TRANSPARENT
        }
    }

    private val mStrokePaint by lazyNone {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = mStrokeWidth
            color = mStrokeColor
        }
    }

    /**
     * 是否允许描边
     */
    private var enableStroke = false

    /**
     * 描边的宽度
     */
    private var mStrokeWidth = 2f

    /**
     * 描边的颜色
     */
    private var mStrokeColor = Color.GREEN

    /**
     * 裁剪圆角
     */
    private val mClipPath = Path()

    /**
     * 阴影颜色
     */
    private var mShadowColor = 0

    /**
     * 阴影扩散区域大小
     * @return
     */
    private var mShadowBlur = 5f

    /**
     * 阴影x偏移量
     */
    private var mOffsetX = 0f

    /**
     * 阴影y偏移量
     */
    private var mOffsetY = 0f

    /**
     * 圆角大小
     * @return
     */
    private var mCornerRadius: Float = 0f
    private var mCornerRadiusLeftTop = 0f
    private var mCornerRadiusRightTop = 0f
    private var mCornerRadiusLeftBottom = 0f
    private var mCornerRadiusRightBottom = 0f

    /**
     * 裁剪区域，没有描边就是viewGroup本身的宽高
     */
    private val boundsRectF by lazy(LazyThreadSafetyMode.NONE) { RectF() }

    private val shadowPath by lazy(LazyThreadSafetyMode.NONE) { Path() }

    /**
     * 圆角数据
     */
    private var cornerValues: FloatArray? = null

    private val defaultShadowColor = "#2a000000".toColorInt()

    fun parseAttributes(context: Context?, attrs: AttributeSet? = null) {
        viewGroup.setWillNotDraw(false)
        if (context == null) return
        context.withStyledAttributes(attrs, R.styleable.ShadowLayout) {
            mCornerRadius = getDimension(R.styleable.ShadowLayout_sl_cornerRadius, 0f)
            mCornerRadiusLeftTop =
                getDimension(R.styleable.ShadowLayout_sl_cornerRadius_leftTop, -1f)
            mCornerRadiusLeftBottom =
                getDimension(R.styleable.ShadowLayout_sl_cornerRadius_leftBottom, -1f)
            mCornerRadiusRightTop =
                getDimension(R.styleable.ShadowLayout_sl_cornerRadius_rightTop, -1f)
            mCornerRadiusRightBottom =
                getDimension(R.styleable.ShadowLayout_sl_cornerRadius_rightBottom, -1f)

            //默认扩散区域宽度
            mShadowBlur = getDimension(R.styleable.ShadowLayout_sl_shadowBlur, 5f)

            //x轴偏移量
            mOffsetX = getDimension(R.styleable.ShadowLayout_sl_shadowOffsetX, 0f)
            //y轴偏移量
            mOffsetY = getDimension(R.styleable.ShadowLayout_sl_shadowOffsetY, 0f)
            mShadowColor = getColor(R.styleable.ShadowLayout_sl_shadowColor, defaultShadowColor)
            enableStroke = getBoolean(R.styleable.ShadowLayout_sl_enable_stroke, false)
            mStrokeWidth = getDimension(R.styleable.ShadowLayout_sl_stroke_width, 2f)
            mStrokeColor = getColor(R.styleable.ShadowLayout_sl_stroke_color, Color.GREEN)
        }
        initShadowPaint()
      // viewGroup.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * 控件大小改变
     * @param w
     * @param h
     */
    fun onSizeChanged(w: Int, h: Int) {
        initCornerValue(h)
        boundsRectF.set(
            mStrokeWidth,
            mStrokeWidth,
            w - mStrokeWidth,
            h - mStrokeWidth
        )

        initPath()
    }

    private fun initPath() {
        //阴影
        shadowPath.reset()
        if (isAllCornerRadius()) {
            shadowPath.addRoundRect(boundsRectF, mCornerRadius, mCornerRadius, Path.Direction.CW)
        } else {
            cornerValues?.let {
                shadowPath.addRoundRect(boundsRectF, it, Path.Direction.CW)
            }
        }

        //内容区域
        mClipPath.reset()
        if (isAllCornerRadius()) {
            mClipPath.addRoundRect(boundsRectF, mCornerRadius, mCornerRadius, Path.Direction.CW)
        } else {
            cornerValues?.let {
                mClipPath.addRoundRect(boundsRectF, it, Path.Direction.CW)
            }
        }
    }

    /**
     * 对子View进行绘制，也是剪裁子view的关键
     *
     * @param canvas
     */
    override fun dispatchDraw(canvas: Canvas) {
        if (viewGroup.getChildAt(0) != null) {
            if (isAllCornerRadius()) {
                canvas.clipPath(mClipPath)
            } else {
                cornerValues?.let { canvas.clipPath(mClipPath) }
            }
        }
    }

    override fun onDrawBeforeSuper(canvas: Canvas) {
        drawShadow(canvas)
    }

    override fun onDrawAfterSuper(canvas: Canvas) {
        drawStroke(canvas)
    }

    /**
     * 画阴影
     */
    private fun drawShadow(canvas: Canvas) {
        canvas.drawPath(shadowPath, mShadowPaint)
    }

    /**
     * 描边
     */
    private fun drawStroke(canvas: Canvas) {
        if (!enableStroke) return
        if (isAllCornerRadius()) {
            canvas.drawPath(mClipPath, mStrokePaint)
        } else {
            cornerValues?.let { canvas.drawPath(mClipPath, mStrokePaint) }
        }
    }

    /**
     * 四个圆角值一样
     */
    private fun isAllCornerRadius(): Boolean {
        return mCornerRadiusLeftTop == -1f
                && mCornerRadiusLeftBottom == -1f
                && mCornerRadiusRightTop == -1f
                && mCornerRadiusRightBottom == -1f
    }

    /**
     * 初始化圆角属性
     * @param trueHeight Int
     */
    private fun initCornerValue(trueHeight: Int) {
        var leftTop: Int
        var rightTop: Int
        var rightBottom: Int
        var leftBottom: Int
        leftTop = if (mCornerRadiusLeftTop == -1f) {
            mCornerRadius.toInt()
        } else {
            mCornerRadiusLeftTop.toInt()
        }

        if (leftTop > trueHeight / 2) {
            leftTop = trueHeight / 2
        }

        rightTop = if (mCornerRadiusRightTop == -1f) {
            mCornerRadius.toInt()
        } else {
            mCornerRadiusRightTop.toInt()
        }

        if (rightTop > trueHeight / 2) {
            rightTop = trueHeight / 2
        }

        rightBottom = if (mCornerRadiusRightBottom == -1f) {
            mCornerRadius.toInt()
        } else {
            mCornerRadiusRightBottom.toInt()
        }

        if (rightBottom > trueHeight / 2) {
            rightBottom = trueHeight / 2
        }

        leftBottom = if (mCornerRadiusLeftBottom == -1f) {
            mCornerRadius.toInt()
        } else {
            mCornerRadiusLeftBottom.toInt()
        }

        if (leftBottom > trueHeight / 2) {
            leftBottom = trueHeight / 2
        }

        cornerValues = floatArrayOf(
            leftTop.toFloat(),
            leftTop.toFloat(),
            rightTop.toFloat(),
            rightTop.toFloat(),
            rightBottom.toFloat(),
            rightBottom.toFloat(),
            leftBottom.toFloat(),
            leftBottom.toFloat()
        )
    }

    /**
     * 阴影初始化
     */
    private fun initShadowPaint() {
        mShadowPaint.color = Color.TRANSPARENT
        mShadowPaint.setShadowLayer(mShadowBlur, mOffsetX, mOffsetY, mShadowColor)
    }

    override fun getCornerRadius(): Float {
        return mCornerRadius
    }

    override fun getShadowBlur(): Float {
        return mShadowBlur
    }

    /**
     * 设置x轴阴影的偏移量,不能大于阴影扩散范围
     *
     * @param dx
     */
    override fun setShadowOffsetX(dx: Float) {
        if (abs(dx.toDouble()) > mShadowBlur) {
            if (dx > 0) {
                this.mOffsetX = this.mShadowBlur
            } else {
                this.mOffsetX = -mShadowBlur
            }
        } else {
            this.mOffsetX = dx
        }
        initShadowPaint()
        invalidate()
    }

    /**
     * 设置y轴阴影的偏移量,不能大于阴影扩散范围
     *
     * @param dy
     */
    override fun setShadowOffsetY(dy: Float) {
        if (abs(dy.toDouble()) > mShadowBlur) {
            if (dy > 0) {
                this.mOffsetY = this.mShadowBlur
            } else {
                this.mOffsetY = -mShadowBlur
            }
        } else {
            this.mOffsetY = dy
        }

        initShadowPaint()
        invalidate()
    }

    override fun getShadowOffsetX(): Float {
        return mOffsetX
    }

    override fun getShadowOffsetY(): Float {
        return mOffsetY
    }

    /**
     * 设置shadowLayout圆角
     *
     * @param cornerRadius
     */
    override fun setCornerRadius(cornerRadius: Int) {
        this.mCornerRadius = cornerRadius.toFloat()
        requestLayout()
    }

    /**
     * 设置阴影扩散区域
     *
     * @param blur
     */
    override fun setShadowBlur(blur: Int) {
        this.mShadowBlur = blur.toFloat()
        initShadowPaint()
        invalidate()
    }

    /**
     * 设置阴影颜色值
     *
     * @param color
     */
    override fun setShadowColor(color: Int) {
        this.mShadowColor = color
        initShadowPaint()
        invalidate()
    }

    override fun getShadowColor(): Int {
        return mShadowColor
    }


    /**
     * 单独设置4个圆角属性
     *
     * @param leftTop
     * @param rightTop
     * @param leftBottom
     * @param rightBottom
     */
    override fun setSpecialCorner(
        leftTop: Int,
        rightTop: Int,
        leftBottom: Int,
        rightBottom: Int
    ) {
        mCornerRadiusLeftTop = leftTop.toFloat()
        mCornerRadiusRightTop = rightTop.toFloat()
        mCornerRadiusLeftBottom = leftBottom.toFloat()
        mCornerRadiusRightBottom = rightBottom.toFloat()
        requestLayout()
    }

    /**
     * 设置描边颜色值
     *
     * @param color
     */
    override fun setStrokeColor(color: Int) {
        mStrokeColor = color
        mStrokePaint.color = mStrokeColor
        invalidate()
    }

    /**
     * 描边的宽度
     * @param width Float
     */
    override fun setStrokeWidth(width: Float) {
        if (!enableStroke) return
        if (width > mShadowBlur) return
        mStrokeWidth = width
        mStrokePaint.strokeWidth = mStrokeWidth
        requestLayout()
    }

    /**
     * 允许描边
     * @param enable Boolean
     */
    override fun enableStrokeWidth(enable: Boolean) {
        enableStroke = enable
        requestLayout()
    }

    private fun invalidate() {
        viewGroup.invalidate()
    }

    private fun requestLayout() {
        onSizeChanged(viewGroup.width, viewGroup.height)
        invalidate()
    }
}