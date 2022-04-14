package com.example.cb.test.kotlin.coroutines.net

import cn.sccl.http.interceptor.BaseUrlInterceptor.Companion.CHANGE_URL_HEADER
import com.example.cb.test.jetpack.paging.RepoResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * 获取首页文章数据
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") pageNo: Int): ApiCodeResponse<ApiListResponse<WanAndroidBean>>


    /**
     * 获取首页文章数据
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleListCall(@Path("page") pageNo: Int): Call<ResponseBody>


    /**
     * 获取首页文章数据
     */
    @Headers(
            "${CHANGE_URL_HEADER}: https://www.wanandroid.com/",
            "token:123456"
    )
    @GET("article/list/{page}/json")
    suspend fun getArticleList2(@Path("page") pageNo: Int): ApiCodeResponse<ApiListResponse<WanAndroidBean>>

    @Headers("${CHANGE_URL_HEADER}:https://api.github.com/")
    @GET("search/repositories?sort=stars&q=Android")
    suspend fun getGitHubData(@Query("page") page: Int, @Query("per_page") perPage: Int): RepoResponse
}