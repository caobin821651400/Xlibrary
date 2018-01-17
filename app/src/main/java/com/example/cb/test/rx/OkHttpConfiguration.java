package com.example.cb.test.rx;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)//链接超时
                    .writeTimeout(5, TimeUnit.SECONDS)//写入超时
                    .readTimeout(5, TimeUnit.SECONDS)//读取超时
                    //.cache("")//缓存路径
                    .build();
        }
         return mOkHttpClient;
    }
}
