package com.example.cb.test.jetpack.navigation

import android.view.View
import androidx.navigation.Navigation
import com.example.cb.test.R
import com.example.cb.test.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_navigation1.*

class NavigationFragment2 : BaseFragment() {

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