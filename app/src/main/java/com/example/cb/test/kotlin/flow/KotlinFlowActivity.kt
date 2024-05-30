package com.example.cb.test.kotlin.flow

import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import cn.sccl.xlibrary.adapter.XViewHolder
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.CommonMenuBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/10/30 10:14
 * @Desc : https://www.jianshu.com/p/f2203227dabf
 * ====================================================
 */
class KotlinFlowActivity : BaseActivity(), CoroutineScope by MainScope() {

    private val mRecyclerView by lazyNone { findViewById<RecyclerView>(R.id.mRecyclerView) }
    private lateinit var mAdapter: MAdapter
    private var mList: ArrayList<CommonMenuBean> = ArrayList()

    private val viewModel by viewModels<KotlinFlowViewModel>()

    override fun getLayoutId() = R.layout.activity_kotlin_flow

    override fun initUI() {
        setHeaderTitle("Kotlin Flow 使用")
        mAdapter = MAdapter(mRecyclerView)
        mRecyclerView.adapter = mAdapter

        mList.add(CommonMenuBean("简单使用", null))
        mList.add(CommonMenuBean("flow同步执行", null))
        mList.add(CommonMenuBean("channel异步执行", null))
        mList.add(CommonMenuBean("切换线程", null))
        mList.add(CommonMenuBean("flow取消", null))
        mList.add(CommonMenuBean("flow结束", null))
        mList.add(CommonMenuBean("flow重试机制", null))
        mList.add(CommonMenuBean("普通flow冷流", null))
        mList.add(CommonMenuBean("flow异步执行", null))

        mAdapter.dataLists = mList
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { _: View?, position: Int ->
            when (position) {
                0 -> launch { demo1() }
                1 -> launch { demo2() }
                2 -> launch { demo3() }
                3 -> launch { demo4() }
                4 -> launch { demo5() }
                5 -> launch { demo6() }
                6 -> launch { demo7() }
                7 -> launch { demo8() }
                8 -> launch { demo33() }
            }
        }
    }

    private suspend fun demo1() {
        flow {
            (1..5).forEach {
                delay(1000)
                //必须在{}内部
                emit(it)
            }
        }.collect {
            XLogUtils.d("collect value= $it")
        }
    }

    /**
     *这里suspend方法就代表两个flow在同一个协程中同步执行
     */
    private suspend fun demo2() {
        flowOf(1, 2, 3, 4, 5).onEach {
            delay(1000)
        }.collect {
            XLogUtils.d("collect value= $it")
        }
        //上面flow会阻塞下面的，是同步执行的
        flowOf(7, 8, 9, 10, 11).onEach {
            delay(1000)
        }.collect {
            XLogUtils.d("collect value= $it")
        }
    }

    private suspend fun demo3() {
//        val time = measureTimeMillis {
//            listOf(1, 2, 3, 4, 5).asFlow().onEach { delay(1000) }
//                    .collect {
//                        delay(1000)
//                        XLogUtils.d("collect value= $it")
//                    }
//        }
//        XLogUtils.d("run time= $time")

        //如果在flow中切换线程，两个delay是异步执行的
//        val time2 = measureTimeMillis {
//            listOf(1, 2, 3, 4, 5).asFlow().onEach { delay(1000) }
//                    .flowOn(Dispatchers.IO)
//                    .collect {
//                        delay(1000)
//                        XLogUtils.d("collect value= $it")
//                    }
//        }
//        XLogUtils.d("run time= $time2")


        //channelFlow 跟上面切换线程所用时间差不多
        val time3 = measureTimeMillis {
            channelFlow {
                (1..5).forEach {
                    delay(1000)
                    send(it)
                }
            }.collect {
                delay(1000)
                XLogUtils.d("collect value= $it")
            }
        }
        XLogUtils.d("run time= $time3")
    }

