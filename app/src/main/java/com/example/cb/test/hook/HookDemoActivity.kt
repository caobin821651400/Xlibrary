package com.example.cb.test.hook

import android.content.Intent
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.TestActivity
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_hook.*

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/17 14:20
 * @Desc :
 * ====================================================
 */
class HookDemoActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_hook
    }

    override fun initUI() {}

    override fun initEvent() {
        btnHook1.setOnClickListener { hookConstant() }
        btnHook2.setOnClickListener {
            HookNoResActivityHelper.hookIActivityManager()
            startActivity(Intent(this, TestActivity::class.java));
        }
        btnHook3.setOnClickListener { hookMethod() }

    }

    /**
     * 简单的反射
     */
    private fun hookConstant() {
        try {
            val hookClass = Class.forName("com.example.cb.test.hook.HookObject")
            val hookObject = hookClass.newInstance() as HookObject
            XLogUtils.e("原始值-->" + hookObject.constant)

            val mGroupFlagsField = hookClass.getDeclaredField("mGroupFlags")
            mGroupFlagsField.isAccessible = true

            //如果hook的对象是静态的可以不传，不是静态的就要传该类的对象
            val i = mGroupFlagsField.get(hookObject) as Int
            XLogUtils.e("Hook开始获取到的值->>$i")
            mGroupFlagsField.set(hookObject, 100)

//            val i = mGroupFlagsField.get(null) as Int
//            XLogUtils.e("Hook开始获取到的值->>$i")
//            mGroupFlagsField.set(null, 100)

            XLogUtils.e("Hook结束获取到的值通过对象获取->>${hookObject.constant}")
//            XLogUtils.e("Hook结束获取到的值，通过hook获取->>${mGroupFlagsField.get(null) as Int}")
            XLogUtils.e("Hook结束获取到的值，通过hook获取->>${mGroupFlagsField.get(hookObject) as Int}")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 反射方法
     */
    private fun hookMethod() {
        //private和protected方法请用Class.getDeclaredMethod()
        //public方法请用Class.getMethod()
        try {
            val hookClass = Class.forName("com.example.cb.test.hook.HookObject")
            val hookObject = hookClass.newInstance() as HookObject

            //第一种(无参数public方法)
            val method = hookClass.getMethod("hookMethod")
            val obj = method.invoke(hookObject)
            XLogUtils.d("第一种结果-->$obj")
            XLogUtils.e("-----------------------------分割线-----------------------------")


            //第二种(无参数private和protected方法)
            val method2 = hookClass.getDeclaredMethod("hookMethod2")
            method2.isAccessible = true
            val obj2 = method2.invoke(hookObject)
            XLogUtils.d("第二种结果-->$obj2")
            XLogUtils.e("-----------------------------分割线-----------------------------")


            //第三种(有参数)
            val method3 = hookClass.getDeclaredMethod("hookMethod3", Int::class.java)
            method3.isAccessible = true
            val obj3 = method3.invoke(hookObject, 1000)
            XLogUtils.d("第三种结果-->$obj3")
            XLogUtils.e("-----------------------------分割线-----------------------------")


            //第四种(有参数有返回值)
            val method4 = hookClass.getDeclaredMethod("hookMethod4", String::class.java)
            method4.isAccessible = true
            val obj4 = method4.invoke(hookObject, "啦啦啦")
            XLogUtils.d("第四种结果-->$obj4")
            XLogUtils.e("-----------------------------分割线-----------------------------")


            //第五种(有参数有返回值)
            val method5 = hookClass.getDeclaredMethod("hookMethod5")
            method5.isAccessible = true
            val obj5 = method5.invoke(hookObject)
            XLogUtils.d("第五种结果-->$obj5")
            XLogUtils.e("-----------------------------分割线-----------------------------")


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}