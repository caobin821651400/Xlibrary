package cn.sccl.http.upload

import cn.sccl.http.XHttp
import cn.sccl.http.api.UploadApiService
import cn.sccl.http.exception.ExceptionHandle
import cn.sccl.http.exception.NetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/8 14:16
 * @Desc :上传
 * ====================================================
 */
object UploadManager {

    private val bodyMap = HashMap<String, ProgressRequestBody>()//文件RequestBody


    /**
     * 上传
     */
    suspend fun doUpload(
            tag: String,
            url: String,
            files: List<MultipartBody.Part>
    ) {
        bodyMap.clear()
        withContext(Dispatchers.IO) {
            //为每个file创建ProgressRequestBody
            UpLoadPool.add(tag, this)


            val a = XHttp.getUpLoadService(UploadApiService::class.java)
                    .upFileList(url, /*null,*/  files).body()
                    ?.string() ?: ""
            val aa = 10
        }
    }

    private suspend fun upload(
            block: suspend () -> String?,
            success: (String) -> Unit,//成功回调
            error: (NetException) -> Unit = {}//不关注失败 可以不用管
    ) {
        kotlin.runCatching {
            block()
        }.onSuccess {
            withContext(Dispatchers.Main) {
                val a = it
                if (!it.isNullOrEmpty()) {
                    success(it)
                } else {
                    error(NetException(NetException.UP_LOAD_ERROR))
                }
            }
        }.onFailure {
            withContext(Dispatchers.Main) {
                error(ExceptionHandle.handleException(it))
            }
        }
    }


//    private suspend fun <T : Any> upload(
//            block: suspend () -> BaseResponse<T>,
//            success: (T) -> Unit,//成功回调
//            error: (NetException) -> Unit = {}//不关注失败 可以不用管
//    ) {
//        kotlin.runCatching {
//            block()
//        }.onSuccess {
//            if (it.isSuccess()) {
//                success(it.getResponseData())
//            } else {
//                error(NetException(it.getResponseCode(), it.getResponseMsg(), ""))
//            }
//        }.onFailure {
//            error(ExceptionHandle.handleException(it))
//        }
//
//    }

}