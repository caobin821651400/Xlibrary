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
import android.widget.FrameLayout
import cn.sccl.xlibrary.kotlin.lazyNone
import com.cb.xlibrary.R
import kotlin.math.abs


/**
 * 阴影容器，媲美cardView
 * @author: cb
 * @date: 2025/1/25
 * @desc: 描述
 */
class ShadowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val shadowPaint by lazyNone {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    /**
     * 阴影颜色
     */
    private var mShadowColor = 0

    /**
     * 阴影扩散区域大小
     * @return
     */
    var shadowLimit = 5f

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
    var cornerRadius: Float = 0f
    private var mCornerRadiusLeftTop = 0f
    private var mCornerRadiusRightTop = 0f
    private var mCornerRadiusLeftBottom = 0f
    private var mCornerRadiusRightBottom = 0f

    //子布局与父布局的padding（即通过padding来实现mShadowLimit的大小和阴影展示）
    private var leftPadding = 0
    private var topPadding = 0
    private var rightPadding = 0
    private var bottomPadding = 0
    private val rectf = RectF() //阴影布局子空间区域
    private var firstView: View? = null //如有子View则为子View，否则为ShadowLayout本身

    private val defaultShadowColor = Color.parseColor("#2a000000")

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout)
        cornerRadius = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius, 0f)
        mCornerRadiusLeftTop =
            attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftTop, -1f)
        mCornerRadiusLeftBottom =
            attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftBottom, -1f)
        mCornerRadiusRightTop =
            attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightTop, -1f)
        mCornerRadiusRightBottom =
            attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightBottom, -1f)

        //默认扩散区域宽度
        shadowLimit = attr.getDimension(R.styleable.ShadowLayout_hl_shadowLimit, 5f)

        //x轴偏移量
        mOffsetX = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetX, 0f)
        //y轴偏移量
        mOffsetY = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetY, 0f)
        mShadowColor = attr.getColor(R.styleable.ShadowLayout_hl_shadowColor, defaultShadowColor)
        attr.recycle()
        setPadding()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h)
        }
        rectf.left = leftPadding.toFloat()
        rectf.top = topPadding.toFloat()
        rectf.right = (width - rightPadding).toFloat()
        rectf.bottom = (height - bottomPadding).toFloat()
        initCornerValue((rectf.bottom - rectf.top).toInt())
    }


    /**
     * 被加载到页面后触发。也就是执行Inflater.inflate()方法后
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        firstView = getChildAt(0)
        if (firstView == null) {
            firstView = this@ShadowLayout
        }
    }

    val path = Path()

    /**
     * 对子View进行绘制，也是剪裁子view的关键
     *
     * @param canvas
     */
    override fun dispatchDraw(canvas: Canvas) {
        val trueHeight = (rectf.bottom - rectf.top).toInt()
        if (getChildAt(0) != null) {
            if (mCornerRadiusLeftTop == -1f
                && mCornerRadiusLeftBottom == -1f
                && mCornerRadiusRightTop == -1f
                && mCornerRadiusRightBottom == -1f
            ) {
                //说明没有设置过任何特殊角、且是半圆。
                if (cornerRadius > trueHeight / 2) {
                    val path = Path()
                    path.addRoundRect(
                        rectf,
                        (trueHeight / 2).toFloat(),
                        (trueHeight / 2).toFloat(),
                        Path.Direction.CW
                    )
                    canvas.clipPath(path)
                } else {
                    path.reset()
                    path.addRoundRect(rectf, cornerRadius, cornerRadius, Path.Direction.CW)
                    canvas.clipPath(path)
                }
            } else {
                if (cornerValues != null) {
                    path.reset()
                    path.addRoundRect(
                        leftPadding.toFloat(),
                        topPadding.toFloat(),
                        (width - rightPadding).toFloat(),
                        (height - bottomPadding).toFloat(),
                        cornerValues!!,
                        Path.Direction.CW
                    )
                    canvas.clipPath(path)
                }
            }
        }
        super.dispatchDraw(canvas)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


    }

    private var cornerValues: FloatArray? = null

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
        ) //左上，右上，右下，左下
    }


    private fun setPadding() {
        val xPadding = (shadowLimit + abs(mOffsetX.toDouble())).toInt()
        val yPadding = (shadowLimit + abs(mOffsetY.toDouble())).toInt()
        leftPadding = xPadding
        topPadding = yPadding
        rightPadding = xPadding
        bottomPadding = yPadding
        setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
    }


    @Suppress("deprecation")
    private fun setBackgroundCompat(w: Int, h: Int) {
        val bitmap = createShadowBitmap(w, h, mShadowColor)
        val drawable = BitmapDrawable(bitmap)
        background = drawable
    }


    private fun createShadowBitmap(width: Int, height: Int, shadowColor: Int): Bitmap {
        val shadowWidth = if (width / 4 == 0) 1 else width / 4
        val shadowHeight = if (height / 4 == 0) 1 else height / 4
        val cornerRadius = cornerRadius / 4
        val shadowDiffuse = shadowLimit / 4
        val dx = mOffsetX / 4
        val dy = mOffsetY / 4

        val output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(output)

        //这里缩小limit的是因为，setShadowLayer后会将bitmap扩散到shadowWidth，shadowHeight
        //同时也要根据某边的隐藏情况去改变
        val rectLeft = shadowDiffuse
        val rectRight = shadowWidth - shadowDiffuse
        val rectBottom = shadowHeight - shadowDiffuse

        val shadowRect = RectF(
            rectLeft,
            shadowDiffuse,
            rectRight,
            rectBottom
        )

        if (dy > 0) {
            shadowRect.top += dy
            shadowRect.bottom -= dy
        } else if (dy < 0) {
            shadowRect.top += (abs(dy.toDouble())).toFloat()
            shadowRect.bottom -= abs(dy.toDouble()).toFloat()
        }

        if (dx > 0) {
            shadowRect.left += dx
            shadowRect.right -= dx
        } else if (dx < 0) {
            shadowRect.left += (abs(dx.toDouble())).toFloat()
            shadowRect.right -= abs(dx.toDouble()).toFloat()
        }

        shadowPaint.color = Color.TRANSPARENT
        if (!isInEditMode) {
            shadowPaint.setShadowLayer(shadowDiffuse / 2, dx, dy, shadowColor)
        }

        if (mCornerRadiusLeftBottom == -1f && mCornerRadiusLeftTop == -1f && mCornerRadiusRightTop == -1f && mCornerRadiusRightBottom == -1f) {
            //如果没有设置整个属性，那么按原始去画
            canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint)
        } else {
            //目前最佳的解决方案
            rectf.left = leftPadding.toFloat()
            rectf.top = topPadding.toFloat()
            rectf.right = (width - rightPadding).toFloat()
            rectf.bottom = (height - bottomPadding).toFloat()

            shadowPaint.isAntiAlias = true
            val leftTop = if (mCornerRadiusLeftTop == -1f) {
                cornerRadius.toInt() / 4
            } else {
                mCornerRadiusLeftTop.toInt() / 4
            }
            val leftBottom = if (mCornerRadiusLeftBottom == -1f) {
                cornerRadius.toInt() / 4
            } else {
                mCornerRadiusLeftBottom.toInt() / 4
            }
            val rightTop = if (mCornerRadiusRightTop == -1f) {
                cornerRadius.toInt() / 4
            } else {
                mCornerRadiusRightTop.toInt() / 4
            }
            val rightBottom = if (mCornerRadiusRightBottom == -1f) {
                cornerRadius.toInt() / 4
            } else {
                mCornerRadiusRightBottom.toInt() / 4
            }

            val outerR = floatArrayOf(
                leftTop.toFloat(),
                leftTop.toFloat(),
                rightTop.toFloat(),
                rightTop.toFloat(),
                rightBottom.toFloat(),
                rightBottom.toFloat(),
                leftBottom.toFloat(),
                leftBottom.toFloat()
            ) //左上，右上，右下，左下
            val path = Path()
            path.addRoundRect(shadowRect, outerR, Path.Direction.CW)
            canvas.drawPath(path, shadowPaint)
        }

        return output
    }

    /**
     * 设置x轴阴影的偏移量
     *
     * @param mDx
     */
    fun setShadowOffsetX(mDx: Float): ShadowLayout {
        if (abs(mDx.toDouble()) > shadowLimit) {
            if (mDx > 0) {
                this.mOffsetX = this.shadowLimit
            } else {
                this.mOffsetX = -shadowLimit
            }
        } else {
            this.mOffsetX = mDx
        }
        setPadding()
        return this
    }

    /**
     * 设置y轴阴影的偏移量
     *
     * @param mDy
     */
    fun setShadowOffsetY(mDy: Float): ShadowLayout {
        if (abs(mDy.toDouble()) > shadowLimit) {
            if (mDy > 0) {
                this.mOffsetY = this.shadowLimit
            } else {
                this.mOffsetY = -shadowLimit
            }
        } else {
            this.mOffsetY = mDy
        }
        setPadding()

        return this
    }

    /**
     * 设置shadowLayout圆角
     *
     * @param mCornerRadius
     */
    fun setCornerRadius(mCornerRadius: Int): ShadowLayout {
        this.cornerRadius = mCornerRadius.toFloat()
        if (width != 0 && height != 0) {
            setBackgroundCompat(width, height)
        }
        return this
    }

    /**
     * 设置阴影扩散区域
     *
     * @param mShadowLimit
     */
    fun setShadowLimit(mShadowLimit: Int): ShadowLayout {
        this.shadowLimit = mShadowLimit.toFloat()
        setPadding()
        return this
    }

    /**
     * 设置阴影颜色值
     *
     * @param mShadowColor
     */
    fun setShadowColor(mShadowColor: Int): ShadowLayout {
        this.mShadowColor = mShadowColor
        if (width != 0 && height != 0) {
            setBackgroundCompat(width, height)
        }
        return this
    }


    /**
     * 单独设置4个圆角属性
     *
     * @param leftTop
     * @param rightTop
     * @param leftBottom
     * @param rightBottom
     */
    fun setSpecialCorner(
        leftTop: Int,
        rightTop: Int,
        leftBottom: Int,
        rightBottom: Int
    ): ShadowLayout {
        mCornerRadiusLeftTop = leftTop.toFloat()
        mCornerRadiusRightTop = rightTop.toFloat()
        mCornerRadiusLeftBottom = leftBottom.toFloat()
        mCornerRadiusRightBottom = rightBottom.toFloat()
        if (width != 0 && height != 0) {
            setBackgroundCompat(width, height)
        }
        return this
    }
}