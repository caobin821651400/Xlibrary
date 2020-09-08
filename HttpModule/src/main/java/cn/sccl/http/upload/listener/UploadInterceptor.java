package cn.sccl.http.upload.listener;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UploadInterceptor implements Interceptor {
    private UploadProgressListener listener;

    public UploadInterceptor(UploadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request build = request.newBuilder()
                .method(request.method(), new ProgressRequestBody(new Handler(Looper.getMainLooper()), request.body(),
                        listener, 180))//进度
                .build();
        Response response = chain.proceed(build);
        return response;
    }
}
