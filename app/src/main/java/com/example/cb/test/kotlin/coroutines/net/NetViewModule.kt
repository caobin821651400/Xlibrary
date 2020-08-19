package com.example.cb.test.kotlin.coroutines.net

import androidx.lifecycle.MutableLiveData
import cn.sccl.net.library.XHttp
import cn.sccl.net.library.core.BaseViewModule
import cn.sccl.net.library.core.request
import cn.sccl.net.library.core.requestNoCheck
import cn.sccl.xlibrary.kotlin.AppGsonObject
import cn.sccl.xlibrary.utils.XLogUtils

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:16
 * @Desc :
 * ====================================================
 */
class NetViewModule : BaseViewModule() {

    //这个类单例
    val dataModule by lazy { MutableLiveData<ZnsListBean>() }

    fun getData() {
        request(
                {
                    XHttp.getService(ApiService::class.java).getData2(1, 2)
                },
                {
                    //请求成功
                    dataModule.value = it
                    XLogUtils.d("成功->" + it.arr[0].propaganda_name)
                },
                {
                    //请求失败
                    XLogUtils.d("失败")
                }, isShowDialog = true)

//        requestString(
//                {
//                    XLogUtils.d("当前线程1->${Thread.currentThread().name}")
//                    RetrofitFactory.instance.getService(ApiService::class.java).getDataString(1, 2).body()?.string()
//                            ?: ""
//                },
//                {
//                    //请求成功
//                    XLogUtils.d("成功->$it")
//                    XLogUtils.d("当前线程2->${Thread.currentThread().name}")
//                },
//                {
//                    //请求失败
//                    XLogUtils.d("失败->" + it.errCode + "   msg->${it.errorMsg}")
//                    XLogUtils.d("当前线程3->${Thread.currentThread().name}")
//                }
//        )
    }

    fun getData2() {
        requestNoCheck(
                {
                    XHttp.getService(ApiService::class.java).getScenesData(1, 1,
                            "1019047515184193536",
                            "993460313977020416",
                            "6F18E5779FFD4417967791C452A3E27F")
                },
                {
                    XLogUtils.d("成功->" + AppGsonObject.toJson(it.data))
                },
                {
                    XLogUtils.d("失败->" + it.errorMsg)
                })
    }
}