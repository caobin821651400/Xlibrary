package cn.sccl.http.upload

import cn.sccl.http.XHttp
import cn.sccl.http.api.UploadApiService
import cn.sccl.http.exception.ExceptionHandle
import cn.sccl.http.exception.NetException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/8 14:16
 * @Desc :上传
 * ====================================================
 */
object UploadManager {

    /**
     * 上传
     */
    suspend inline fun <reified T> doUpload(
            paramsMap: HashMap<String, Any>?,
            fileMap: HashMap<String, File>,
            tag: String,
            url: String,//上传的地址
            success: (T) -> Unit,//成功回调
            error: (NetException) -> Unit = {}//不关注失败 可以不用管
    ) {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        //添加文件
        fileMap.forEach { (key, value) ->
            builder.addFormDataPart(
                    key, value.name,
                    value.asRequestBody("multipart/form-data".toMediaTypeOrNull()))
        }

        //添加参数
        paramsMap?.apply {
            forEach { (key, value) ->
                builder.addFormDataPart(key, "$value")
            }
        }


        runCatching {
            val result = withContext(Dispatchers.IO) {
                //用一个map保存协程
                UpLoadPool.add(tag, this)
                XHttp.getUpLoadService(UploadApiService::class.java)
                        .upFileList(url, /*null,*/  builder.build().parts)
                        .body()?.string() ?: ""
            }
            System.err.println("返回参数->$result")
            val jsonObject = JSONObject(result)
            if (jsonObject.optInt("code") != 0) {
                error(NetException(NetException.UP_LOAD_ERROR, jsonObject.optString("msg")))
                UpLoadPool.remove(tag)
                return
            }

            //自动解析泛型的具体类型
            val dataString = jsonObject.optJSONObject("data").toString()
            val resultData: T = dataString.fromGson()
            resultData
        }.onSuccess {
            val data = it
            success(data)
            UpLoadPool.remove(tag)
        }.onFailure {
            error(ExceptionHandle.handleException(it))
            UpLoadPool.remove(tag)
        }
    }


    fun cancel(tag: String) {
        UpLoadPool.remove(tag)
    }

    fun cancelAll() {
        UpLoadPool.removeAll()
    }


    /**
     *
     */
    inline fun <reified T> String.fromGson(): T {
        return Gson().fromJson(this, T::class.java)
    }
}