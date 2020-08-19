package cn.sccl.net.library.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/17 22:13
 * @Desc :异常处理
 * ====================================================
 */
object ExceptionHandle {


    fun handleException(throwable: Throwable?): cn.sccl.net.library.exception.HttpException {
        throwable?.let {
            when (it) {
                is HttpException -> {
                    return HttpException(HttpError.NETWORK_ERROR, throwable)
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    HttpException(HttpError.PARSE_ERROR, throwable)
                }
                is ConnectException -> {
                    return HttpException(HttpError.NETWORK_ERROR, throwable)
                }
                is javax.net.ssl.SSLException -> {
                    return HttpException(HttpError.SSL_ERROR, throwable)
                }
                is java.net.SocketTimeoutException -> {
                    return HttpException(HttpError.TIMEOUT_ERROR, throwable)
                }
                is java.net.UnknownHostException -> {
                    return HttpException(HttpError.TIMEOUT_ERROR, throwable)
                }
                is cn.sccl.net.library.exception.HttpException -> {
                    return it
                }
                else -> {
                    HttpException(HttpError.UNKNOWN, throwable)
                }
            }
        }
        return HttpException(HttpError.UNKNOWN, throwable)
    }
}