package cn.sccl.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.net.URL

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/25 20:41
 * @Desc :通过拦截器实现Retrofit切换BaseUrl
 * ====================================================
 */
class BaseUrlInterceptor : Interceptor {

    companion object {

        /**
         * 切换BaseUrl的header
         */
        const val CHANGE_URL_HEADER = "rxhttp_chang_header"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val headers = request.headers

        if (headers.size > 0) {
            for (header in headers) {
                val first = header.first//key
                val second = header.second//value

                //我们自定义的请求头，判断是否有{@ CHANGE_URL_HEADER}请求头来切换BaseUrl
                if (first == CHANGE_URL_HEADER) {
                    val httpUrl = request.url
                    val newBuilder = httpUrl.newBuilder()

//                    System.err.println("first=$first   second=$second ")

                    when {
                        second.startsWith("http") -> newBuilder.scheme("http")
                        second.startsWith("https") -> newBuilder.scheme("https")
                        else -> throw Throwable("$second 不是http或者https的 , 请检查url是否正确")
                    }

                    runCatching {
                        newBuilder.host(URL(second).host)
                    }.onFailure {
                        throw Throwable("$second 不支持的base url , 请检查url是否正确")
                    }

                    request = request.newBuilder()
                            .removeHeader(CHANGE_URL_HEADER)
                            .url(newBuilder.build())
                            .build()
                    break
                }
            }
            return chain.proceed(request)
        }
        return chain.proceed(request)
    }
}