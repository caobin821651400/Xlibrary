package cn.sccl.net.library.response.network

import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/18 10:55
 * @Desc :OkHttp Retrofit 配置基类
 * ====================================================
 */
abstract class BaseNetApi {

    /**
     *实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    abstract fun setOkHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder


    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，Protocol
     */
    abstract fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder

    /**
     * 配置OkHttp
     */
    private val okHttpClient: OkHttpClient
        get() {
            var builder = RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
            builder = setOkHttpClientBuilder(builder)
            return builder.build()
        }

}