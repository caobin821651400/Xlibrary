package com.example.cb.test.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cb.test.R
import com.example.cb.test.databinding.ActivityMvvmBinding

class MvvmActivity : AppCompatActivity() {

    private var mModule: MvvmModule? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_mvvm)

        var binding: ActivityMvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm)
        mModule = MvvmModule(binding)

        mModule!!.mViewDataBinding.user = mModule!!.getUserInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        mModule!!.onDestriy()
    }
}
