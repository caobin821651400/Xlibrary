package com.example.cb.test.kotlin.coroutines.net

import androidx.lifecycle.MutableLiveData
import cn.sccl.xlibrary.utils.XLogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:16
 * @Desc :
 * ====================================================
 */
class NetViewModule2 : BaseViewModule() {

    //这个类单例
    val dataModule by lazy { MutableLiveData<ZnsListBean>() }

    fun getData() {
//        request({
//            withContext(Dispatchers.IO) {
//                XLogUtils.d("当前线程1->${Thread.currentThread().name}")
//                RetrofitFactory.instance
//                        .getService(ApiService::class.java).getData2(1, 2)
//            }
//        }, {
//            //请求成功
//            dataModule.value = it
//            XLogUtils.d("成功->" + it.arr[0].propaganda_name)
//            XLogUtils.d("当前线程2->${Thread.currentThread().name}")
//        }, {
//            //请求失败
//            XLogUtils.d("失败")
//            XLogUtils.d("当前线程3->${Thread.currentThread().name}")
//        })

        requestString(
                {
                    withContext(Dispatchers.IO) {
                        XLogUtils.d("当前线程1->${Thread.currentThread().name}")
                        RetrofitFactory.instance.getService(ApiService::class.java).getDataString(1, 2).body()?.string()
                                ?: ""
                    }
                },
                {
                    //请求成功
                    XLogUtils.d("成功->$it")
                    XLogUtils.d("当前线程2->${Thread.currentThread().name}")
                },
                {
                    //请求失败
                    XLogUtils.d("失败->" + it.errCode + "   msg->${it.errorMsg}")
                    XLogUtils.d("当前线程3->${Thread.currentThread().name}")
                }
        )
    }
}