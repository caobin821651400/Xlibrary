package com.example.cb.test.rx;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/17
 * desc   :
 */
public class BaseHttpRequest {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    //服务器路径
//    private static final String API_SERVER = "http://v.juhe.cn/";
    private static final String API_SERVER = "https://mo.sctel.com.cn/scportal_mh/";

    private static CompositeDisposable compositeDisposable = null;//统一管理所有的订阅生命周期

    private static Retrofit mRetrofit;
    private OkHttpConfiguration okHttpConfiguration;

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    protected Retrofit getRetrofit() {
        okHttpConfiguration = new OkHttpConfiguration();
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(okHttpConfiguration.getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())//gson解析
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//加入rxjava
                    .baseUrl(API_SERVER)
                    .build();
        }
        return mRetrofit;
    }

    /**
     * 获取okHttp配置信息
     *
     * @return
     */
    public OkHttpConfiguration getOkHttpConfiguration() {
        if (okHttpConfiguration == null) {
            return new OkHttpConfiguration();
        }
        return okHttpConfiguration;
    }

    /**
     * add  Disposable
     *
     * @param d
     */
    public void addDisposable(Disposable d) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

    /**
     * 移除Disposable(取消订阅避免内存溢出)
     */
    public void removeDisposable() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    /**
     * 处理请求错误提示
     *
     * @param e
     * @return
     */
    public static String handleError(Throwable e) {
        String errorMsg = "";
        Throwable throwable = e;
        //获取最根源的异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        if (e instanceof HttpException) {//HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    errorMsg = "对不起，网络错误。";
                    break;
            }
        } else if (e instanceof UnknownHostException) {
            errorMsg = "对不起，请检查您的网络。";
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "加载失败，请稍后重试。";
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            errorMsg = "对不起，数据格式有误。";
        } else if (e instanceof ConnectException) {
            errorMsg = "对不起，网络错误。";
        } else {//未知错误
            errorMsg = "对不起，程序出现异常。";
        }
        return errorMsg;
    }
}
