package com.example.cb.test.kotlin.coroutines.net

import androidx.lifecycle.MutableLiveData
import cn.sccl.net.library.XHttp
import cn.sccl.net.library.core.BaseViewModule
import cn.sccl.net.library.core.request
import com.example.cb.test.kotlin.coroutines.XListPageDataUi

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:16
 * @Desc :
 * ====================================================
 */
class NetViewModule : BaseViewModule() {

    //页码 首页数据页码从0开始
    var pageNo = 0

    //这个类单例
    val dataModule by lazy { MutableLiveData<XListPageDataUi<WanAndroidBean>>() }


    /**
     * 加载列表数据
     * @param isRefresh 是否是刷新。第一次加载相当于是刷新
     * @param isLoadError 加载更多出错
     */
    fun getData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ XHttp.getService(ApiService::class.java).getArticleList(pageNo) }, {
            //请求成功
            pageNo++
            dataModule.value = XListPageDataUi(
                    isSuccess = true,
                    isEmpty = it.isEmpty(),
                    isRefresh = isRefresh,
                    isHasMore = it.isLoadMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas

            )
        }, {
            //请求失败
            dataModule.value = XListPageDataUi(
                    isSuccess = false,
                    errMsg = it.errorMsg,
                    isRefresh = isRefresh

            )
        })

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
//        requestNoCheck(
//                {
//                    XHttp.getService(ApiService::class.java).getScenesData(1, 1,
//                            "1019047515184193536",
//                            "993460313977020416",
//                            "6F18E5779FFD4417967791C452A3E27F")
//                },
//                {
//                    XLogUtils.d("成功->" + AppGsonObject.toJson(it.data))
//                },
//                {
//                    XLogUtils.d("失败->" + it.errorMsg)
//                })
    }
}