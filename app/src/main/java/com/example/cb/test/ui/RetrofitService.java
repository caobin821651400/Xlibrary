package com.example.cb.test.ui;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/16
 * desc   :
 */
public interface RetrofitService {

    @POST("AppFiftyToneGraph/videoLink")
    Observable<RetrofitEntity> getAllAudio(@Body boolean once_no);
}
