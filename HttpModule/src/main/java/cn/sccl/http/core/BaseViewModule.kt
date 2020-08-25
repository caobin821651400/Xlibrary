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
fun <T> BaseViewModule.request(
        block: suspend () -> BaseResponse<T>,
        success: (T) -> Unit,
        error: (NetException) -> Unit = {},
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
 * 发送请求不关心服务器的状态码，解析完全交给调用者自己处理，这里不做json解析
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回泛型{@ T}
 * @param error 失败回调    返回自定义的异常类{@ NetException}
 */
fun <T> BaseViewModule.requestNoCheck(
        block: suspend () -> T,
        success: (T) -> Unit,
        error: (NetException) -> Unit = {},
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
fun BaseViewModule.requestString(
        block: suspend () -> String,
        success: (String) -> Unit,
        error: (NetException) -> Unit = {},
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