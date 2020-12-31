package com.example.cb.test

import android.app.Application
import cn.sccl.http.XHttp
import cn.sccl.http.interceptor.BaseUrlInterceptor
import cn.sccl.http.interceptor.HttpLoggingInterceptor
import cn.sccl.xlibrary.XLibrary
import cn.sccl.xlibrary.kotlin.AppGsonObject
import com.example.cb.test.utils.SSLUtils
import com.example.cb.test.weight.loadCallBack.EmptyCallback
import com.example.cb.test.weight.loadCallBack.ErrorCallback
import com.example.cb.test.weight.loadCallBack.LoadingCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import javax.net.ssl.HostnameVerifier

/**
 * Created by cb on 2017/12/1.
 */
class MyApplication : Application() {

    companion object {
        lateinit var app: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        XLibrary.init(this)
        XLibrary.logTag = "ME日志"
        XLibrary.isDebug = true

        initHttp()
        initLoadSir()
    }

    private fun initHttp() {
        XHttp.init(this)
        val sslParams = SSLUtils.getSslSocketFactory()
        //log相关
        val loggingInterceptor = HttpLoggingInterceptor("XHttp")
        loggingInterceptor.setPrintLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE) //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO) //log颜色级别，决定了log在控制台显示的颜色

        val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(BaseUrlInterceptor())
//                .addInterceptor(RxLogInterceptor())
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .build()
        XHttp.setRetrofit(Retrofit.Builder()
//                .baseUrl("https://iot.sctel.com.cn/")
                .baseUrl("https://www.wanandroid.com/")
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