package com.example.cb.test.jetpack.navigation

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseFragment
import kotlinx.coroutines.launch

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/5/8 22:13
 * @Desc :
 * ====================================================
 */
class NavigationFragment1 : BaseFragment() {

    private val btn1 by lazyNone { requireView().findViewById<Button>(R.id.btn1) }
    private val btn2 by lazyNone { requireView().findViewById<Button>(R.id.btn2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        XLogUtils.d("NavigationFragment1 onCreate")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation1
    }

    override fun initUI(v: View?) {
        viewLifecycleOwner.lifecycleScope.launch { }
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