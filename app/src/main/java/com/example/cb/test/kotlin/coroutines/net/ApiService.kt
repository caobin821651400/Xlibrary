package com.example.cb.test.kotlin.coroutines.net

import cn.sccl.http.interceptor.BaseUrlInterceptor.Companion.CHANGE_URL_HEADER
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    /**
     * 获取首页文章数据
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") pageNo: Int): ApiCodeResponse<ApiListResponse<WanAndroidBean>>


    /**
     * 获取首页文章数据
     */
    @Headers(
            "${CHANGE_URL_HEADER}: https://www.wanandroid.com/",
            "token:123456"
    )
    @GET("article/list/{page}/json")
    suspend fun getArticleList2(@Path("page") pageNo: Int): ApiCodeResponse<ApiListResponse<WanAndroidBean>>
}