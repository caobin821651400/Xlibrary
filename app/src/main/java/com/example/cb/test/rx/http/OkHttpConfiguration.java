package com.example.cb.test.rx.http;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/17
 * desc   : 配置OkHttp
 */
public class OkHttpConfiguration {

    private static OkHttpClient mOkHttpClient;
    private static final String OKHTTP_CACHE_PATH = "";
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private ProgressListener mProgressListener;
    private int mRefreshTime = 180; //进度刷新时间(单位ms),避免高频率调用

    public OkHttpConfiguration() {
        mOkHttpClient = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);//链接超时
        builder.writeTimeout(5, TimeUnit.SECONDS);//写入超时
        builder.readTimeout(5, TimeUnit.SECONDS);//读取超时
        if (false)
            builder.addInterceptor(netCacheInterceptor);
        mOkHttpClient = builder.build();
    }

    /**
     * 初始化OkHttp
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void setmProgressListener(ProgressListener listener) {
        this.mProgressListener = listener;
    }

    /**
     * 拦截器
     */
    private Interceptor netCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            HttpUrl httpUrl = request.url()
//                    .newBuilder()
//                    // add common parameter
//                    .addQueryParameter("token", "123")
//                    .addQueryParameter("username", "xiaocai")
//                    .build();
            Request build = request.newBuilder()
                    // add common header
                    .addHeader("User-Agent", "phone/3.0")
                    .method(request.method(), new ProgressRequestBody(mainHandler, request.body(), mProgressListener, mRefreshTime))//进度
                    //.url(httpUrl)
                    .build();
            Response response = chain.proceed(build);
            return response;
        }
    };
}
