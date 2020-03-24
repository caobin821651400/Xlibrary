package com.example.cb.test.rx.http;

import com.example.cb.test.bean.UploadBean;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/17
 * desc   :
 */
public class MovieHttpRequest extends BaseHttpRequest {
    private static MovieHttpRequest instance;

    public static MovieHttpRequest getInstance() {
        if (instance == null) {
            synchronized (MovieHttpRequest.class) {
                if (instance == null) {
                    instance = new MovieHttpRequest();
                }
            }
        }
        return instance;
    }

    private MovieHttpRequest() {
    }

    private ApiService apiService = getRetrofit().create(ApiService.class);

    public interface ApiService {

        @FormUrlEncoded
        @POST("toutiao/index")
        Observable<NewsResp> getNews(@FieldMap Map<String, String> map);
        //f323c09a114635eb935ed8dd19f7284e

        /**
         * 注意1：必须使用{@code @POST}注解为post请求<br>
         * 注意2：使用{@code @Body}注解参数，则不能使用{@code @Multipart}注解方法了<br>
         * 直接将所有的{@link MultipartBody.Part}合并到一个{@link MultipartBody}中
         */
        @Multipart
        @POST("xxx")
        Observable<UploadBean> uploadImg(@PartMap Map<String, RequestBody> map, @Part MultipartBody.Part part);
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
                        xHttpCallback.onError(handleError(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 上传图片
     *
     * @param file
     * @param map
     * @param xHttpCallback
     */
    public void uploadImage(File file, Map<String, RequestBody> map, final XHttpCallback<UploadBean> xHttpCallback, ProgressListener listener) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("multipartFiles", file.getName(), requestBody);

        Observable<UploadBean> observable = apiService.uploadImg(map, part);
        getOkHttpConfiguration().setmProgressListener(listener);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(UploadBean uploadBean) {
//                        System.err.println("哈哈  " + new Gson().toJson(uploadBean));
                        if (uploadBean.getCode().equals("0")) {
                            xHttpCallback.onSuccess(uploadBean);
                        } else {
                            xHttpCallback.onError(uploadBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        xHttpCallback.onError(handleError(e));
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
