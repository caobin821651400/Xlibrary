package cn.sccl.xlibrary.view.shadow

/**
 * @author: cb
 * @date: 2025/3/25
 * @desc: 描述
 */
interface ShadowLayoutImpl {

    /**
     * 圆角
     * @return Float
     */
    fun getCornerRadius(): Float

    /**
     * 阴影扩散区域
     * @return Float
     */
    fun getShadowLimit(): Float

    /**
     * 设置x轴阴影的偏移量
     *
     * @param dx
     */
    fun setShadowOffsetX(dx: Float)

    /**
     * 设置y轴阴影的偏移量
     *
     * @param dy
     */
    fun setShadowOffsetY(dy: Float)

    /**
     * X轴阴影偏移量
     * @return Float
     */
    fun getShadowOffsetX(): Float

    /**
     * Y轴阴影偏移量
     * @return Float
     */
    fun getShadowOffsetY(): Float

    /**
     * 设置ShadowLayoutImpl圆角
     *
     * @param cornerRadius
     */
    fun setCornerRadius(cornerRadius: Int)

    /**
     * 设置阴影扩散区域
     *
     * @param shadowLimit
     */
    fun setShadowLimit(shadowLimit: Int)

    /**
     * 设置阴影颜色值
     *
     * @param color
     */
    fun setShadowColor(color: Int)

    /**
     * 设置描边颜色值
     *
     * @param color
     */
    fun setStrokeColor(color: Int)

    /**
     * 描边的宽度
     * @param width Float
     */
    fun setStrokeWidth(width: Float)

    /**
     * 允许描边
     * @param enable Boolean
     */
    fun enableStrokeWidth(enable: Boolean)

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
    )
}