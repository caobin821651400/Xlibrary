package com.example.cb.test.kotlin.coroutines.net

import com.example.cb.test.net.BaseResp
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("appio2/app/service/appHomeFX")
    suspend fun getDara(@Field("pageNo") pageNo: Int,
                        @Field("pageSize") pageSize: Int
    ): BaseResp<ZnsListBean>


}