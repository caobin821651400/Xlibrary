package com.example.cb.test.rx;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/17
 * desc   :
 */
public class MovieHttpRequest extends BaseHttpRequest {

    private static final ApiService apiService = getRetrofit().create(ApiService.class);
    private static CompositeSubscription compositeSubscription = new CompositeSubscription();//管理所有的订阅

    public interface ApiService {

        @FormUrlEncoded
        @POST("toutiao/index")
        Observable<NewsResp> getNews(@FieldMap Map<String, String> map);
        //f323c09a114635eb935ed8dd19f7284e
    }


    public static void sendNewsRequest(Map<String, String> map, final XHttpCallback<NewsResp> xHttpCallback) {
        Observable<NewsResp> observable = apiService.getNews(map);
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsResp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        xHttpCallback.onError(handleError(e));
                    }

                    @Override
                    public void onNext(NewsResp newsResp) {
                        if (newsResp.getError_code() == 0) {
                            xHttpCallback.onSuccess(newsResp);
                        } else {
                            xHttpCallback.onError(newsResp.getReason());
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    /**
     * 获取订阅管理对象
     *
     * @return
     */
    public static CompositeSubscription getCompositeSubscription() {
        return compositeSubscription;
    }
}
