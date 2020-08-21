package com.example.cb.test.kotlin.coroutines.net


/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/19 12:52
 * @Desc :分页数据
 * ====================================================
 */
data class ApiListResponse<T>(
        var datas: ArrayList<T>,
        var curPage: Int,
        var offset: Int,
        var over: Boolean,
        var pageCount: Int,
        var size: Int,
        var total: Int
) {

    /**
     * 是否是空数据
     */
    fun isEmpty() = datas.isNullOrEmpty()

    /**
     * 是否有更多
     */
    fun isLoadMore() = (curPage * size) < total

}