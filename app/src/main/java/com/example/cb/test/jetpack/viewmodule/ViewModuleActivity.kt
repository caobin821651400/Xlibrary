package com.example.cb.test.jetpack.viewmodule

import androidx.databinding.DataBindingUtil
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.databinding.ActivityViewModuleBinding

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/4/29 12:20
 * @Desc :
 * ====================================================
 */
class ViewModuleActivity : BaseActivity() {

    lateinit var mBinding: ActivityViewModuleBinding
    
    override fun getLayoutId(): Int {
        return R.layout.activity_view_module
    }

    override fun initUI() {
        setHeaderTitle("ViewModule使用")

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_module)

        val user = User("曹斌", "11111111")
        mBinding.user = user

        mBinding.btn.setOnClickListener {
            Thread {
                for (i in 0..10) {
                    user.name = "曹斌" + i
                    mBinding.user = user
                    Thread.sleep(1000)
                }
            }.start()
        }
    }


    override fun initEvent() {
    }
}
