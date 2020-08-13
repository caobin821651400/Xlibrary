package com.example.cb.test.kotlin

import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.PersonBean
import com.example.cb.test.kotlin.lambda.LambdaListener
import com.example.cb.test.kotlin.lambda.LambdaListener2
import com.example.cb.test.utils.txt1
import kotlinx.android.synthetic.main.activity_kotlin_test.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 11:29
 * @Desc :
 * ====================================================
 */
class KotlinTestActivity : BaseActivity() {

    open var s = "111"

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin_test
    }

    override fun initEvent() {
    }


    override fun initUI() {
        setHeaderTitle("Kotlin基础")
        val people = arrayListOf(PersonBean("汤姆", 23), PersonBean("杰瑞", 34))

        //1. -->意思是将PersonBean对象中的name单独取出来作为一个新的集合
//        XLogUtils.d(people.map { it.name }.toString())

        //2. -->代码意思与1相同，这里用了
//        var list = people.map(PersonBean::name)
//        XLogUtils.e(list.toString())

        //3. --> 数据类data要求:
        //  A:主构造必须要有一个参数
        //  B:主构造所有参数必须用var或val声明
        //  C:数据类不能用abstract,open,sealed修饰,也不能定义成内部类
        //系统为data类生成如下内容
        //  A:生成equals()和hashCode()方法
        //  B:自动重写toString()方法
        //  C:每个属性都自动生成operator修饰的componentN()方法
        //  D:生成copy()方法，用于完成复制

        //4.遍历JS对象，key不同的情况
//        val txt = """{"0":{"name":"汤姆","age":"24"},"1":{"name":"酸奶","age":"10"}}"""
//        val jsonObject = JSONObject(txt)
//        val keys = jsonObject.keys()
//        while (keys.hasNext()) {
//            XLogUtils.d(jsonObject.getJSONObject(keys.next()).toString())
//        }

        //5. --> if(a>b)a else b
        var a = null
        var b = "ss"
        var c = a ?: b
        XLogUtils.d(c)

        //6. -->无参数接口支持lambda
        var lambda = LambdaListener()
        lambda.setListener {
            XLogUtils.d("无参数接口回调")
        }
        lambda.onClick()

        var lambda2 = LambdaListener2()
        lambda2.setLambda {
            XLogUtils.i("无参数接口JAVA版回调")
        }
        lambda2.onClick()

        //7. -->有参数无返回值接口支持lambda
        var lambda3 = LambdaListener()
        lambda3.setListener2 { position ->
            XLogUtils.v("有参数回调无返回值-> $position")
        }
        lambda3.onClick2()


        //8. -->有参数有返回值接口支持lambda
        val lambda4 = LambdaListener()
        lambda4.setListener3 { position ->
            XLogUtils.d("有参数回调有返回值-> $position")
            500//返回值放到最后
        }
        lambda4.onClick3()

        //9.扩展函数
        tv.txt1("22")

        //10.with函数
        val aa = PersonBean("汤姆", 23)
//        val aa: PersonBean? = null
        with(aa) {
            XLogUtils.d("with函数->${this?.name}")
            XLogUtils.d("with函数->${this?.age}")
        }

        //11.run函数：结合了let和with
//        val aaa = PersonBean("汤姆", 23)
        val aaa: PersonBean? = null
        aaa?.run {
            XLogUtils.d("run函数->name=$name    age=$age")
        }
    }
}
