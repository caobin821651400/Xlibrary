package com.example.cb.test.mvvm

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.cb.test.mvvm.viewmodule.MvvmBaseViewModule
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


/**
 * ====================================================
 * @User :caobin
 * @Date :2020/4/19 17:37
 * @Desc :MVVM基类
 * ====================================================
 */
open abstract class MvvmBaseActivity<V : ViewDataBinding, VM : MvvmBaseViewModule?> : AppCompatActivity() {

    protected var mViewModule: VM? = null
    protected var mViewBinding: V? = null
    private var mLoadService: LoadService<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
//        initLoadSir()
    }

    /**
     * 绑定XML
     */
    private fun initDataBinding() {
        mViewBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModule = getViewModule()
        if (getBindingVariable() > 0) {
            mViewBinding?.setVariable(getBindingVariable(), mViewModule)
        }
        mViewBinding?.executePendingBindings()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun getBindingVariable(): Int

    protected abstract fun getViewModule(): VM

//    protected abstract fun onRetry()
//
//    public fun initLoadSir() {
//        val loadService = LoadSir.getDefault().register(this) {
//            // 重新加载逻辑
//            onRetry()
//        }
//    }
}