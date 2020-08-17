package com.example.cb.test.kotlin.coroutines.net

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.sccl.net.library.response.HttpResponse
import kotlinx.coroutines.launch

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:16
 * @Desc :
 * ====================================================
 */
class NetViewModule2 : ViewModel() {

    //这个类单例
    val dataModule by lazy { MutableLiveData<HttpResponse<ZnsListBean>>() }

    fun getData() {
//        viewModelScope.launch() {
//            XLogUtils.d("当前线程2->${Thread.currentThread().name}")
//            dataModule.value = withContext(Dispatchers.IO) {
//                executeResponse({
//                    RetrofitFactory.instance
//                            .getService(ApiService::class.java)
//                            .getData(1, 2)
//                            .body()?.string() ?: ""
//                }, ZnsListBean::class.java)
//            }
//        }
    }


    /**
     * 发送请求
     */
    private suspend fun <T : Any> request(
            block: suspend () -> String?,
            liveData: MutableLiveData<HttpResponse<T>>,
            isShowLoading: Boolean = false,
            loadingMsg: String = "努力加载中..."
    ) {
        //相当于java的try/catch
        viewModelScope.launch {
            kotlin.runCatching {
                //执行代码块，也就是网络请求的代码
                block()
            }.onSuccess {
//                if ()
//                liveData
            }.onFailure {

            }
        }


//        try {
//            call()?.let { result ->
//                val jsonObject = JSONObject(result)
//                val code = jsonObject.optInt("code")
//                val msg = jsonObject.optString("msg")
//
//                return if (jsonObject.optInt("code") == 1000) {
//                    val dataObject = jsonObject.optJSONObject("data")
//                    HttpResponse.Success(AppGsonObject.fromJson(dataObject?.toString(), tClass))
//                } else {
//                    HttpResponse.Error(code, msg)
//                }
//            } ?: return HttpResponse.Error(8888, "response is null")
//        } catch (e: Exception) {
//            //这里可以处理自己的异常
//            return HttpResponse.Error(8888, "response is null")
//        }
    }
}