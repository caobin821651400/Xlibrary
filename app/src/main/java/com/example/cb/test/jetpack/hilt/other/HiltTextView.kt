package com.example.cb.test.jetpack.hilt.other

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import javax.inject.Inject

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 16:19
 * @Desc :
 * ====================================================
 */
class HiltTextView @Inject constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : TextView(context, attrs, defStyleAttr) {

    /**
     * @Inject 不能是private的
     */


}