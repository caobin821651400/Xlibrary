package com.example.cb.test.kotlin.coroutines.net

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.utils.dataConvert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:16
 * @Desc :
 * ====================================================
 */
class NetViewModule : ViewModel() {

    //这个类单例
    val dataModule by lazy { MutableLiveData<ZnsListBean>() }

    fun getData() {
        viewModelScope.launch() {
            try {
                dataModule.value = withContext(Dispatchers.IO) {
                    RetrofitFactory.instance.getService(ApiService::class.java).getData(1, 2).dataConvert()
                }
            } catch (e: Exception) {
                XLogUtils.e("请求错误->" + e.message)
            }
        }
    }
}