package com.example.cb.test.kotlin.function

import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_function_inline.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2021/2/26 15:17
 * @Desc :函数相关
 * ====================================================
 */
class FunctionInlineActivity : BaseActivity() {


    override fun getLayoutId() = R.layout.activity_function_inline


    override fun initUI() {
        //https://www.bilibili.com/video/BV1kz4y1f7sf/ 视频讲解
        //如果你写的是高阶函数，会有函数类型的参数，加上 inline 就对了。
        //crossinline  只会跳出当前lambda中的return对整个流程没有影响，只会跳出当前lambda;
        //函数类型的参数，它本质上是个对象
        //inline 会让函数类型的参数的返回值失效   block: suspend () -> String, --->
        // 也就是return block失效

        //kotlin 规定 Lambda 中不能直接使用return 除非这个lambda是内联函数的参数

        //crossinline使用及效果
        button1.setOnClickListener {
            XLogUtils.e("=======================")
            startCrossInline()
            XLogUtils.e("=======================\n")
        }


        //inline使用及效果
        button2.setOnClickListener {
            XLogUtils.d("=======================")
            startinline()
            XLogUtils.d("=======================\n")
        }

        //noline使用及效果
        button3.setOnClickListener {
            XLogUtils.v("=======================")
            val func = nolineTest { XLogUtils.v("nolineTest") }

            func()
            XLogUtils.v("=======================\n")
        }
    }

    /**
     * 单独弄出来是因为 按钮的setOnClickListener 是Lambda
     * 编译期不知道 return 到底是 return  setOnClickListener 还是return当前方法
     */
    private fun startCrossInline() {
        crossinlineTest {
            XLogUtils.d("调用return")
            //加上 crossinline 只会跳出 内联的func()
            //而且必须在return后面加上 @crossinlineTest2，否则编译期会报错
            return@crossinlineTest
        }
        //上述高阶函数就变成了下面这种顺序

        // 1.XLogUtils.e(" crossinlineTest() 内部执行开始")
        // 2. XLogUtils.d("调用return")
        // 3. return@crossinlineTest 相当于return func()
        // 4.XLogUtils.e(" crossinlineTest() 内部执行完毕")
        // 第三部的return直接跳出了startCrossInline() 所以第四布就不会执行
    }

    /**
     *
     */
    private fun startinline() {
        inlineTest {
            XLogUtils.d("调用return")
            return//如果没加 crossinline 则直接跳出了crossinlineTest()
        }
        //上述高阶函数就变成了下面这种顺序
        // 1.XLogUtils.e(" crossinlineTest() 内部执行开始")
        // 2.XLogUtils.d("调用return")
        // 3.return  这里的return 直接跳出了inlineTest()
        // 4.  XLogUtils.e(" crossinlineTest() 内部执行完毕")
        // 第三部的return直接跳出了startCrossInline() 所以第四布就不会执行
    }

    /**
     * 因为inline关键字  func()代码会被编译期 原封不动的“拷贝”到调用处
     */
    private inline fun crossinlineTest(crossinline func: () -> Unit) {
        XLogUtils.e(" crossinlineTest() 内部执行开始")
        //执行方法func 以下两种都可以
        runOnUiThread { func() }
//        func()
        XLogUtils.e(" crossinlineTest() 内部执行完毕")
    }

    /**
     *
     */
    private inline fun inlineTest(func: () -> Unit) {
        XLogUtils.e(" crossinlineTest() 内部执行开始")
        //执行方法func
//        runOnUiThread { func() } 这种操作 不允许
        func()
        XLogUtils.e(" crossinlineTest() 内部执行完毕")
    }


    /**
     * 如果 func不加 noinline 则编译器会报错
     * noinline 简单理解就是把func当成一个对象返回,不会讲方法内联到调用处
     */
    private inline fun nolineTest(noinline func: () -> Unit): () -> Unit {
        return func
    }

    override fun initEvent() {

    }
}