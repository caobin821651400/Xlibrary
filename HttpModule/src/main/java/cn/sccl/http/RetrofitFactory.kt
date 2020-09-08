package cn.sccl.http

import android.content.Context
import retrofit2.Retrofit

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:27
 * @Desc :RetrofitFactory实例
 * ====================================================
 */

//LazyThreadSafetyMode.SYNCHRONIZED:初始化属性时会有双重锁检查，保证该值只在一个线程中计算，并且所有线程会得到相同的值
//LazyThreadSafetyMode.PUBLICATION :多个线程会同时执行，初始化属性的函数会被多次调用，但是只有第一个返回的值被当做委托属性的值。
//LazyThreadSafetyMode.NONE        :没有双重锁检查，不应该用在多线程下。
//不传默认为 mode = LazyThreadSafetyMode.SYNCHRONIZED
val XHttp: RetrofitFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitFactory() }

class RetrofitFactory {

    lateinit var mContext: Context

    /**
     * 普通接口的Retrofit,
     */
    private lateinit var retrofit: Retrofit

    /**
     * 用于下载的 Retrofit
     */
    private lateinit var downLoadRetrofit: Retrofit

    /**
     * 用于上传的 Retrofit
     */
    private lateinit var upLoadRetrofit: Retrofit

    /**
     * Retrofit一般在Application传入
     */
    fun setRetrofit(retrofit: Retrofit) {
        this.retrofit = retrofit
    }

    /**
     * Retrofit一般在Application传入
     */
    fun setDownLoadRetrofit(retrofit: Retrofit) {
        this.downLoadRetrofit = retrofit
    }

    /**
     * Retrofit一般在Application传入
     */
    fun setUploadRetrofit(retrofit: Retrofit) {
        this.upLoadRetrofit = retrofit
    }

    /**
     * 获取具体的Service
     */
    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }

    /**
     * 获取下载具体的Service
     */
    fun <T> getDownLoadService(service: Class<T>): T {
        return downLoadRetrofit.create(service)
    }

    /**
     * 获取上传具体的Service
     */
    fun <T> getUpLoadService(service: Class<T>): T {
        return upLoadRetrofit.create(service)
    }

    /**
     *
     */
    fun init(context: Context) {
        mContext = context
    }
}