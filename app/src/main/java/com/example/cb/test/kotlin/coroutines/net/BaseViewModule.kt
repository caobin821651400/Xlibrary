package com.example.cb.test.kotlin.coroutines.net

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.sccl.net.library.response.network.BaseResponse
import cn.sccl.net.library.response.network.Error
import cn.sccl.net.library.response.network.ExceptionHandle
import cn.sccl.net.library.response.network.NetException
import cn.sccl.xlibrary.utils.XLogUtils
import kotlinx.coroutines.launch

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/18 12:45
 * @Desc :ViewModel基类 可以实现公共方法，或者实现扩展函数
 * ====================================================
 */
open class BaseViewModule : ViewModel() {


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
        error: (NetException) -> Unit = {}
) {
    viewModelScope.launch {
        //相当于java的try/catch
        runCatching {
            //执行代码块，也就是网络请求的代码
            XLogUtils.d("当前线程4->${Thread.currentThread().name}")
            block()
        }.onSuccess {
            XLogUtils.d("当前线程5->${Thread.currentThread().name}")
            if (it.isSuccess()) {
                success(it.getResponseData())
            } else {
                error(NetException(it.getResponseCode(), it.getResponseMsg(), ""))
            }
        }.onFailure {
            XLogUtils.d("当前线程6->${Thread.currentThread().name}")
            error(ExceptionHandle.handleException(it))
        }
    }
}


/**
 * 发送请求不关心服务器的状态码，HttpCode=200
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回泛型{@ T}
 * @param error 失败回调    返回自定义的异常类{@ NetException}
 */
fun <T> BaseViewModule.requestNoCheck(
        block: suspend () -> T,
        success: (T) -> Unit,
        error: (NetException) -> Unit = {}
) {
    viewModelScope.launch {
        //相当于java的try/catch
        runCatching {
            //执行代码块，也就是网络请求的代码
            XLogUtils.d("当前线程4->${Thread.currentThread().name}")
            block()
        }.onSuccess {
            XLogUtils.d("当前线程5->${Thread.currentThread().name}")
            success(it)
        }.onFailure {
            XLogUtils.d("当前线程6->${Thread.currentThread().name}")
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
        error: (NetException) -> Unit = {}
) {
    viewModelScope.launch {
        //相当于java的try/catch
        runCatching {
            //执行代码块，也就是网络请求的代码
            XLogUtils.d("当前线程4->${Thread.currentThread().name}")
            block()
        }.onSuccess {
            XLogUtils.d("当前线程5->${Thread.currentThread().name}")
            if (it.isNotEmpty()) {
                success(it)
            } else {
                error(NetException(Error.UNKNOWN, null))
            }
        }.onFailure {
            XLogUtils.d("当前线程6->${Thread.currentThread().name}")
            error(ExceptionHandle.handleException(it))
        }
    }
}