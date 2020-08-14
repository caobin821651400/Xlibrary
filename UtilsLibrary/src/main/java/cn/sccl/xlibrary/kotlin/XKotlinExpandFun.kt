package cn.sccl.xlibrary.kotlin

import cn.sccl.xlibrary.XLibrary
import cn.sccl.xlibrary.utils.XDensityUtils
import com.google.gson.Gson
import org.json.JSONArray

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/13 17:37
 * @Desc :Kotlin 扩展方法
 * ====================================================
 */

/**
 * APP全局Gson对象
 */
val AppGsonObject = Gson()

/**
 * dp2px
 */
fun dp2px(dp: Float) {
    XDensityUtils.dp2px(XLibrary.getContext(), dp)
}

/**
 * sp2px
 */
fun sp2px(sp: Float) {
    XDensityUtils.sp2px(XLibrary.getContext(), sp)
}

/**
 * JSONArray为空
 */
fun JSONArray?.isNullOrEmpty(): Boolean {
    return this == null || this.length() == 0
}

/**
 * JSONArray 不为空并且有数据
 */
fun JSONArray?.isNoEmpty(): Boolean {
    return this != null && this.length() > 0
}

/**
 * JSONArray循环
 */
fun <T> JSONArray.forEach(action: (T) -> Unit): Unit {
    if (this.isNullOrEmpty()) return
    for (i in 0 until this.length()) action(this[i] as T)
}

/**
 * JSONArray循环 带位置
 */
fun <T> JSONArray.forEachIndex(action: (index: Int, T) -> Unit): Unit {
    if (this.isNullOrEmpty()) return
    for (i in 0 until this.length()) {
        action(i, this[i] as T)
    }
}

