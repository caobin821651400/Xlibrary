package com.example.cb.test.jetpack.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import cb.xlibrary.utils.XLogUtils
/**
 * ====================================================
 * @User :caobin
 * @Date :2020/5/11 12:23
 * @Desc :
 * ====================================================
 */
class MainWorkManager3(var context: Context, var workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    override fun doWork(): Result {

        XLogUtils.d("MainWorkManager3 后台任务执行了")


        return Result.success()
    }
}