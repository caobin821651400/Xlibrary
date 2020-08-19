package com.example.cb.test.kotlin.coroutines.net

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.sccl.xlibrary.kotlin.AppGsonObject
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseViewModuleActivity
import kotlinx.android.synthetic.main.activity_http.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:12
 * @Desc :协程结合ViewModule请求
 * ====================================================
 */
class HttpCoroutinesActivity : BaseViewModuleActivity<NetViewModule>() {

    override fun getLayoutId() = R.layout.activity_http

    override fun createViewModel() = ViewModelProvider(this).get(NetViewModule::class.java)

    override fun initUI() {
        setHeaderTitle("协程请求")

        supportFragmentManager.beginTransaction().add(R.id.content, HttpCoroutinesFragment()).commit()

        mViewModule.dataModule.observe(this, Observer {
            XLogUtils.d("当前线程44->${Thread.currentThread().name}")
            tvResult.text = AppGsonObject.toJson(it)
        })
    }

    override fun initEvent() {
        btnRequest.setOnClickListener {
            mViewModule.getData()
            mViewModule.getData2()
        }
    }


}