package com.example.cb.test.kotlin

import android.os.Bundle
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.PersonBean

/**
 * 记录一些好用的语句and方法
 */
class KotlinFunActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_fun)
        initView()
    }

    private fun initView() {
        //查询集合中最大值&最小值
        val people = listOf(PersonBean("曹斌", 23), PersonBean("武历婷", 34))
        XLogUtils.d("最大值1->" + people.maxBy { it.age })
        XLogUtils.i("最小值1->" + people.minBy { it.age })
        people.forEach { p: PersonBean ->
            XLogUtils.e("遍历people->${p.name}")
        }

        //map集合取最大值&最小值
        val courseMap = mapOf(1 to "数学", 2 to "语文", 3 to "物理", 4 to "化学")
        val key: Map.Entry<Int, String>? = courseMap.maxBy { it.key }
        XLogUtils.d("最大值2->${courseMap[key!!.key]} ")
        XLogUtils.i("最小值2->" + courseMap.minBy { it.key })
        courseMap.forEach {
            XLogUtils.d("遍历数组->${it.key}   值：${it.value}")
        }
    }
}
