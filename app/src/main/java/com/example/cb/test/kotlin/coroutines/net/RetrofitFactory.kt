package com.example.cb.test.kotlin.coroutines.net

import cn.sccl.xlibrary.kotlin.AppGsonObject
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.utils.SSLUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:27
 * @Desc :
 * ====================================================
 */
class RetrofitFactory private constructor() {

    /**
     * Retrofit
     */
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://iot.sctel.com.cn/")
            .client(createOkHttp())
            .addConverterFactory(GsonConverterFactory.create(AppGsonObject))
            .build()


    companion object {
        //LazyThreadSafetyMode.SYNCHRONIZED:初始化属性时会有双重锁检查，保证该值只在一个线程中计算，并且所有线程会得到相同的值
        //LazyThreadSafetyMode.PUBLICATION :多个线程会同时执行，初始化属性的函数会被多次调用，但是只有第一个返回的值被当做委托属性的值。
        //LazyThreadSafetyMode.NONE        :没有双重锁检查，不应该用在多线程下。
        //不传默认为 mode = LazyThreadSafetyMode.SYNCHRONIZED
        val instance: RetrofitFactory by lazy() {
            RetrofitFactory()
        }
    }


    /**
     *  创建OkHttpClient
     */
    private fun createOkHttp(): OkHttpClient {
        val sslParams = SSLUtils.getSslSocketFactory()
        return OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(RxLogInterceptor())
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .build()
    }

    /**
     * 获取具体的Service
     */
    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }
}