package cn.sccl.http.download

import cn.sccl.http.download.DownLadProgressListener.OnDownLoadListener
import cn.sccl.http.download.utils.ShareDownLoadUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import java.util.concurrent.ConcurrentHashMap

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/31 16:10
 * @Desc :
 * ====================================================
 */
object DownLoadPool {

    //保存工作的协程
    val scopeMap = ConcurrentHashMap<String, CoroutineScope>()

    //下载路径
    val pathMap = ConcurrentHashMap<String, String>()

    //下载的listener
    val listenerMap = ConcurrentHashMap<String, OnDownLoadListener>()

    /**
     * 添加任务
     */
    fun addJob(key: String, job: CoroutineScope, path: String, loadListener: OnDownLoadListener) {
        scopeMap[key] = job
        listenerMap[key] = loadListener
        pathMap[key] = path
    }

    /**
     * 移除一个任务
     */
    fun remove(key: String) {
        try {
            pause(key)
            scopeMap.remove(key)
            listenerMap.remove(key)
            pathMap.remove(key)
            ShareDownLoadUtil.remove(key)
        } catch (e: Exception) {

        }
    }

    /**
     * 取消一个任务
     */
    fun pause(key: String) {
        val scope = scopeMap[key]
        scope?.apply {
            if (isActive) cancel()
        }
    }
}