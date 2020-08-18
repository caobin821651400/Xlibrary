package com.example.cb.test.kotlin.coroutines.net

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import cn.sccl.xlibrary.kotlin.AppGsonObject
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_http.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:12
 * @Desc :协程结合ViewModule请求
 * ====================================================
 */
class HttpCoroutinesActivity : BaseActivity() {

    private val netViewModule by viewModels<NetViewModule>()

    private val netViewModule2 by viewModels<NetViewModule2>()

    override fun getLayoutId(): Int {
        return R.layout.activity_http
    }

    override fun initUI() {
        setHeaderTitle("协程请求")

        supportFragmentManager.beginTransaction().add(R.id.content, HttpCoroutinesFragment()).commit()

        val aa = netViewModule.dataModule
        netViewModule.dataModule.observe(this, Observer {
            XLogUtils.d("当前线程4->${Thread.currentThread().name}")
            it.result(
                    { result ->
                        tvResult.text = AppGsonObject.toJson(result)
                    },
                    { _, msg ->
                        toast(msg)
                    })
        })


        netViewModule2.dataModule.observe(this, Observer {
            XLogUtils.d("当前线程4->${Thread.currentThread().name}")
            tvResult.text = AppGsonObject.toJson(it)
        })
    }


    override fun initEvent() {
        btnRequest.setOnClickListener {
//            netViewModule.getData()
            netViewModule2.getData()
        }
    }
}