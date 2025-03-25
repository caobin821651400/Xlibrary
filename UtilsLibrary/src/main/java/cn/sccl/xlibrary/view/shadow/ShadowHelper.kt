package cn.sccl.xlibrary.view.shadow

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import com.cb.xlibrary.R
import kotlin.math.abs

/**
 * 抽象出来通用方法，可以为不同的ViewGroup实现阴影
 * @author cb
 * @date 2025/2/6
 */
class ShadowHelper(private val viewGroup: ViewGroup) : ShadowLayoutImpl {

    private val shadowPaint by lazyNone {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
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

    private val strokePaint by lazyNone {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
    }

    private val clipPath = Path()

    /**
     * 阴影颜色
     */
    private var mShadowColor = 0

    /**
     * 阴影扩散区域大小
     * @return
     */
    private var shadowLimit = 5f

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
    private var cornerRadius: Float = 0f
    private var mCornerRadiusLeftTop = 0f
    private var mCornerRadiusRightTop = 0f
    private var mCornerRadiusLeftBottom = 0f
    private var mCornerRadiusRightBottom = 0f

    //子布局与父布局的padding（即通过padding来实现mShadowLimit的大小和阴影展示）
    private var leftPadding = 0
    private var topPadding = 0
    private var rightPadding = 0
    private var bottomPadding = 0
    private val rectF = RectF() //阴影布局子空间区域

    /**
     * 圆角数据
     */
    private var cornerValues: FloatArray? = null

    private val defaultShadowColor = Color.parseColor("#2a000000")

    fun parseAttributes(context: Context?, attrs: AttributeSet? = null) {
        if (context == null) return
        val attr = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout)
        cornerRadius = attr.getDimension(R.styleable.ShadowLayout_sl_cornerRadius, 0f)
        mCornerRadiusLeftTop =
            attr.getDimension(R.styleable.ShadowLayout_sl_cornerRadius_leftTop, -1f)
        mCornerRadiusLeftBottom =
            attr.getDimension(R.styleable.ShadowLayout_sl_cornerRadius_leftBottom, -1f)
        mCornerRadiusRightTop =
            attr.getDimension(R.styleable.ShadowLayout_sl_cornerRadius_rightTop, -1f)
        mCornerRadiusRightBottom =
            attr.getDimension(R.styleable.ShadowLayout_sl_cornerRadius_rightBottom, -1f)

        //默认扩散区域宽度
        shadowLimit = attr.getDimension(R.styleable.ShadowLayout_sl_shadowLimit, 5f)

        //x轴偏移量
        mOffsetX = attr.getDimension(R.styleable.ShadowLayout_sl_shadowOffsetX, 0f)
        //y轴偏移量
        mOffsetY = attr.getDimension(R.styleable.ShadowLayout_sl_shadowOffsetY, 0f)
        mShadowColor = attr.getColor(R.styleable.ShadowLayout_sl_shadowColor, defaultShadowColor)
        enableStroke = attr.getBoolean(R.styleable.ShadowLayout_sl_enable_stroke, false)
        mStrokeWidth = attr.getDimension(R.styleable.ShadowLayout_sl_stroke_width, 2f)
        mStrokeColor = attr.getColor(R.styleable.ShadowLayout_sl_stroke_color, Color.GREEN)
        attr.recycle()
        setPadding()
    }

