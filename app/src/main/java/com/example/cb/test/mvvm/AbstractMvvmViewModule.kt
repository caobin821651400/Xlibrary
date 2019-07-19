package com.example.cb.test.mvvm

import androidx.databinding.ViewDataBinding

abstract class AbstractMvvmViewModule<T : ViewDataBinding>(private var viewDataBinding: T) : BaseViewModule {

    var mViewDataBinding: T = viewDataBinding

    override fun onDestriy() {
        viewDataBinding.unbind()
    }
}