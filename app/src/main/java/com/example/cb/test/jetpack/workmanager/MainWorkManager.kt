package com.example.cb.test.jetpack.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import cn.sccl.xlibrary.utils.XLogUtils
/**
 * ====================================================
 * @User :caobin
 * @Date :2020/5/11 12:23
 * @Desc :
 * ====================================================
 */
class MainWorkManager(var context: Context, var workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    override fun doWork(): Result {

        XLogUtils.d("后台任务执行了")

        //接受activity传递的数据
        XLogUtils.e("传递数据->${workerParams.inputData.getString("data")}")

        //返回给activity数据
        val data = Data.Builder().putString("data", "我是返回数据").build()
        val success=Result.success(data)

        return success
//        return Result.success()
    }
}