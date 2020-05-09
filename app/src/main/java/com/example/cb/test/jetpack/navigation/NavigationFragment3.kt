package com.example.cb.test.jetpack.navigation

import android.view.View
import androidx.navigation.Navigation
import com.example.cb.test.R
import com.example.cb.test.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_navigation1.*

class NavigationFragment3 : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation3
    }

    override fun initUI(v: View?) {
    }

    override fun initEvent(view: View?) {
        //跳转到fragment2
        btn1.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragment3_to_fragment1)
        }

        //跳转到fragment3
        btn2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragment3_to_fragment2)

            //一下三种都可以
//            Fragment.findNavController()
//            View.findNavController()
//            Activity.findNavController(viewId: Int)
//            findNavController().navigate(R.id.action_fragment3_to_fragment2)
        }
    }
}