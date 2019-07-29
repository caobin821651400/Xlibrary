package com.example.cb.test.mvp.presenter

import com.example.cb.test.base.BasePresenter
import com.example.cb.test.mvp.view.INewListView
import com.example.cb.test.rx.http.MovieHttpRequest
import com.example.cb.test.rx.http.NewsResp
import com.example.cb.test.rx.http.XHttpCallback

/**
 * ====================================================
 * @User :caobin
 * @Date :2019/7/29 10:49
 * @Desc :逻辑处理类
 * ====================================================
 */
class NewListPresenter(view: INewListView) : BasePresenter<INewListView>(view) {

    fun fetchData() {
        val map = HashMap<String, String>()
        map["type"] = "top"
        map["key"] = "f323c09a114635eb935ed8dd19f7284e"
        MovieHttpRequest.getInstance().sendNewsRequest(map, object : XHttpCallback<NewsResp> {
            override fun onSuccess(t: NewsResp?) {
                if (t != null) {
                    mView?.onRequestSuccess(t)
                }
            }

            override fun onError(error: String?) {
                if (error != null) {
                    mView?.onError(error)
                }
            }
        })
    }
}