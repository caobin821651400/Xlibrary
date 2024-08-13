package cn.sccl.http.core

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.Serializable
import java.lang.reflect.Type

val mGson = Gson()

/**
 * 发送请求,并缓存在本地,当http code=304时，取本地缓存中的数据
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回服务器data对象,也就是泛型{@ T}
 * @param error 失败回调    返回自定义的异常类{@ NetException}
 */
inline fun <reified T> CoroutineScope.requestCache(
    crossinline block: suspend () -> ResponseBody,
    crossinline getCacheData: suspend () -> String?,
    crossinline setCacheData: suspend (str: String) -> Unit = { },
    crossinline success: (T?) -> Unit,
    crossinline error: (code: Int, errorMsg: String?) -> Unit = { _, _ -> },
): Job {
    return launch {
        try {
            val result = withContext(Dispatchers.IO) { block().string() }
            val response = parseCacheData<T>(result)

            //成功就缓存起来
            if (response?.isSuccess() == true) {
                withContext(Dispatchers.IO) { setCacheData(result) }
            }
            requestDataCallback(response, success, error)
        } catch (e: Exception) {
            //处理304缓存
            if (e.message?.contains("304") == true) {
                try {
                    val cacheStrData = withContext(Dispatchers.IO) { getCacheData() }
                    requestDataCallback(parseCacheData<T>(cacheStrData), success, error)
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        error(-1, e.message)
                    }
                }
            } else {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    error(-1, e.message)
                }
            }
        }
    }
}

/**
 * 回调
 * @param response ApiCodeResponse<T>?
 * @param success Function1<T?, Unit>
 * @param error Function2<[@kotlin.ParameterName] Int, [@kotlin.ParameterName] String?, Unit>
 */
suspend inline fun <T> requestDataCallback(
    response: ApiCodeResponse<T>?,
    crossinline success: (T?) -> Unit,
    crossinline error: (code: Int, errorMsg: String?) -> Unit = { _, _ -> },
) {
    //切换回主线程
    withContext(Dispatchers.Main) {
        if (response?.isSuccess() == true) {
            success(response.data)
        } else {
            error(
                response?.code ?: -1,
                response?.getResponseMsg() ?: "网络异常"
            )
        }
    }
}

/**
 * 解析数据
 * @param str String?
 * @return ApiCodeResponse<T>?
 */
inline fun <reified T> parseCacheData(str: String?): ApiCodeResponse<T>? {
    var tempResponse: ApiCodeResponse<T>? = null
    if (!str.isNullOrEmpty()) {
        val listType: Type = object : TypeToken<ApiCodeResponse<T>>() {}.type
        tempResponse = mGson.fromJson(str, listType)
    }
    return tempResponse
}

/**
 * 发送请求并过滤服务器code，只取成功的data不为空的数据,失败提示服务器errorMsg
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回服务器data对象,也就是泛型{@ T}
 * @param error 失败回调    返回自定义的异常类{@ NetException}
 */
