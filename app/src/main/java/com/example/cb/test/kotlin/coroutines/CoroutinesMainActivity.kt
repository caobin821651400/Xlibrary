package com.example.cb.test.kotlin.coroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_coroutine_main.*
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

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
                val data = async(Dispatchers.IO) { getImage(imageUrl) }
                imageView.setImageBitmap(data.await())
            }
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
}
