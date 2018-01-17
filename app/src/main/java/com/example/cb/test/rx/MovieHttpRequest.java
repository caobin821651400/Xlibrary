package com.example.cb.test.rx;

import com.example.cb.test.ui.RetrofitEntity;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Observer;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/17
 * desc   :
 */
public class MovieHttpRequest extends BaseHttpRequest {

    protected static final MovieService movieService = getRetrofit().create(MovieService.class);


    public interface MovieService {

        @FormUrlEncoded
        @POST("AppFiftyToneGraph/videoLink")
        Observable<RetrofitEntity> getAllAudio(@Field("name") String name);

        //POST请求
        @FormUrlEncoded
        @POST("AppFiftyToneGraph/videoLink")
        Observable<RetrofitEntity> getAllAudioWithMap(@FieldMap Map<String, String> map);
    }


    //POST请求
    public static void sendPostRequest(String s,final IHttpCallback iHttpCallback) {
        setSubscribe(movieService.getAllAudio(s), new Observer<RetrofitEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RetrofitEntity retrofitEntity) {
                if (retrofitEntity.getMsg() != null) {
                    iHttpCallback.onResponseSuccess(retrofitEntity);
                }else{

                }
            }
        });
    }

    //POST请求参数以map传入
    public static void sendPostRequestByMap(Map<String, String> map, Observer<RetrofitEntity> observer) {
        setSubscribe(movieService.getAllAudioWithMap(map), observer);
    }
}
