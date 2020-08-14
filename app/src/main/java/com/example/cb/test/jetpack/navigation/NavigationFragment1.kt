package com.example.cb.test.jetpack.navigation

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_navigation1.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/5/8 22:13
 * @Desc :
 * ====================================================
 */
class NavigationFragment1 : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        XLogUtils.d("NavigationFragment1 onCreate")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation1
    }

    override fun initUI(v: View?) {
    }

    override fun initEvent(view: View?) {
        //跳转到fragment2
        btn1.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragment1_to_fragment2)
        }

        //跳转到fragment3
        btn2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragment1_to_fragment3)
        }
    }
}