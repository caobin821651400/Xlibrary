package cn.sccl.http.download

import cn.sccl.http.exception.NetException
import retrofit2.HttpException

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/28 10:24
 * @Desc :
 * ====================================================
 */
interface DownLadProgressListener {

    /**
     * 下载进度
     *
     * @param tag url
     * @param progress  进度
     * @param loadedLength  已下载
     * @param totalLength 总共长度
     * @param isDone  是否完成
     */
    fun onUpdate(
            tag: String,
            progress: Int,
            loadedLength: Long,
            totalLength: Long,
            isDone: Boolean
    )


    interface OnDownLoadListener : DownLadProgressListener {

        //准备完成
        fun onPrepare(tag: String)

        //下载中
        fun onProgress(tag: String, progress: Int)

        //下载失败
        fun onError(tag: String, e: NetException)

        //下载成功
        fun onSuccess(tag: String, path: String)

        //下载暂停
        fun onPause(tag: String)

        //下载取消
        fun onCancel(tag: String)
    }
}