inline fun <T> CoroutineScope.requestDataNoNull(
    crossinline block: suspend () -> BaseResponse<T>,
    crossinline success: (T) -> Unit,
    crossinline error: (code: Int, errorMsg: String?) -> Unit = { _, _ -> }
): Job {
    return launch {
        try {
            val response = withContext(Dispatchers.IO) { block() }
            withContext(Dispatchers.Main) {
                if (response.isSuccess()) {
                    if (response.getResponseData() == null) {
                        error(response.getResponseCode(), "data is null")
                    } else {
                        success(response.getResponseData()!!)
                    }
                } else {
                    error(response.getResponseCode(), response.getResponseMsg())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                error(-1, e.message)
            }
        }
    }
}


/**
 * 发送请求并过滤服务器code，忽略data为null的情况，只关心code=0就认为请求成功
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回服务器data对象,也就是泛型{@ T}
 * @param error 失败回调    返回自定义的异常类{@ NetException}
 */
inline fun <T> CoroutineScope.requestMayNull(
    crossinline block: suspend () -> BaseResponse<T?>,
    crossinline success: (T?) -> Unit,
    crossinline error: (code: Int, errorMsg: String?) -> Unit = { _, _ -> }
): Job {
    return launch {
        try {
            val response = withContext(Dispatchers.IO) { block() }
            withContext(Dispatchers.Main) {
                if (response.isSuccess()) {
                    success(response.getResponseData())
                } else {
                    error(response.getResponseCode(), response.getResponseMsg())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                error(-1, e.message)
            }
        }
    }
}


/**
 * 发送请求,返回string,自行解析,
 *
 * 注意：api中的返回类型需要使用->ResponseBody 如：suspend fun test(): ResponseBody
 * 注意：api中的返回类型需要使用->ResponseBody 如：suspend fun test(): ResponseBody
 * 注意：api中的返回类型需要使用->ResponseBody 如：suspend fun test(): ResponseBody
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回服务器data对象,也就是泛型{@ T}
 * @param error 失败回调    返回自定义的异常类{@ NetException}
 */
inline fun CoroutineScope.requestString(
    crossinline block: suspend () -> ResponseBody,
    crossinline success: (String?) -> Unit,
    crossinline error: (code: Int, errorMsg: String?) -> Unit = { _, _ -> },
): Job {
    return launch {
        try {
            val result = withContext(Dispatchers.IO) {
                val responseBody = block()
                responseBody.string()
            }
            withContext(Dispatchers.Main) { success(result) }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                error(-1, e.message)
            }
        }
    }
}

/**
 * 发送请求并过滤服务器code，只取成功的data不为空的数据,失败提示服务器errorMsg
 *
 * @param block 网络请求的方法块
 * @param success 成功回调  返回服务器data对象,也就是泛���{@ T}
 * @param error 失败回调
 */
inline fun <T> CoroutineScope.request33(
    crossinline block: suspend () -> Response<ApiCodeResponse<T>>,//这里变了
    crossinline success: (T) -> Unit,
    crossinline error: (code: Int, errorMsg: String?) -> Unit = { _, _ -> }
): Job {
    return launch {
        try {
            //切换到IO线程执行网络请求
            val response = withContext(Dispatchers.IO) { block() }

            //下面代码变了
            withContext(Dispatchers.Main) {
                //判断Http的code
                if (response.isSuccessful && response.code() == 200) {
                    val data = response.body()
                    //判断服务器状态码和data不能为空,切换主线程回调回去
                    if (data?.isSuccess() == true && data.data != null) {
                        success(data.data!!)
                    }
                } else {
                    error(response.code(), "data is null")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //异常情况，需要单独处理，一般需要再主线程中处理
            withContext(Dispatchers.Main) {
                error(-1, e.message)
            }
        }
    }
}

/**
 * post请求
 * @receiver Parcelable
 * @return RequestBody
 */
fun Parcelable.toBody(): RequestBody {
    return Gson().toJson(this).toBody()
}

/**
 * post请求
 * @receiver Serializable
 * @return RequestBody
 */
fun Serializable.toBody(): RequestBody {
    return Gson().toJson(this).toBody()
}

/**
 * post请求
 * @receiver String
 * @return RequestBody
 */
fun String.toBody(): RequestBody {
    return RequestBody.create(
        "application/json".toMediaTypeOrNull(), this
    )
}

///**
// * 处理403
// * @param handler Function2<CoroutineContext, Throwable, Unit>
// * @return CoroutineExceptionHandler
// */
//inline fun NoSupportChinaException(crossinline handler: (CoroutineContext, Throwable) -> Unit): CoroutineExceptionHandler =
//    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
//        override fun handleException(context: CoroutineContext, exception: Throwable) {
//            handler.invoke(context, exception)
//        }
//    }