    /**
     *
     */
    private fun demo33() {
        lifecycleScope.launch {
            flowOf(1, 2, 3, 4, 5).onEach {
                delay(1000)
            }.collect {
                XLogUtils.d("collect value= $it")
            }
        }

        //上面flow会阻塞下面的，是同步执行的
        lifecycleScope.launch {
            flowOf(7, 8, 9, 10, 11).onEach {
                delay(1000)
            }.collect {
                XLogUtils.d("collect value= $it")
            }
        }
    }

    /**
     * 值得注意的地方，不要使用 withContext() 来切换 flow 的线程。
     */
    private suspend fun demo4() {
        flow { emit(1) }
            .onEach {
                delay(1000)
            }
            .map {
                XLogUtils.d("map1->${Thread.currentThread().name}")
            }
            .flowOn(Dispatchers.IO)//对map进行线程切换，在线程池中切换
            .map {
                XLogUtils.e("map2->${Thread.currentThread().name}")
            }
            .flowOn(Dispatchers.Main)
            .collect {
                //collect 执行的线程取决于 整个方法所在的线程
                XLogUtils.d("${Thread.currentThread().name}: $it")
            }
    }

    private var cancelJob: Job? = null

    /**
     * flow在挂起函数内是可以被中断的，不在挂起函数内是不能中断的
     */
    private suspend fun demo5() {
        //第二次点击取消协程
        if (cancelJob != null) {
            cancelJob?.cancel()
            return
        }
        cancelJob = lifecycleScope.launch {
            (0..100).asFlow().onEach {
                delay(1000)
            }.collect {
                XLogUtils.d("collect value= $it")
            }
        }
    }


    /**
     * 看onCompletion源码可知 内部是通过 try finally实现的，
     * 不过正常还是异常结束都会走onCompletion
     */
    private suspend fun demo6() {
        //onCompletion不管是否有异常都会走
        //可以借助扩展函数来实现只有成功才会走
        flow {
            emit(1)
            delay(1000)
            emit(2)
            throw RuntimeException("发生异常")
        }
//            .onCompleted { XLogUtils.i("执行完毕") }
            .onCompletion { XLogUtils.i("执行完毕") }
            .catch { XLogUtils.e("异常= ${it.printStackTrace()}") }
            .collect {
                XLogUtils.d("collect value= $it")
            }
    }

    private var retryCount = 0

    /**
     * 重试机制
     */
    private suspend fun demo7() {
        (1..5).asFlow().onEach {
            if (it == 3 && retryCount == 0) throw RuntimeException("出错啦")
        }.retry(2) {//重试两次都失败的情况 会抛出异常
            retryCount++;
            if (it is RuntimeException) {
                return@retry true
            }
            return@retry false
        }
            .onEach { XLogUtils.d("数据 $it") }
            .catch { it.printStackTrace() }
            .collect()
    }

    private var demo8IsStart = false

    private suspend fun demo8() {
        if (!demo8IsStart) {
            lifecycleScope.launchWhenResumed {
                viewModel.mEffect.collect {
                    when (it) {
                        is KotlinFlowViewModel.Effect.ShowToast -> {
                            XLogUtils.d(it.text)
//                            Toast.makeText(this@KotlinFlowActivity, it.text,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            demo8IsStart = true
            XLogUtils.d("观察成功")
        } else {
            (1..50).forEach {
                delay(1000)
                viewModel.showToast("$it")
            }
        }
    }

    inner class MAdapter(mRecyclerView: RecyclerView) :
        XRecyclerViewAdapter<CommonMenuBean>(mRecyclerView, R.layout.item_main) {
        override fun bindData(holder: XViewHolder, data: CommonMenuBean, position: Int) {
            val textView = holder.itemView as TextView
            textView.text = data.name
        }
    }


    /**
     * 只有在成功的时候才会执行onCompleted
     */
    fun <T> Flow<T>.onCompleted(action: () -> Unit) = flow {
        collect { value -> emit(value) }
        action()
    }
}


