package com.example.cb.test.kotlin.coroutines.net

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.sccl.net.library.response.HttpResponse
import cn.sccl.xlibrary.kotlin.AppGsonObject
import cn.sccl.xlibrary.utils.XLogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:16
 * @Desc :
 * ====================================================
 */
class NetViewModule : ViewModel() {

    //这个类单例
    val dataModule by lazy { MutableLiveData<HttpResponse<ZnsListBean>>() }

    fun getData() {
        viewModelScope.launch() {
            XLogUtils.d("当前线程2->${Thread.currentThread().name}")
            dataModule.value = withContext(Dispatchers.IO) {
                executeResponse({
                    RetrofitFactory.instance
                            .getService(ApiService::class.java)
                            .getData(1, 2)
                            .body()?.string() ?: ""
                }, ZnsListBean::class.java)
            }
        }
    }


    /**
     * 处理请求结果
     */
    private suspend fun <T : Any> executeResponse(
            call: suspend () -> String?,
            tClass: Class<T>
    ): HttpResponse<T> {
        try {
            call()?.let { result ->
                val jsonObject = JSONObject(result)
                val code = jsonObject.optInt("code")
                val msg = jsonObject.optString("msg")

                return if (jsonObject.optInt("code") == 1000) {
                    val dataObject = jsonObject.optJSONObject("data")
                    HttpResponse.Success(AppGsonObject.fromJson(dataObject?.toString(), tClass))
                } else {
                    HttpResponse.Error(code, msg)
                }
            } ?: return HttpResponse.Error(8888, "response is null")
        } catch (e: Exception) {
            //这里可以处理自己的异常
            return HttpResponse.Error(8888, "response is null")
        }
    }
}