package com.example.cb.test.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/5/14 22:29
 * @Desc :
 * ====================================================
 */
class MainActivityViewModule : ViewModel() {


    //控制抽屉菜单
    public val openDrawer = MutableLiveData<Boolean>()


    //控制抽屉菜单
    public val allowDrawerOpen = MutableLiveData<Boolean>()

    /****
     * 默认允许展开抽屉菜单
     */
    init {
        allowDrawerOpen.value = true
    }

}