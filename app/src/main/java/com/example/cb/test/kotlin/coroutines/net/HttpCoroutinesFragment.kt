package com.example.cb.test.kotlin.coroutines.net

import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import cn.sccl.xlibrary.kotlin.AppGsonObject
import com.example.cb.test.R
import com.example.cb.test.base.BaseFragment
import kotlinx.coroutines.launch

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 17:20
 * @Desc :
 * ====================================================
 */
class HttpCoroutinesFragment : BaseFragment() {

//    private val netViewModule by activityViewModels<NetViewModule>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_kotlin_http
    }

    override fun initUI(v: View?) {
        lifecycleScope.launch {  }
//        val aa = netViewModule.dataModule
//        netViewModule.dataModule.observe(this, Observer {
//            tvContent.text = AppGsonObject.toJson(it)
//        })

    }


    override fun initEvent(view: View?) {
    }
}