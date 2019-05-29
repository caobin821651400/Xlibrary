package com.example.cb.test.base

import android.view.View


abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment() {

    protected var mPresenter: T? = null

    override fun getRootViewId(): Int {
        return getContentViewId()
    }

    override fun initUI(v: View) {
        mPresenter = createPresenter()
        initView(v)
        initData()
        initListener()
    }

    protected abstract fun createPresenter(): T

    protected abstract fun getContentViewId(): Int

    abstract fun initData()

    abstract fun initListener()

    abstract fun initView(v: View)

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}