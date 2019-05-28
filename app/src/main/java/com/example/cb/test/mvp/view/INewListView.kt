package com.example.cb.test.mvp.view

import com.example.cb.test.rx.http.NewsResp

/**
 * Created by bin on 2018/12/24.
 */
interface INewListView {
    fun onRequestSuccess(data: NewsResp)

    fun onError(error: String)
}
