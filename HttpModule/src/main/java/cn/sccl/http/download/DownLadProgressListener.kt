package cn.sccl.http.download

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/28 10:24
 * @Desc :
 * ====================================================
 */
interface DownLadProgressListener {


    fun onUpdate(
            tag: String,
            progress: Int,
            loadedLength: Long,
            totalLength: Long,
            isDone: Boolean
    )

}