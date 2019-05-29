package com.example.cb.test.mvp.presenter

import com.example.cb.test.base.BasePresenter
import com.example.cb.test.mvp.view.IMvpFragmentView
import com.example.cb.test.rx.http.MovieHttpRequest
import com.example.cb.test.rx.http.NewsResp
import com.example.cb.test.rx.http.XHttpCallback

class MvpFragmentPresenter(view: IMvpFragmentView) : BasePresenter<IMvpFragmentView>(view) {

    fun fetchData() {
        val map = HashMap<String, String>()
        map["type"] = "top"
        map["key"] = "f323c09a114635eb935ed8dd19f7284e"
        MovieHttpRequest.getInstance().sendNewsRequest(map, object : XHttpCallback<NewsResp> {
            override fun onSuccess(newBean: NewsResp?) {
                if (newBean != null) {
                    mView!!.onNewsSuccess(newBean)
                }
            }

            override fun onError(error: String?) {
                if (error != null) {
                    mView!!.onNewsError(error)
                }
            }
        })
    }
}