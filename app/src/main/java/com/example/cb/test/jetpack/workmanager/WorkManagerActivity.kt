package com.example.cb.test.jetpack.workmanager

import androidx.lifecycle.Observer
import androidx.work.*
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_work_manager.*
import java.util.concurrent.TimeUnit

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/5/10 22:40
 * @Desc :
 * ====================================================
 */
class WorkManagerActivity : BaseActivity() {

    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest

    override fun getLayoutId(): Int {
        return R.layout.activity_work_manager
    }

    override fun initUI() {
        setHeaderTitle("WorkManager使用")
        val data = Data.Builder().putString("data", "WorkManagerActivity传递的值").build()
        oneTimeWorkRequest = OneTimeWorkRequest.Builder(MainWorkManager::class.java)
                .setInputData(data)
                .build()
        //接受回传数据
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                .observe(this, object : Observer<WorkInfo> {
                    override fun onChanged(t: WorkInfo?) {
                        t?.let {
                            XLogUtils.i("WorkManagerActivity接受->" + t.outputData.getString("data"))
                        }
                    }
                })

    }

    override fun initEvent() {
        //单一任务
        btn1.setOnClickListener {
            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

        }
        //多个任务
        btn2.setOnClickListener {
            val work2 = OneTimeWorkRequest.Builder(MainWorkManager2::class.java).build()
            val work3 = OneTimeWorkRequest.Builder(MainWorkManager3::class.java).build()
            val work4 = OneTimeWorkRequest.Builder(MainWorkManager4::class.java).build()
            val work5 = OneTimeWorkRequest.Builder(MainWorkManager5::class.java).build()

            //顺序执行
            WorkManager.getInstance(this).beginWith(work2)
                    .then(work3)
                    .then(work4)
                    .then(work5)
                    .enqueue()

        }

        //重复任务
        btn1.setOnClickListener {
            //最少15分钟执行一次
            val work = PeriodicWorkRequest.Builder(MainWorkManager2::class.java, 15, TimeUnit.SECONDS).build()

            //监听状态
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(work.id)
                    .observe(this, object : Observer<WorkInfo> {
                        override fun onChanged(t: WorkInfo?) {
                            t?.let {
                                XLogUtils.d("状态->" + t.state.name)
                            }
                        }
                    })

            WorkManager.getInstance(this).enqueue(work)

        }
        //约束条件
        btn4.setOnClickListener {
            val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresCharging(true)//充电
                    .setRequiresDeviceIdle(true)//空闲
                    .build()

            val work = OneTimeWorkRequest.Builder(MainWorkManager2::class.java)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(this).enqueue(work)
        }
    }
}
