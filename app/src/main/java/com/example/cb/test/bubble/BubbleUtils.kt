package com.example.cb.test.bubble

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 *
 * @author cb
 * @date 2024/12/18
 */
class BubbleUtils {

    companion object {

        /**
         * 颜色透明度饱和度转换,需求如下
         * 当背景饱和度 < 50时，气泡颜色基于背景色，亮度增加 16
         * 当背景饱和度 ≥ 50时，气泡颜色基于背景色，亮度减少 16
         * @param color Int
         * @return Int
         */
        fun calculateColor(@ColorInt color: Int): Int {
            var result = color
            kotlin.runCatching {
                val hsv = FloatArray(3)
                Color.colorToHSV(color, hsv)

                //亮度范围0-1，UI那边是0-100
                var brightness = hsv[2]
                brightness = if (brightness < 0.5f) {
                    brightness + 0.16f
                } else {
                    brightness - 0.16f
                }
                result = Color.HSVToColor(floatArrayOf(hsv[0], hsv[1], brightness))
            }
            return result
        }
    }
}