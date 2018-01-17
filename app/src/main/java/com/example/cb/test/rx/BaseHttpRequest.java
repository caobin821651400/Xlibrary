package com.example.cb.test.rx;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/17
 * desc   :
 */
public class BaseHttpRequest {


    //服务器路径
    private static final String API_SERVER = "http://www.izaodao.com/Api/";

    private static Retrofit mRetrofit;

    public interface IHttpCallback<T> {
        // 网络请求成功
        void onResponseSuccess(T t);

        // 网络请求失败
        void onError(String error);
    }

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    protected static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(OkHttpConfiguration.getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())//gson解析
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//加入rxjava
                    .baseUrl(API_SERVER)
                    .build();
        }
        return mRetrofit;
    }

    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())//io线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
