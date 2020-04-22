package com.example.cb.test.mvvm

import com.example.cb.test.R
import com.example.cb.test.databinding.ActivityMvvmBinding
import com.example.cb.test.mvvm.viewmodule.MvvmBaseViewModule

/**
 * Mvvm练习
 * @author bin
 */
class MvvmActivity : MvvmBaseActivity<ActivityMvvmBinding, MvvmBaseViewModule>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_mvvm
    }

    override fun getBindingVariable(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getViewModule(): MvvmBaseViewModule {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
