package com.example.cb.test.base

import androidx.lifecycle.Observer
import cn.sccl.http.core.BaseViewModule

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/19 22:28
 * @Desc :BaseViewModule Activity
 * ====================================================
 */
abstract class BaseViewModuleActivity<VM : BaseViewModule> : BaseActivity() {

    lateinit var mViewModule: VM

    override fun beforeInitUI() {
        mViewModule = createViewModel()

        mViewModule.showDialogLiveData.observe(this, Observer { showDlgWithMsg(it) })
        mViewModule.dismissDialogLiveData.observe(this, Observer { disMissDLG() })
    }

    /**
     * 创建viewModel
     */
    abstract fun createViewModel(): VM
}