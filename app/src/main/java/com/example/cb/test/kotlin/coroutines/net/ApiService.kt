package com.example.cb.test.kotlin.coroutines.net

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("appio2/app/service/appHomeFX")
    suspend fun getData(@Field("pageNo") pageNo: Int,
                        @Field("pageSize") pageSize: Int
    ): Response<ResponseBody>


}