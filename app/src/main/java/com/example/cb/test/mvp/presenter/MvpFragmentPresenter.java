package com.example.cb.test.mvp.presenter;

import com.example.cb.test.mvp.BasePresenter;
import com.example.cb.test.mvp.view.IMvpFragmentView;
import com.example.cb.test.rx.http.MovieHttpRequest;
import com.example.cb.test.rx.http.NewsResp;
import com.example.cb.test.rx.http.XHttpCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bin on 2018/12/24.
 */
public class MvpFragmentPresenter extends BasePresenter<IMvpFragmentView> {

    public MvpFragmentPresenter(IMvpFragmentView view) {
        super(view);
    }

    public void fetchData() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "top");
        map.put("key", "f323c09a114635eb935ed8dd19f7284e");
        MovieHttpRequest.getInstance().sendNewsRequest(map, new XHttpCallback<NewsResp>() {
            @Override
            public void onSuccess(NewsResp newsResp) {
                mView.onNewsSuccess(newsResp);
            }

            @Override
            public void onError(String error) {
                mView.onNewsError(error);
            }
        });
    }
}