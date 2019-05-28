package com.example.cb.test.mvp.view

import com.example.cb.test.rx.http.NewsResp

/**
 * Created by bin on 2018/12/25.
 */
interface IMvpFragmentView {

    fun onNewsSuccess(newsResp: NewsResp)

    fun onNewsError(error: String)

}
