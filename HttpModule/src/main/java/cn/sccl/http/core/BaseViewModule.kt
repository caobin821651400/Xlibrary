package cn.sccl.http.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.sccl.http.event.EventLiveData
import cn.sccl.http.exception.ExceptionHandle
import cn.sccl.http.exception.NetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/18 12:45
 * @Desc :ViewModel基类 可以实现公共方法，或者实现扩展函数
 * ====================================================
 */
open class BaseViewModule : ViewModel() {

    val defaultLoadingMsg = "努力加载中..."

    /**
     * 显示dialog
     */
    val showDialogLiveData by lazy { EventLiveData<String>() }

    /**
     * 隐藏dialog
     */
    val dismissDialogLiveData by lazy { EventLiveData<String>() }

}

/**
 * 发送请求并过滤服务器code，只取成功的data数据,失败提示服务器errorMsg
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回服务器data对象,也就是泛型{@ T}
 * @param error 失败回调    返回自定义的异常类{@ NetException}
 */
inline fun <T> BaseViewModule.request(
        crossinline block: suspend () -> BaseResponse<T>,
        crossinline success: (T) -> Unit,
        crossinline error: (NetException) -> Unit = {},
        isShowDialog: Boolean = false,
        loadingMsg: String = defaultLoadingMsg
) {
    viewModelScope.launch {
        //相当于java的try/catch
        runCatching {
            //执行代码块，也就是网络请求的代码
            if (isShowDialog) showDialogLiveData.value = loadingMsg
            withContext(Dispatchers.IO) { block() }
        }.onSuccess {
            if (isShowDialog) dismissDialogLiveData.value = ""
            if (it.isSuccess()) {
                success(it.getResponseData())
            } else {
                error(NetException(it.getResponseCode(), it.getResponseMsg(), ""))
            }
        }.onFailure {
            if (isShowDialog) dismissDialogLiveData.value = ""
            error(ExceptionHandle.handleException(it))
        }
    }
}


/**
 * 发送请求不关心服务器的状态码，
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回泛型{@ T}
 * @param error 失败回调    返回自定义的异常类{@ NetException}
 */
inline fun <T> BaseViewModule.requestNoCheck(
        crossinline block: suspend () -> T,
        crossinline success: (T) -> Unit,
        crossinline error: (NetException) -> Unit = {},
        isShowDialog: Boolean = false,
        loadingMsg: String = defaultLoadingMsg
) {
    viewModelScope.launch {
        //相当于java的try/catch
        runCatching {
            //执行代码块，也就是网络请求的代码
            if (isShowDialog) showDialogLiveData.value = loadingMsg
            withContext(Dispatchers.IO) { block() }
        }.onSuccess {
            if (isShowDialog) dismissDialogLiveData.value = ""
            success(it)
        }.onFailure {
            if (isShowDialog) dismissDialogLiveData.value = ""
            error(ExceptionHandle.handleException(it))
        }
    }
}


/**
 * 发送请求接收String,解析完全交给调用者自己处理，这里不做json解析
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回泛型{@ T}
 * @param error 失败回调    返回自定义的异常类{@ NetException}
 */
inline fun BaseViewModule.requestString(
        crossinline block: suspend () -> String,
        crossinline success: (String) -> Unit,
        crossinline error: (NetException) -> Unit = {},
        isShowDialog: Boolean = false,
        loadingMsg: String = defaultLoadingMsg
) {
    viewModelScope.launch {
        //相当于java的try/catch
        runCatching {
            //执行代码块，也就是网络请求的代码;
            if (isShowDialog) showDialogLiveData.value = loadingMsg
            withContext(Dispatchers.IO) { block() }
        }.onSuccess {
            if (isShowDialog) dismissDialogLiveData.value = ""
            if (it.isNotEmpty()) {
                success(it)
            } else {
                error(NetException(NetException.UNKNOWN, null))
            }
        }.onFailure {
            if (isShowDialog) dismissDialogLiveData.value = ""
            error(ExceptionHandle.handleException(it))
        }
    }
}

//如果你写的是高阶函数，会有函数类型的参数，加上 inline 就对了。
//crossinline  只会跳出当前lambda中的return对整个流程没有影响，只会跳出当前lambda;
//函数类型的参数，它本质上是个对象
//inline 会让函数类型的参数的返回值失效   block: suspend () -> String, --->  也就是return block失效