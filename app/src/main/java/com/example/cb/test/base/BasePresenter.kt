package com.example.cb.test.base

abstract class BasePresenter<V>(view: V) {

    protected var mView: V? = null

    /**
     * 默认的构造方法
     */
    init {
        attachView(view)
    }

    /**
     * p层与view层绑定[的构造方法][BasePresenter]
     *
     * @param view
     */
    private fun attachView(view: V) {
        mView = view
    }


    fun detachView() {
        mView = null
    }
}