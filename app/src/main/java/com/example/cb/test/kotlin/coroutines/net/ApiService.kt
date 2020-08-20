package com.example.cb.test.kotlin.coroutines.net

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    /**
     * 获取首页文章数据
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") pageNo: Int): ApiCodeResponse<ApiListResponse<WanAndroidBean>>

}