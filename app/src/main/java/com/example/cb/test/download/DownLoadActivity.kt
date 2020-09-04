package com.example.cb.test.download

import android.Manifest
import android.os.Environment
import cn.sccl.http.XHttp
import cn.sccl.http.download.DownLadProgressListener
import cn.sccl.http.download.DownLoadManager
import cn.sccl.http.exception.NetException
import cn.sccl.xlibrary.utils.XLogUtils
import cn.sccl.xlibrary.utils.XPermission
import com.example.cb.test.MyApplication
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_aidl_test.*
import kotlinx.android.synthetic.main.activity_down_load.*
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
class DownLoadActivity : BaseActivity(), CoroutineScope by MainScope(), DownLadProgressListener.OnDownLoadListener {

    private val url1 = "http://update.9158.com/miaolive/Miaolive.apk"
    private val url2 = "https://o8g2z2sa4.qnssl.com/android/momo_8.18.5_c1.apk"
    private val url3 = "https://o8g2z2sa4.qnssl.com/android/momo_8.18.5_c1.apk"

    companion object {
        const val TAG2 = "aaaaa->"
    }

    data class DownLoadInfo(val url: String, val taskId: String, val saveName: String)

    private lateinit var mAdapter: DownLoadAdapter
    private val mList = ArrayList<DownLoadInfo>()

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

//        mAdapter = DownLoadAdapter(mRecyclerView)
//        mRecyclerView.adapter = mAdapter
//
//        mList.add(DownLoadInfo(url1, url1, "111.apk"))
//        mList.add(DownLoadInfo(url2, url2, "222.apk"))
//        mList.add(DownLoadInfo(url3, url3, "333.apk"))
//        mAdapter.dataLists = mList
    }

    override fun initEvent() {
        btnDownLoad.setOnClickListener {
            launch {
//                startDownLoad2()
                if (!isRun) startDownLoad() else pauseDownLoad(url1)
            }
        }


//        mAdapter.setListener(
//                { startDownLoad(it.url, it.saveName) },
//                { pauseDownLoad(it.taskId) }
//        )
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

    //获取更路径
    private fun getBasePath(): String {
        val file = MyApplication.app.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        return file?.absolutePath ?: ""
    }

    override fun onPrepare(tag: String) {
        XLogUtils.v("准备下载 $tag  thread ${Thread.currentThread().name}")
//        mAdapter.setDownLoadPrepare(tag)
    }

    override fun onProgress(tag: String, progress: Int) {
        XLogUtils.d("onProgress=$progress")
    }

    override fun onError(tag: String, e: NetException) {
//        mAdapter.setDownLoadError(tag, e)
    }

    override fun onSuccess(tag: String, path: String) {
//        mAdapter.setDownLoadSuccess(tag, path)
    }

    override fun onPause(tag: String) {
//        mAdapter.setDownLoadPause(tag)
    }

    override fun onCancel(tag: String) {
//        mAdapter.setDownLoadCancel(tag)
    }

    override fun onUpdate(tag: String, progress: Int, loadedLength: Long, totalLength: Long, isDone: Boolean) {
        XLogUtils.i(
                "下载进度 $tag  loadedLength= $loadedLength  totalLength= $totalLength" +
                        "  isDone $isDone thread ${Thread.currentThread().name}"
        )
        tvInfo.text="进度=$progress"
//        mAdapter.setDownLoadProgress(
//                tag,
//                progress,
//                DownLoadManager.bytes2kb(loadedLength),
//                DownLoadManager.bytes2kb(totalLength),
//                isDone
//        )
    }

}