package com.example.cb.test.kotlin.coroutines

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/20 21:25
 * @Desc :列表分页封装，用于ViewModule通过LiveData向 view层传递数据
 * ====================================================
 */
data class XListPageDataUi<T>(
        val isSuccess: Boolean = false,
        val isRefresh: Boolean = false,
        val isEmpty: Boolean = false,
        val isHasMore: Boolean = false,
        val isFirstEmpty:Boolean = false,
        val errMsg: String = "",
        val listData: ArrayList<T>? = null
)