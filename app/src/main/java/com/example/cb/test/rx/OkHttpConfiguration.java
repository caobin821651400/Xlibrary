package com.example.cb.test.rx;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
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

    public OkHttpConfiguration() {
    }

    /**
     * 初始化OkHttp
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(5, TimeUnit.SECONDS);//链接超时
            builder.writeTimeout(5, TimeUnit.SECONDS);//写入超时
            builder.readTimeout(5, TimeUnit.SECONDS);//读取超时
            if (false)
                builder.addInterceptor(netCacheInterceptor);
            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }

    /**
     * 拦截器
     */
    private static Interceptor netCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl httpUrl = request.url()
                    .newBuilder()
                    // add common parameter
                    .addQueryParameter("token", "123")
                    .addQueryParameter("username", "xiaocai")
                    .build();
            Request build = request.newBuilder()
                    // add common header
                    .addHeader("contentType", "text/json")
                    .url(httpUrl)
                    .build();
            Response response = chain.proceed(build);
            return response;
        }
    };
}
