package cn.sccl.xlibrary.view.shadow

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout


/**
 * 阴影FrameLayout
 * @author: cb
 * @date: 2025/1/25
 * @desc: 描述
 */
class ShadowFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ShadowLayoutImpl {

    private val mShadowHelper = ShadowHelper(this)

    init {
        mShadowHelper.parseAttributes(context, attrs)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mShadowHelper.onSizeChanged(w, h)
    }

    /**
     * 对子View进行绘制，也是剪裁子view的关键
     *
     * @param canvas
     */
    override fun dispatchDraw(canvas: Canvas) {
        mShadowHelper.dispatchDraw(canvas)
        super.dispatchDraw(canvas)
    }

    override fun getCornerRadius(): Float {
        return mShadowHelper.getCornerRadius()
    }

    override fun getShadowLimit(): Float {
        return mShadowHelper.getShadowLimit()
    }

    /**
     * 设置x轴阴影的偏移量
     *
     * @param dx
     */
    override fun setShadowOffsetX(dx: Float) {
        mShadowHelper.setShadowOffsetX(dx)
    }

    /**
     * 设置y轴阴影的偏移量
     *
     * @param dy
     */
    override fun setShadowOffsetY(dy: Float) {
        mShadowHelper.setShadowOffsetY(dy)
    }

    override fun getShadowOffsetX(): Float {
        return mShadowHelper.getShadowOffsetX()
    }

    override fun getShadowOffsetY(): Float {
        return mShadowHelper.getShadowOffsetY()
    }

    /**
     * 设置shadowLayout圆角
     *
     * @param cornerRadius
     */
    override fun setCornerRadius(cornerRadius: Int) {
        mShadowHelper.setCornerRadius(cornerRadius)
    }

    /**
     * 设置阴影扩散区域
     *
     * @param shadowLimit
     */
    override fun setShadowLimit(shadowLimit: Int) {
        mShadowHelper.setShadowLimit(shadowLimit)
    }

    /**
     * 设置阴影颜色值
     *
     * @param shadowColor
     */
    override fun setShadowColor(shadowColor: Int) {
        mShadowHelper.setShadowColor(shadowColor)
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
        mShadowHelper.setSpecialCorner(
            leftTop,
            rightTop,
            leftBottom,
            rightBottom
        )
    }
}