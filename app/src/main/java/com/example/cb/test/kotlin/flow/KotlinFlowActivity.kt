package com.example.cb.test.kotlin.flow

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import cn.sccl.xlibrary.adapter.XViewHolder
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.CommonMenuBean
import kotlinx.android.synthetic.main.activity_kotlin_flow.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.system.measureTimeMillis

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/10/30 10:14
 * @Desc :
 * ====================================================
 */
class KotlinFlowActivity : BaseActivity(), CoroutineScope by MainScope() {

    private lateinit var mAdapter: MAdapter
    private var mList: ArrayList<CommonMenuBean> = ArrayList()

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
            }
        }
    }

    private suspend fun demo1() {
        flow {
            (1..5).forEach {
                delay(1000)
                emit(it)
            }
        }.collect {
            XLogUtils.d("collect value= $it")
        }
    }

    private suspend fun demo2() {
        flowOf(1, 2, 3, 4, 5).onEach { delay(1000) }.collect { XLogUtils.d("collect value= $it") }
        //上面代码会阻塞下面的，是同步执行的
        listOf(7, 8, 9, 10, 11).asFlow().onEach { delay(1000) }.collect { XLogUtils.d("collect value= $it") }
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
     * 值得注意的地方，不要使用 withContext() 来切换 flow 的线程。
     */
    private suspend fun demo4() {
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9).asFlow()
                .onEach {
                    delay(1000)
                }
                .map {
                    XLogUtils.d("map->${Thread.currentThread().name}")
                    it * it
                }
                .flowOn(Dispatchers.IO)//对map进行线程切换，在线程池中切换
                .collect {
                    //collect 执行的线程取决于 整个方法所在的线程
                    XLogUtils.d("${Thread.currentThread().name}: $it")
                }
    }

    /**
     * flow在挂起函数内是可以被中断的，不在挂起函数内是不能中断的
     */
    private suspend fun demo5() {
        //超时可以取消协程,返回null
        //withTimeout 则会抛出异常
        val result = withTimeoutOrNull(3000) {
            listOf(1, 2, 3, 4).asFlow().onEach { delay(1000) }.collect { XLogUtils.d("collect value= $it") }
        }
        XLogUtils.e("执行结果= $result")
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
                .onCompleted { XLogUtils.i("执行完毕") }
//                .onCompletion { XLogUtils.i("执行完毕") }
                .catch { XLogUtils.e("异常= ${it.printStackTrace()}") }
                .collect {
                    XLogUtils.d("collect value= $it")
                }
    }

    inner class MAdapter(mRecyclerView: RecyclerView) : XRecyclerViewAdapter<CommonMenuBean>(mRecyclerView, R.layout.item_main) {
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


