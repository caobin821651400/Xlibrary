package cn.sccl.net.library.response.state

import androidx.lifecycle.MutableLiveData
import cn.sccl.net.library.response.network.BaseResponse
import cn.sccl.net.library.response.network.ExceptionHandle
import cn.sccl.net.library.response.network.NetException

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/17 22:09
 * @Desc :
 * ====================================================
 */
sealed class HttpResponseState<out T> {

    companion object {
        fun <T> onSuccess(data: T): HttpResponseState<T> = Success(data)
        fun <T> onError(error: NetException): HttpResponseState<T> = Error(error)
        fun <T> onLoading(msg: String): HttpResponseState<T> = Loading(msg)
    }

    data class Success<out T>(val data: T) : HttpResponseState<T>()
    data class Error(val error: NetException) : HttpResponseState<Nothing>()
    data class Loading(val msg: String) : HttpResponseState<Nothing>()
}


/**
 * 判断后台的状态码 只需要data
 */
fun <T> MutableLiveData<HttpResponseState<T>>.parseResult(response: BaseResponse<T>) {
    value = if (response.isSuccess()) {
        HttpResponseState.onSuccess(response.getResponseData())
    } else {
        HttpResponseState.onError(NetException(response.getResponseCode(), response.getResponseMsg()))
    }
}


/**
 * 不管后台返回的状态码 将整个response结果返回
 */
fun <T> MutableLiveData<HttpResponseState<T>>.parseResult(response: T) {
    value = HttpResponseState.onSuccess(response)
}

/**
 * 异常处理
 */
fun <T> MutableLiveData<HttpResponseState<T>>.parseException(e: Throwable) {
    value = HttpResponseState.onError(ExceptionHandle.handleException(e))
}