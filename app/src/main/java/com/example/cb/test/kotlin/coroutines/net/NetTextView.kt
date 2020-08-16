package com.example.cb.test.kotlin.coroutines.net

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.sccl.xlibrary.kotlin.AppGsonObject

internal class NetTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) :
        AppCompatTextView(context, attrs, defStyleAttr) {


    //        private val netViewModule by viewModels<NetViewModule>()
    private var netViewModule: NetViewModule =
            ViewModelProvider(context as AppCompatActivity).get(NetViewModule::class.java)

    init {
        val aa = netViewModule.dataModule
        netViewModule.dataModule.observe(context as AppCompatActivity, Observer {
            text = AppGsonObject.toJson(it.arr[0].propaganda_name)
        })

    }
}