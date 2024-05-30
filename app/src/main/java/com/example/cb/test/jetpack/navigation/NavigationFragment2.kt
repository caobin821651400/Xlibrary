package com.example.cb.test.jetpack.navigation

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.Navigation
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseFragment

class NavigationFragment2 : BaseFragment() {

    private val btn1 by lazyNone { requireView().findViewById<Button>(R.id.btn1) }
    private val btn2 by lazyNone { requireView().findViewById<Button>(R.id.btn2) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        XLogUtils.d("NavigationFragment2 onCreate")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation2
    }

    override fun initUI(v: View?) {
    }

    override fun initEvent(view: View?) {
        //跳转到fragment1
        btn1.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragment2_to_fragment1)
        }

        //跳转到fragment3
        btn2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragment2_to_fragment3)
        }
    }


}