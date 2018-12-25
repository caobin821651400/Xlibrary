package com.example.cb.test.mvp.view;

import com.example.cb.test.rx.http.NewsResp;

/**
 * Created by bin on 2018/12/25.
 */
public interface IMvpFragmentView {

    void onNewsSuccess(NewsResp newsResp);

    void onNewsError(String error);

}
