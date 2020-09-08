package cn.sccl.http.download

import cn.sccl.http.XHttp
import cn.sccl.http.api.DownloadApiService
import cn.sccl.http.download.DownLadProgressListener.OnDownLoadListener
import cn.sccl.http.download.utils.FileTool
import cn.sccl.http.download.utils.ShareDownLoadUtil
import cn.sccl.http.exception.NetException
import cn.sccl.http.exception.NetException.Companion.DOWN_LOAD_ERROR
import cn.sccl.http.exception.NetException.Companion.DOWN_LOAD_PATH_ERROR
import cn.sccl.http.exception.NetException.Companion.DOWN_URL_ERROR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.File

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/28 9:58
 * @Desc :下载类
 * ====================================================
 */
object DownLoadManager {

    private val TAG = "RX_DownLoadManager"

    /**
     * 开始下载
     */
    suspend fun downLoad(
            tag: String,//唯一标识
            url: String,
            savePath: String,
            saveName: String,
            listener: OnDownLoadListener
    ) {
        //下载放到IO线程
        withContext(Dispatchers.IO) {
            doDownLoad(tag, url, savePath, saveName, listener, this)
        }
    }

    suspend fun pause(tag: String) {
        DownLoadPool.listenerMap[tag]?.onPause(tag)
        DownLoadPool.pause(tag)
    }


    private suspend fun doDownLoad(
            tag: String,
            url: String,
            savePath: String,
            saveName: String,
            loadListener: OnDownLoadListener,
            coroutineScope: CoroutineScope
    ) {
        val scope = DownLoadPool.scopeMap[tag]
        scope?.apply {
            if (isActive) {
                return
            } else {
                DownLoadPool.scopeMap.remove(tag)
            }
        }

        if (saveName.isEmpty()) {
            withContext(Dispatchers.Main) {
                loadListener.onError(tag, NetException(DOWN_LOAD_PATH_ERROR))
            }
            return
        }

        //获取历史进度
        val file = File("$savePath/$saveName")
        val downLoadedLength = if (!file.exists()) {
            0L
        } else {
            ShareDownLoadUtil.getLong(tag, 0)
        }

        runCatching {
            //添加任务
            DownLoadPool.addJob(tag, coroutineScope, savePath, loadListener)

            withContext(Dispatchers.Main) {
                loadListener.onPrepare(tag)
            }

            val responseBody = XHttp.getDownLoadService(DownloadApiService::class.java)
                    .downloadFile("bytes=$downLoadedLength-", url).body()
            if (responseBody == null) {
                withContext(Dispatchers.Main) {
                    loadListener.onError(tag, NetException(DOWN_URL_ERROR))
                }
                DownLoadPool.remove(tag)
                return
            }

            FileTool.downToFile(
                    tag,
                    savePath,
                    saveName,
                    downLoadedLength,
                    responseBody,
                    loadListener
            )

        }.onFailure {
            withContext(Dispatchers.Main) {
                loadListener.onError(tag, NetException(DOWN_LOAD_ERROR))
            }
            DownLoadPool.remove(tag)
        }
    }

    //格式化小数
    fun bytes2kb(bytes: Long): String {
        return FileTool.byte2kb(bytes)
    }
}