package com.example.cb.test.download

import cn.sccl.http.download.DownLadProgressListener

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/7 11:31
 * @Desc :
 * ====================================================
 */
class DownloadTask(
        val url: String,
        val taskId: String,
        val saveName: String

)