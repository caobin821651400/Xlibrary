package com.example.cb.test.base

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 *
 */
abstract class BasePresenter<V>(view: V) {
    protected var mView: V? = null
    private var reference: Reference<V>? = null

    /**
     * 默认的构造方法
     */
    init {
        attachView(view)
    }

    /**
     * <p>层与view层绑定[的构造方法][BasePresenter]
     * <p>这里使用的弱引用让Presenter持有Activity</p>
     * <p>防止Presenter中耗时操作导致Activity不能及时回收</p>
     * @param view
     */
    private fun attachView(view: V) {
        reference = WeakReference<V>(view)
        mView = reference!!.get()
    }

    /**
     * 销毁View
     */
    fun detachView() {
        if (reference != null) {
            reference!!.clear()
            reference = null
        }
        mView = null
    }
}