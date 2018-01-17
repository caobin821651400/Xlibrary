package com.example.cb.test.ui;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/16
 * desc   :
 */
public class RetrofitServiceManager {
    private static RetrofitServiceManager mInstance = null;
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    public static final String BASE_URL = "http://www.izaodao.com/Api/";
    private Retrofit mRetrofit;

    public RetrofitServiceManager() {
        //创建OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);

//        // 添加公共参数拦截器
//        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("paltform", "android")
//                .addHeaderParams("userToken", "1234343434dfdfd3434")
//                .addHeaderParams("userId", "123445")
//                .build();
//        builder.addInterceptor(commonInterceptor);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    /**
     * 获得实例的唯一全局访问点
     *
     * @return
     */
    public static RetrofitServiceManager getInstance() {
        if (mInstance == null) {
            // 增加类锁,保证只初始化一次
            synchronized (RetrofitServiceManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitServiceManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}
