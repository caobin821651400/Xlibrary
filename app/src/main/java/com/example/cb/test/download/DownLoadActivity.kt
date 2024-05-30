package com.example.cb.test.download

import android.Manifest
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.http.XHttp
import cn.sccl.http.download.DownLadProgressListener
import cn.sccl.http.download.DownLoadManager
import cn.sccl.http.exception.NetException
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import cn.sccl.xlibrary.utils.XPermission
import com.example.cb.test.MyApplication
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/1 21:09
 * @Desc :
 * ====================================================
 */
class DownLoadActivity : BaseActivity(), CoroutineScope by MainScope(),
    DownLadProgressListener.OnDownLoadListener {

    private val url1 = "http://update.9158.com/miaolive/Miaolive.apk"
    private val url2 = "https://o8g2z2sa4.qnssl.com/android/momo_8.18.5_c1.apk"
    private val url3 = "https://o8g2z2sa4.qnssl.com/android/momo_8.18.5_c1.apk"

    private val mRecyclerView by lazyNone { findViewById<RecyclerView>(R.id.mRecyclerView) }
    private val btnDownLoad by lazyNone { findViewById<Button>(R.id.btnDownLoad) }
    private val tvInfo by lazyNone { findViewById<TextView>(R.id.tvInfo) }

    companion object {
        const val TAG2 = "aaaaa->"
    }

    private lateinit var mAdapter: DownLoadAdapter
    private val mList = ArrayList<DownloadTask>()

    override fun getLayoutId() = R.layout.activity_down_load

    override fun initUI() {
        setHeaderTitle("下载")

        XPermission.requestPermissions(this, 1025,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            object : XPermission.OnPermissionListener {
                override fun onPermissionGranted() {
                }

                override fun onPermissionDenied() {
                }
            })

        val client = OkHttpClient.Builder().build()

        XHttp.setDownLoadRetrofit(
            Retrofit.Builder()
                .baseUrl("https://iot.sctel.com.cn/")
                .client(client)
                .build()
        )

//        mAdapter2=DownLoadAdapter2()
//        mAdapter2.mList=mList


        mAdapter = DownLoadAdapter(mRecyclerView)
        mRecyclerView.adapter = mAdapter

        mList.add(DownloadTask(url1, url1, "111.apk"))
        mList.add(DownloadTask(url2, url2, "222.apk"))
        mList.add(DownloadTask(url3, url3, "333.apk"))
        mAdapter.dataLists = mList
    }

    override fun initEvent() {
        btnDownLoad.setOnClickListener {
            launch {
//                startDownLoad2()
                if (!isRun) startDownLoad() else pauseDownLoad(url1)
            }
        }
    }

    private var isRun = false

    private suspend fun startDownLoad() {
        isRun = true
        btnDownLoad.text = "暂停"
        DownLoadManager.downLoad(
            url1, url1,
            getBasePath(), "111.apk", this
        )
    }

    private suspend fun startDownLoad2() {
        DownLoadManager.downLoad(
            url2, url2,
            getBasePath(), "222.apk", this
        )
    }

    /**
     * 暂停
     */
    private suspend fun pauseDownLoad(tag: String) {
        isRun = false
        btnDownLoad.text = "下载"
        DownLoadManager.pause(tag)
    }

    //获取路径
    private fun getBasePath(): String {
        val file = MyApplication.app.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        return file?.absolutePath ?: ""
    }

    override fun onPrepare(tag: String) {
        XLogUtils.v("准备下载 $tag  thread ${Thread.currentThread().name}")
    }

    override fun onProgress(tag: String, progress: Int) {
        XLogUtils.d("onProgress=$progress")
    }

    override fun onError(tag: String, e: NetException) {
    }

    override fun onSuccess(tag: String, path: String) {
    }

    override fun onPause(tag: String) {
    }

    override fun onCancel(tag: String) {
    }

    override fun onUpdate(
        tag: String,
        progress: Int,
        loadedLength: Long,
        totalLength: Long,
        isDone: Boolean
    ) {
        XLogUtils.i(
            "下载进度 $tag  loadedLength= $loadedLength  totalLength= $totalLength" +
                    "  isDone $isDone thread ${Thread.currentThread().name}"
        )
        tvInfo.text = "进度=$progress"
    }
}