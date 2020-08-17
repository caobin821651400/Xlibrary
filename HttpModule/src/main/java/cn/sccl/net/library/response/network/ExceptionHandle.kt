package cn.sccl.net.library.response.network

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

    /**
     *
     */
    fun handleException(throwable: Throwable?): NetException {
        throwable?.let {
            when (it) {
                is HttpException -> {
                    return NetException(Error.NETWORK_ERROR, throwable)
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    NetException(Error.PARSE_ERROR, throwable)
                }
                is ConnectException -> {
                    return NetException(Error.NETWORK_ERROR, throwable)
                }
                is javax.net.ssl.SSLException -> {
                    return NetException(Error.SSL_ERROR, throwable)
                }
                is java.net.SocketTimeoutException -> {
                    return NetException(Error.TIMEOUT_ERROR, throwable)
                }
                is java.net.UnknownHostException -> {
                    return NetException(Error.TIMEOUT_ERROR, throwable)
                }
                is NetException -> {
                    return it
                }
                else -> {
                    NetException(Error.UNKNOWN, throwable)
                }
            }
        }
        return NetException(Error.UNKNOWN, throwable)
    }
}