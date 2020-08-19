package com.example.cb.test

import android.app.Application
import cn.sccl.xlibrary.XLibrary
import cn.sccl.xlibrary.kotlin.AppGsonObject
import cn.sccl.net.library.log.RxLogInterceptor
import cn.sccl.net.library.XHttp
import com.example.cb.test.utils.SSLUtils
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * Created by cb on 2017/12/1.
 */
@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //        XCrashHandlerUtils.getInstance().init(this);
        XLibrary.init(this)
        XLibrary.logTag = "ME日志"
        XLibrary.isDebug = true

        initHttp()

//        CrashReport.initCrashReport(getApplicationContext(), "c4163937f8", true);
    }

    private fun initHttp() {
        val sslParams = SSLUtils.getSslSocketFactory()
        val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(RxLogInterceptor())
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .build()

        XHttp.setRetrofit(
                Retrofit.Builder()
                        .baseUrl("https://iot.sctel.com.cn/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(AppGsonObject))
                        .build()
        )
    }
}