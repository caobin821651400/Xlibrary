package com.example.cb.test.mvp.presenter.view;

import com.example.cb.test.rx.http.NewsResp;

/**
 * Created by bin on 2018/12/24.
 */
public interface INewListView {
    void onRequestSuccess(NewsResp data);

    void onError(String error);
}
