package cn.sccl.xlibrary.view.shadow

/**
 *
 * @author cb
 * @date 2025/2/6
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
     * @param shadowColor
     */
    fun setShadowColor(shadowColor: Int)


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