package cn.sccl.http.upload

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/8 10:37
 * @Desc :
 * ====================================================
 */
interface OnUpLoadListener<T> {

    fun opUploadPrepare(tag: String)

    fun onUpLoadSuccess(tag: String, data: T)

    fun onUpLoadFailed(tag: String, msg:String)

    fun onUploadCancel(tag: String)

    /**
     *
     * @param tag          tag
     * @param bytesWriting 已经写的字节数
     * @param totalBytes   文件的总字节数
     * @param progress     进度
     */
    fun onUploadProgress(tag: String, bytesWriting: Long, totalBytes: Long, progress: Int)
}