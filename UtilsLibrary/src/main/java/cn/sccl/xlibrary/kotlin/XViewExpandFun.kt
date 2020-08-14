package cn.sccl.xlibrary.kotlin

import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 12:56
 * @Desc :View扩展函数
 * ====================================================
 */

/**
 * 设置View显示隐藏
 */
fun View.setVisible(show: Boolean, isGone: Boolean = true) {
    this.visibility = if (show) View.VISIBLE else
        (if (isGone) View.GONE else View.INVISIBLE)
}

/**
 * 判断View显示隐藏
 */
fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

/**
 * TextView DrawableLeft
 */
fun TextView.setDrawableLeft(@DrawableRes resId: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0)
}

/**
 * TextView DrawableTop
 */
fun TextView.setDrawableTop(@DrawableRes resId: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0)
}

/**
 * TextView DrawableRight
 */
fun TextView.setDrawableRight(@DrawableRes resId: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0)
}

/**
 * TextView DrawableBottom
 */
fun TextView.setDrawableBottom(@DrawableRes resId: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resId)
}