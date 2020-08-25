package cn.sccl.http.exception

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


    fun handleException(throwable: Throwable?): NetException {
        throwable?.let {
            when (it) {
                is HttpException -> {
                    return NetException(NetException.UNKNOWN, errLog = throwable.message)
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    return NetException(NetException.PARSE_ERROR, errLog = throwable.message)
                }
                is ConnectException -> {
                    return NetException(NetException.NETWORK_ERROR, errLog = throwable?.message)
                }
                is javax.net.ssl.SSLException -> {
                    return NetException(NetException.SSL_ERROR, errLog = throwable.message)
                }
                is java.net.SocketTimeoutException -> {
                    return NetException(NetException.TIMEOUT_ERROR, errLog = throwable.message)
                }
                is java.net.UnknownHostException -> {
                    return NetException(NetException.TIMEOUT_ERROR, errLog = throwable.message)
                }
                is NetException -> {
                    return it
                }
                else -> {
                    return NetException(NetException.UNKNOWN, errLog = throwable.message)
                }
            }
        }
        return NetException(NetException.UNKNOWN, errLog = throwable?.message
                ?: "")
    }
}