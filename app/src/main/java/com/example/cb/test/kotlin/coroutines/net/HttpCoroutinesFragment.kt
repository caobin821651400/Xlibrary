package com.example.cb.test.kotlin.coroutines.net

import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.sccl.xlibrary.kotlin.AppGsonObject
import com.example.cb.test.R
import com.example.cb.test.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_kotlin_http.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 17:20
 * @Desc :
 * ====================================================
 */
class HttpCoroutinesFragment : BaseFragment() {

//    lateinit var netViewModule: NetViewModule
    private val netViewModule by activityViewModels<NetViewModule>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_kotlin_http
    }

    override fun initUI(v: View?) {

//        netViewModule = ViewModelProvider(activity!!).get(NetViewModule::class.java)
        val aa = netViewModule.dataModule
        netViewModule.dataModule.observe(this, Observer {
            tvContent.text = AppGsonObject.toJson(it)
        })
    }


    override fun initEvent(view: View?) {
    }
}