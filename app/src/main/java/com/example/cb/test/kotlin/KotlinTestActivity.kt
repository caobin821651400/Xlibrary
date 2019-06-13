package com.example.cb.test.kotlin

import android.os.Bundle
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.PersonBean

class KotlinTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)
        initView()
    }

    private fun initView() {
        val people = arrayListOf(PersonBean("汤姆", 23), PersonBean("杰瑞", 34))

        //1.意思是将PersonBean对象中的name单独取出来作为一个新的集合
        XLogUtils.d(people.map { it.name }.toString())

        //2.代码意思与1相同，这里用了
        var list = people.map(PersonBean::name)
        XLogUtils.i(list[0])
        XLogUtils.e(people.map(PersonBean::name).toString())

        //3.数据类data要求:
        //  A:主构造必须要有一个参数
        //  B:主构造所有参数必须用var或val声明
        //  C:数据类不能用abstract,open,sealed修饰,也不能定义成内部类
        //系统为data类生成如下内容
        //  A:生成equals()和hashCode()方法
        //  B:自动重写toString()方法
        //  C:每个属性都自动生成operator修饰的componentN()方法
        //  D:生成copy()方法，用于完成复制

    }
}
