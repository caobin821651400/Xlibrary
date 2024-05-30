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
import com.example.cb.test.jetpack.hilt.impl.HiltInterface
import com.example.cb.test.jetpack.hilt.ok.OkHttpClient2
import com.example.cb.test.jetpack.hilt.provider.HiltProvider
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject

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
    lateinit var mHiltInterface: HiltInterface
    @Inject
    lateinit var mOkHttp: OkHttpClient2

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


        //接口实例
        mHiltInterface.doSomeThing()

        //三方SDK 不能修改构造方法的情况
        mOkHttp.request()
    }


    @SuppressLint("SetTextI18n")
    override fun initEvent() {
        button.setOnClickListener {

        }
    }
}
