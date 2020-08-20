package com.example.cb.test.base

import androidx.lifecycle.Observer
import cn.sccl.net.library.core.BaseViewModule

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/19 22:28
 * @Desc :BaseViewModule Activity
 * ====================================================
 */
abstract class BaseViewModuleFragment<VM : BaseViewModule> : BaseFragment() {

    lateinit var mViewModule: VM

    override fun beforeInitUI() {
        mViewModule = createViewModel()
        
        mViewModule.showDialogLiveData.observe(this, Observer { showDlgWithMsg(it) })
        mViewModule.dismissDialogLiveData.observe(this, Observer { disMissDLG() })
    }

    /**
     * 创建viewModel
     */
    public abstract fun createViewModel(): VM
}