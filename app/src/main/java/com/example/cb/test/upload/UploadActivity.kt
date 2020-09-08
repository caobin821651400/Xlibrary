package com.example.cb.test.upload

import android.Manifest
import android.os.Environment
import cn.sccl.http.XHttp
import cn.sccl.http.upload.OnUpLoadListener
import cn.sccl.http.upload.UploadManager
import cn.sccl.http.upload.listener.ProgressInfo
import cn.sccl.http.upload.listener.UploadInterceptor
import cn.sccl.http.upload.listener.UploadProgressListener
import cn.sccl.xlibrary.utils.XLogUtils
import cn.sccl.xlibrary.utils.XPermission
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import java.io.File
import java.lang.Exception

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/8 15:32
 * @Desc :
 * ====================================================
 */
class UploadActivity : BaseActivity(), UploadProgressListener, CoroutineScope by MainScope() {

    override fun getLayoutId() = R.layout.activity_upload
    private lateinit var file: File
    private var pathFile: File? = null

    override fun initUI() {
        setHeaderTitle("上传")

        XPermission.requestPermissions(this, 1025,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                object : XPermission.OnPermissionListener {
                    override fun onPermissionGranted() {

                        pathFile = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                        file = File(pathFile?.absolutePath + "/111.jpg")
                        XLogUtils.d("")
                    }

                    override fun onPermissionDenied() {
                    }
                })

        val client = OkHttpClient.Builder().addInterceptor(UploadInterceptor(this)).build()

        XHttp.setUploadRetrofit(
                Retrofit.Builder()
                        .baseUrl("https://iot.sctel.com.cn/")
                        .client(client)
                        .build()
        )
    }

    override fun initEvent() {
        btnStart.setOnClickListener {
            launch {
                start()
            }
        }
    }

    private suspend fun start() {
        if (!file.exists()) return
        val body = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val parts = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("images[1]", "111.jpg", body)
                .addFormDataPart("title", "1214")
                .addFormDataPart("content", "1214")
                .addFormDataPart("area", "成都1")
                .build().parts
        UploadManager.doUpload(
                "1111",
                "https://api.holapets.cn/api/articles/create",
                parts
        )
    }


    override fun onProgress(progressInfo: ProgressInfo) {
        XLogUtils.d("onProgress ${progressInfo.percent}")
    }

    override fun onError(id: Long, e: Exception) {
        XLogUtils.d("onError ${e.message}")
    }
}