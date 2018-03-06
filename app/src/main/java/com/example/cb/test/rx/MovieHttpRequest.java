package com.example.cb.test.rx;

import com.cb.xlibrary.utils.log.XLog;
import com.google.gson.Gson;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/17
 * desc   :
 */
public class MovieHttpRequest extends BaseHttpRequest {

    private static class HttpRequestHolder {
        public static MovieHttpRequest instance = new MovieHttpRequest();
    }

    public MovieHttpRequest() {
    }

    public static MovieHttpRequest getInstance() {
        return HttpRequestHolder.instance;
    }

    private static final ApiService apiService = getRetrofit().create(ApiService.class);

    public interface ApiService {

        @FormUrlEncoded
        @POST("toutiao/index")
        Observable<NewsResp> getNews(@FieldMap Map<String, String> map);
        //f323c09a114635eb935ed8dd19f7284e

        @FormUrlEncoded
        @POST("userInfor/queryUserInfor")
        Observable<UserInfoResp> getUserInfo(@FieldMap Map<String, String> map);
    }

    /**
     * 获取新闻信息
     *
     * @param map
     * @param xHttpCallback
     */
    public void sendNewsRequest(Map<String, String> map, final XHttpCallback<NewsResp> xHttpCallback) {
        Observable<NewsResp> observable = apiService.getNews(map);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResp>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(NewsResp value) {
                        xHttpCallback.onSuccess(value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getUesrInfo(Map<String, String> map, final XHttpCallback<UserInfoResp> xHttpCallback) {
        Observable<UserInfoResp> observable = apiService.getUserInfo(map);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoResp>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(UserInfoResp value) {
                        System.err.println("请求结果--》" + new Gson().toJson(value));
                        if (value.getCode().equals("200")) {
                            xHttpCallback.onSuccess(value);
                        } else {
                            xHttpCallback.onError(value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 接触订阅,在activity退出时调用，防止发生内存泄漏
     */
    public void dispose() {
        removeDisposable();
    }
}
