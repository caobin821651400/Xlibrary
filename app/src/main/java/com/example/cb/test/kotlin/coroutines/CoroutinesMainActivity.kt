package com.example.cb.test.kotlin.coroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.lifecycleScope
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.kotlin.coroutines.net.HttpCoroutinesActivity
import kotlinx.android.synthetic.main.activity_coroutine_main.*
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.system.measureTimeMillis

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 22:19
 * @Desc :协程
 * ====================================================
 */
class CoroutinesMainActivity : BaseActivity() {

    private val imageUrl = "http://110.190.91.33:6020/cdaaa875b5f1eeb3879b33498ee5c8a4"

    override fun getLayoutId(): Int {
        return R.layout.activity_coroutine_main
    }

    override fun initUI() {
        setHeaderTitle("协程")

    }

    override fun initEvent() {
        //线程切换
        button1.setOnClickListener {
            //launch代表创建一个线程，参数代表在那个线程上运行
            //Dispatchers.Main:主线程
            //Dispatchers.Default:此调度程序经过了专门优化，适合在主线程之外执行占用大量 CPU 资源的工作。用例示例包括对列表排序和解析 JSON。
            //Dispatchers.IO:适合在主线程之外执行磁盘或网络 I/O。示例包括使用 Room 组件、从文件中读取数据或向文件中写入数据，以及运行任何网络操作
            CoroutineScope(Dispatchers.Main).launch() {
                val data = withContext(Dispatchers.IO) {
                    getImage(imageUrl)
                }
                imageView.setImageBitmap(data)
            }
        }


        //suspend关键字
        //【挂起】&【暂停】;非阻塞式
        //挂起的是协程，也就是launch或者async包裹的代码块;比如当前在Main线程，挂起在IO线程，那么包裹的代码块就从Main脱离，在IO线程运行
        //暂停只是针对当前线程暂停，代码块切换到另一个线程执行;执行到suspend函数式，就暂时不去执行剩余的协程代码，跳出协程的代码块；执行完成，会自动切到之前的线程
        //作用：提醒;函数创建者对使用者的提醒；提醒使用者在协程里面调用我
        //如果普通方法加上 suspend关键字，那么该方法只能在（协程中或者其他suspend方法中调用）调用
        //什么时候需要用到suspend:原则上耗时操作就可以用协程挂起
        //suspend方法怎么写: fun前面加suspend，用withContext或者其他的包裹整个方法
        //而恢复这个功能是协程的，如果你不在协程里面调用，恢复这个功能没法实现，所以也就回答了这个问题：为什么挂起函数必须在协程或者另一个挂起函数里被调用
        button2.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch() {
                val data = getImage3(imageUrl)
                imageView.setImageBitmap(data)
            }
        }


        //async关键字
        //与launch比较：
        //相同点：都可以开启一个协程
        //不同点：async多返回了一个Deferred;它的意思就是延迟，也就是结果稍后才能拿到。调用 Deferred.await() 就可以得到结果了
        //async()不会为我们启动新的线程,需要在构造方法中传入Dispatchers
        button3.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val data: Deferred<Bitmap?> = async(Dispatchers.IO) { getImage(imageUrl) }
                imageView.setImageBitmap(data.await())
            }
        }


        //协程网络请求
        button4.setOnClickListener {
            launchActivity(HttpCoroutinesActivity::class.java, null)
        }

        var testJob: Job? = null

        button5.setOnClickListener {
            testJob = CoroutineScope(Dispatchers.Main).launch {
                XLogUtils.d("delay start !")
                delay(5000)
                XLogUtils.d("delay end !")
            }

        }

        button6.setOnClickListener {
            XLogUtils.d("stop testJob !")
            testJob?.cancel()
        }

        button7.setOnClickListener { globalScopeTest() }


        button8.setOnClickListener { asyncMultipleTest() }
        button9.setOnClickListener { cancelAndJoin() }
    }

    private fun cancelAndJoin() {
        lifecycleScope.launch {
            val job = launch {
                repeat(10) { index ->
                    XLogUtils.d("job: I'm sleeping $index ...")
                    delay(500)
                }
            }
            delay(1300)//等待一会，在超时之前不会继续往下执行
            XLogUtils.d("main: I'm tired of waiting")
//            job.cancel()
            job.join()
//            job.cancelAndJoin()
            XLogUtils.d("main: Now I can quit.")
            XLogUtils.d("isActive ${job.isActive} isCanceled ${job.isCancelled} isComplete ${job.isCompleted}")
        }
    }

    /**
     * 网络获取一张图片
     */
    private fun getImage(imageUrl: String): Bitmap? {
        var bitmap: Bitmap?
        try {
            val url = URL(imageUrl)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connect()
            val inputStream = conn.inputStream
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            bitmap = null
        }
        return bitmap
    }


    /**
     * suspend 网络获取一张图片
     */
    private suspend fun getImage2(imageUrl: String) =
        withContext(Dispatchers.IO) {
            var bitmap: Bitmap?
            try {
                val url = URL(imageUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connect()
                val inputStream = conn.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                bitmap = null
            }
            bitmap
        }

    /**
     * suspend 网络获取一张图片
     * 返回数据 不需要写return；返回数据在最后一行
     */
    private suspend fun getImage3(imageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connect()
                val inputStream = conn.inputStream
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    /**
     * runBlocking回阻塞当前线程执行，但是内部的协程不会阻塞
     */
    private fun runBloTest() {
        XLogUtils.d("caobin start")
        //context上下文使用默认值,阻塞当前线程，直到代码块中的逻辑完成
        runBlocking {
            //这里是协程体
            delay(1000)//挂起函数，延迟1000毫秒
            XLogUtils.d("caobin runBlocking")
        }
        XLogUtils.d("caobin end")
    }

    private fun globalScopeTest() {
        XLogUtils.d("start")
        //创建全局作用域协程，类似于Application,不会阻塞当前线程，生命周期与应用程序一致
        val job = GlobalScope.launch {
            XLogUtils.d("delay 1s")
            delay(1000)//1秒无阻塞延迟（默认单位为毫秒）
            XLogUtils.d("delay 1s complete")
        }
        XLogUtils.d("end")//主线程继续，而协程被延迟
    }

    private fun asyncMultipleTest() {
        XLogUtils.d("start")
        GlobalScope.launch {
            val time = measureTimeMillis {//计算执行时间
                val deferredOne: Deferred<Int> = async {
                    delay(2000)
                    XLogUtils.d("asyncOne")
                    100//这里返回值为100
                }

                val deferredTwo: Deferred<Int> = async {
                    delay(3000)
                    XLogUtils.d("asyncTwo")
                    200//这里返回值为200
                }

                val deferredThr: Deferred<Int> = async {
                    delay(4000)
                    XLogUtils.d("asyncThr")
                    300//这里返回值为300
                }

                //等待所有需要结果的协程完成获取执行结果
                val result = deferredOne.await() + deferredTwo.await() + deferredThr.await()
                XLogUtils.d("result == $result")
            }
            XLogUtils.d("耗时 $time ms")
        }
        XLogUtils.d("end")
    }
}
