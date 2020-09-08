package cn.sccl.http.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url
/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/1 19:42
 * @Desc :
 * ====================================================
 */
interface DownloadApiService {


//    /**
//     * 下载文件
//     */
//    @Streaming
//    @GET
//    suspend fun downLoadFile(
//            @Header("RANGE") start: String,
//            @Url url: String
//    ): Response<ResponseBody>

    @Streaming
    @GET
    suspend fun downloadFile(
            @Header("RANGE") start: String,
            @Url url: String
    ): Response<ResponseBody>

}