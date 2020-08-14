package com.example.cb.test.kotlin

import android.widget.TextView
import cn.sccl.xlibrary.kotlin.*
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.PersonBean
import com.example.cb.test.kotlin.lambda.LambdaListener
import com.example.cb.test.kotlin.lambda.LambdaListener2
import com.example.cb.test.utils.TOKEM
import com.example.cb.test.utils.getToken
import com.example.cb.test.utils.txt1
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_kotlin_test.*
import org.json.JSONArray

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

        //12.全局方法 & 常量
        XLogUtils.d("token1=$TOKEM")
        XLogUtils.e("token2=${getToken()}")

        //13.require  false会抛异常
        //require负责检查输入的参数，如果有问题，抛出IllegalArgumentException
        //check负责检查自身是否万事俱备可以执行了，如果不是，抛出IllegalStateException
        //assert负责确保程序执行完毕后的结果/内部状态是否符合预期，如果不是，抛出AssertionError
//        require(TOKEM.isNullOrEmpty()){"TOKEM is no null"}
//        check(TOKEM.isNullOrEmpty()){"TOKEM is no null"}
//        assert(TOKEM.isNullOrEmpty()){"TOKEM is no null"}


        //14.JSONArray遍历
        val jsonArray = JSONArray()
        jsonArray.put("1")
        jsonArray.put("2")
        jsonArray.put("3")

        if (jsonArray.isNoEmpty()) {
            XLogUtils.i("JSONArray no null")
        }
        //第一种
        jsonArray.forEach<String> {
            XLogUtils.d("JSONArray forEach循环结果=$it")
        }
        //第二种
        jsonArray.forEachIndex<String> { index, t ->
            XLogUtils.d("JSONArray forEachIndex循环结果=$t   位置=$index")

        }

        //15.全局单例对象
        val str = """["123","456"]"""
        val list = AppGsonObject.fromJson<List<String>>(str, object : TypeToken<List<String>>() {}.type)
        if (!list.isNullOrEmpty()) {
            XLogUtils.e("列表数据=${list[0]}")
        }
    }
}
