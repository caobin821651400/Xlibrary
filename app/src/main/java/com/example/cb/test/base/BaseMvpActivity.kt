package com.example.cb.test.base

import android.os.Bundle

abstract class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity() {

    protected var mPresenter: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        setContentView(getContentViewId())
        initView()
        initData()
        initListener()
    }

    /**
     * 子类返回presenter
     *
     * @return
     */
    protected abstract fun createPresenter(): T

    protected abstract fun getContentViewId(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    open fun initListener() {}

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}