    /**
     * 对子View进行绘制，也是剪裁子view的关键
     *
     * @param canvas
     */
    fun dispatchDraw(canvas: Canvas) {
        if (viewGroup.getChildAt(0) != null) {
            if (mCornerRadiusLeftTop == -1f
                && mCornerRadiusLeftBottom == -1f
                && mCornerRadiusRightTop == -1f
                && mCornerRadiusRightBottom == -1f
            ) {
                clipPath.reset()
                clipPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)
                canvas.clipPath(clipPath)
            } else {
                cornerValues?.let {
                    clipPath.reset()
                    clipPath.addRoundRect(
                        leftPadding.toFloat(),
                        topPadding.toFloat(),
                        (viewGroup.width - rightPadding).toFloat(),
                        (viewGroup.height - bottomPadding).toFloat(),
                        it,
                        Path.Direction.CW
                    )
                    canvas.clipPath(clipPath)
                }
            }
        }
    }

    fun onDraw(canvas: Canvas) {
        //描边
        if (enableStroke) {
            strokePaint.strokeWidth = mStrokeWidth
            strokePaint.color = mStrokeColor
            clipPath.reset()
            if (mCornerRadiusLeftTop == -1f
                && mCornerRadiusLeftBottom == -1f
                && mCornerRadiusRightTop == -1f
                && mCornerRadiusRightBottom == -1f
            ) {
                clipPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)
                canvas.drawPath(clipPath, strokePaint)
            } else {
                cornerValues?.let {
                    clipPath.addRoundRect(
                        leftPadding.toFloat(),
                        topPadding.toFloat(),
                        (viewGroup.width - rightPadding).toFloat(),
                        (viewGroup.height - bottomPadding).toFloat(),
                        it,
                        Path.Direction.CW
                    )
                    canvas.drawPath(clipPath, strokePaint)
                }
            }
        }
    }

    fun onSizeChanged(w: Int, h: Int) {
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h)
        }
        rectF.left = leftPadding.toFloat()
        rectF.top = topPadding.toFloat()
        rectF.right = (viewGroup.width - rightPadding).toFloat()
        rectF.bottom = (viewGroup.height - bottomPadding).toFloat()
        initCornerValue((rectF.bottom - rectF.top).toInt())
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
            cornerRadius.toInt()
        } else {
            mCornerRadiusLeftTop.toInt()
        }

        if (leftTop > trueHeight / 2) {
            leftTop = trueHeight / 2
        }

        rightTop = if (mCornerRadiusRightTop == -1f) {
            cornerRadius.toInt()
        } else {
            mCornerRadiusRightTop.toInt()
        }

        if (rightTop > trueHeight / 2) {
            rightTop = trueHeight / 2
        }

        rightBottom = if (mCornerRadiusRightBottom == -1f) {
            cornerRadius.toInt()
        } else {
            mCornerRadiusRightBottom.toInt()
        }

        if (rightBottom > trueHeight / 2) {
            rightBottom = trueHeight / 2
        }

        leftBottom = if (mCornerRadiusLeftBottom == -1f) {
            cornerRadius.toInt()
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
     * 阴影内边距
     */
    fun setPadding() {
        val p = shadowLimit.toInt()
        leftPadding = p
        topPadding = p
        rightPadding = p
        bottomPadding = p
        viewGroup.setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
    }


    @Suppress("deprecation")
    private fun setBackgroundCompat(w: Int, h: Int) {
        viewGroup.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val bitmap = createShadowBitmap(w, h, mShadowColor)
        val drawable = BitmapDrawable(bitmap)
        viewGroup.background = drawable
    }

    /**
     * 创建阴影视图
     * @param width Int
     * @param height Int
     * @param shadowColor Int
     * @return Bitmap
     */
    private fun createShadowBitmap(width: Int, height: Int, shadowColor: Int): Bitmap {
        //阴影bitmap的X起始位置，整个控件的1/4
        val shadowXStart = if (width / 4 == 0) 1 else width / 4
        //阴影bitmap的Y起始位置，整个控件的1/4
        val shadowYStart = if (height / 4 == 0) 1 else height / 4
        val cornerRadius = cornerRadius / 4
        //模糊范围的扩散区域，上面的shadowXStart，shadowYStart形成的bitmap四周是没有模糊的效果的
        val shadowDiffuse = shadowLimit / 4

        //阴影XY轴偏移量，一般是底部偏移
        var dx = mOffsetX
        var dy = mOffsetY

        val output = Bitmap.createBitmap(shadowXStart, shadowYStart, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        XLogUtils.d("大小：" + output.byteCount)

        //整个阴影的矩形区域，包括模糊扩散的区域
        val shadowRect = RectF(
            shadowDiffuse,
            shadowDiffuse,
            shadowXStart - shadowDiffuse,
            shadowYStart - shadowDiffuse
        )
        val maxOffset = shadowDiffuse / 2

        //计算偏移最大值,保证XY轴偏移量不能超出范围
        if (dy > 0) {
            if (dy > maxOffset) {
                dy = maxOffset
            }
        } else if (dy < 0) {
            if (dy < -maxOffset) {
                dy = -maxOffset
            }
        }
        shadowRect.top += dy
        shadowRect.bottom += dy

        //计算偏移最大值,保证XY轴偏移量不能超出范围
        if (dx > 0) {
            if (dx > maxOffset) {
                dx = maxOffset
            }
        } else if (dx < 0) {
            if (dx < -maxOffset) {
                dx = -maxOffset
            }
        }
        shadowRect.left += dx
        shadowRect.right += dx

        shadowPaint.color = Color.TRANSPARENT
        shadowPaint.setShadowLayer(shadowDiffuse / 2, 0f, 0f, shadowColor)
        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint)
        return output
    }

    override fun getCornerRadius(): Float {
        return cornerRadius
    }

    override fun getShadowLimit(): Float {
        return shadowLimit
    }

    /**
     * 设置x轴阴影的偏移量
     *
     * @param dx
     */
    override fun setShadowOffsetX(dx: Float) {
        if (abs(dx.toDouble()) > shadowLimit) {
            if (dx > 0) {
                this.mOffsetX = this.shadowLimit
            } else {
                this.mOffsetX = -shadowLimit
            }
        } else {
            this.mOffsetX = dx
        }
        if (viewGroup.width != 0 && viewGroup.height != 0) {
            setBackgroundCompat(viewGroup.width, viewGroup.height)
        }
    }

    override fun getShadowOffsetX(): Float {
        return abs(mOffsetX) + shadowLimit
    }

    override fun getShadowOffsetY(): Float {
        return abs(mOffsetY) + shadowLimit
    }

    /**
     * 设置y轴阴影的偏移量
     *
     * @param dy
     */
    override fun setShadowOffsetY(dy: Float) {
        if (abs(dy.toDouble()) > shadowLimit) {
            if (dy > 0) {
                this.mOffsetY = this.shadowLimit
            } else {
                this.mOffsetY = -shadowLimit
            }
        } else {
            this.mOffsetY = dy
        }

        if (viewGroup.width != 0 && viewGroup.height != 0) {
            setBackgroundCompat(viewGroup.width, viewGroup.height)
        }
    }

    /**
     * 设置shadowLayout圆角
     *
     * @param cornerRadius
     */
    override fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = cornerRadius.toFloat()
        if (viewGroup.width != 0 && viewGroup.height != 0) {
            setBackgroundCompat(viewGroup.width, viewGroup.height)
        }
    }

    /**
     * 设置阴影扩散区域
     *
     * @param shadowLimit
     */
    override fun setShadowLimit(shadowLimit: Int) {
        this.shadowLimit = shadowLimit.toFloat()
        setPadding()
    }

    /**
     * 设置阴影颜色值
     *
     * @param color
     */
    override fun setShadowColor(color: Int) {
        this.mShadowColor = color
        if (viewGroup.width != 0 && viewGroup.height != 0) {
            setBackgroundCompat(viewGroup.width, viewGroup.height)
        }
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
        if (viewGroup.width != 0 && viewGroup.height != 0) {
            setBackgroundCompat(viewGroup.width, viewGroup.height)
        }
    }

    /**
     * 设置描边颜色值
     *
     * @param color
     */
    override fun setStrokeColor(color: Int) {
        mStrokeColor = color
        viewGroup.invalidate()
    }

    /**
     * 描边的宽度
     * @param width Float
     */
    override fun setStrokeWidth(width: Float) {
        mStrokeWidth = width
        viewGroup.invalidate()
    }

    /**
     * 允许描边
     * @param enable Boolean
     */
    override fun enableStrokeWidth(enable: Boolean) {
        enableStroke = enable
        viewGroup.invalidate()
    }
}