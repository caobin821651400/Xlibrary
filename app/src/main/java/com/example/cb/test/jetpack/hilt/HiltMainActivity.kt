package com.example.cb.test.jetpack.hilt

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.jetpack.hilt.assisted.XiaoMiCarFactory
import com.example.cb.test.jetpack.hilt.context.ContextA
import com.example.cb.test.jetpack.hilt.context.ContextB
import com.example.cb.test.jetpack.hilt.impl.HiltImplModule
import com.example.cb.test.jetpack.hilt.impl.HiltInterface
import com.example.cb.test.jetpack.hilt.ok.OkHttpClient2
import com.example.cb.test.jetpack.hilt.ok.OkHttpModule.Companion.NAME_SPACE_OKHTTP_NO_INTERCEPT
import com.example.cb.test.jetpack.hilt.provider.HiltProvider
import com.example.cb.test.jetpack.hilt.single.SingleA
import com.example.cb.test.jetpack.hilt.single.SingleB
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 11:50
 * @Desc :Hilt使用
 * ====================================================
 */
@AndroidEntryPoint
class HiltMainActivity : BaseActivity() {


    private val tvContent by lazyNone { findViewById<TextView>(R.id.tvContent) }
    private val button by lazyNone { findViewById<Button>(R.id.button) }
    private val rootView by lazyNone { findViewById<LinearLayout>(R.id.root_view) }

    @Inject
    lateinit var mHiltProvider: HiltProvider

    @Inject
    @HiltImplModule.Type1
    lateinit var mHiltInterface: HiltInterface

    @Inject
    @HiltImplModule.Type2
    lateinit var mHiltInterface2: HiltInterface

    @Inject
    lateinit var mOkHttp: OkHttpClient2

    @Named(NAME_SPACE_OKHTTP_NO_INTERCEPT)
    @Inject
    lateinit var mOkHttpNoIntercept: OkHttpClient2

    @Inject
    lateinit var mSingleA: SingleA

    @Inject
    lateinit var mSingleB: SingleB

    @Inject
    lateinit var mContextB: ContextB

    @Inject
    lateinit var mContextA: ContextA

    @Inject
    lateinit var mXiaoMiCarFactory: XiaoMiCarFactory

    private val viewModule by viewModels<HiltViewModule>()

    override fun getLayoutId(): Int {
        return R.layout.activity_hilt_main
    }


    override fun initUI() {
        setHeaderTitle("Hilt使用")
        //注入普通类
        XLogUtils.v("joker log1=${mHiltProvider.getValue1()}")
        XLogUtils.e("joker log2=${mHiltProvider.getChildValue1()}")
        XLogUtils.d("joker log3=${mHiltProvider.getSecondChildValue1()}")

        XLogUtils.e("==========================================")
        //接口实例
        mHiltInterface.doSomeThing()
        mHiltInterface2.doSomeThing()

        XLogUtils.e("==========================================")
        //三方SDK 不能修改构造方法的情况
        mOkHttp.request()
        mOkHttpNoIntercept.request("无拦截器")

        XLogUtils.e("==========================================")
        //单例对象
        mSingleA.log()
        //Activity作用域对象
        mSingleB.log()

        XLogUtils.e("==========================================")
        //ApplicationContext
        mContextA.logString()
        //ActivityContext
        mContextB.logString()

        XLogUtils.e("==========================================")
        val car = mXiaoMiCarFactory.createCar(1)
        XLogUtils.v("joker 小米汽车配置 轮胎：${car.wheel.name} " +
                "电机：${car.engine.name} 生成编号：${car.number} 价格：${car.price} ");

        val car2 = mXiaoMiCarFactory.createCar(2,21)
        XLogUtils.i("joker 小米汽车配置 轮胎：${car2.wheel.name} " +
                "电机：${car2.engine.name} 生成编号：${car2.number} 价格：${car2.price} ");
    }


    @SuppressLint("SetTextI18n")
    override fun initEvent() {
        button.setOnClickListener {

        }
    }
}
