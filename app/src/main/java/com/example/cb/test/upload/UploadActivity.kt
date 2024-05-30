package com.example.cb.test.upload

import android.Manifest
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.http.XHttp
import cn.sccl.http.upload.UploadManager
import cn.sccl.http.upload.listener.ProgressInfo
import cn.sccl.http.upload.listener.UploadInterceptor
import cn.sccl.http.upload.listener.UploadProgressListener
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import cn.sccl.xlibrary.utils.XPermission
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.io.File

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/8 15:32
 * @Desc :
 * ====================================================
 */
class UploadActivity : BaseActivity(), UploadProgressListener, CoroutineScope by MainScope() {

    private val tvInfo by lazyNone { findViewById<TextView>(R.id.tvInfo) }
    private val btnStart by lazyNone { findViewById<Button>(R.id.btnStart) }
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
//                        file = File(pathFile?.absolutePath + "/111.jpg")
                    file = File(pathFile?.absolutePath + "/222.jpg")
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
        val fileMap = hashMapOf("images[1]" to file)
        val paramsMap = hashMapOf<String, Any>(
            "title" to "1214",
            "content" to "1214",
            "area" to "成都"
        )
        UploadManager.doUpload<UploadDataBean>(
            paramsMap, fileMap, "1111",
            "https://api.holapets.cn/api/articles/create",
            {
                XLogUtils.e("哈哈 " + it.content)
            },
            {
                XLogUtils.e(it.errorMsg)
            }
        )
    }


    override fun onProgress(progressInfo: ProgressInfo) {
        tvInfo.text = "上传中 ${progressInfo.percent}"
    }

    override fun onError(id: Long, e: Exception) {
    }
}