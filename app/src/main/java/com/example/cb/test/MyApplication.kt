package com.example.cb.test

import android.app.Application
import cn.sccl.http.XHttp
import cn.sccl.http.interceptor.BaseUrlInterceptor
import cn.sccl.http.log.RxLogInterceptor
import cn.sccl.xlibrary.XLibrary
import cn.sccl.xlibrary.kotlin.AppGsonObject
import com.example.cb.test.utils.SSLUtils
import com.example.cb.test.weight.loadCallBack.EmptyCallback
import com.example.cb.test.weight.loadCallBack.ErrorCallback
import com.example.cb.test.weight.loadCallBack.LoadingCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
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
        initLoadSir()

//        CrashReport.initCrashReport(getApplicationContext(), "c4163937f8", true);
    }

    private fun initHttp() {
        val sslParams = SSLUtils.getSslSocketFactory()
        val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(BaseUrlInterceptor())
                .addInterceptor(RxLogInterceptor())
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .build()

        XHttp.setRetrofit(
                Retrofit.Builder()
                        .baseUrl("https://iot.sctel.com.cn/")
//                        .baseUrl("https://www.wanandroid.com/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(AppGsonObject))
                        .build()
        )
    }

    private fun initLoadSir() {
        //界面加载管理 初始化
        LoadSir.beginBuilder()
                .addCallback(LoadingCallback())//加载
                .addCallback(ErrorCallback())//错误
                .addCallback(EmptyCallback())//空
                .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
                .commit()
    }
}