package com.example.cb.test.jetpack.navigation

import androidx.navigation.fragment.NavHostFragment
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity


/**
 * ====================================================
 * @User :caobin
 * @Date :2020/5/8 21:39
 * @Desc :
 * ====================================================
 */
class NavigationActivity : BaseActivity() {
    override fun initUI() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_navigation
    }

    override fun initEvent() {
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val fragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
//        return NavHostFragment.findNavController(fragment!!).navigateUp()
//    }

}
