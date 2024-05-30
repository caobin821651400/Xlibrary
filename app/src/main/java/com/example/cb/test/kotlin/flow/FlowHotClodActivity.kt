package com.example.cb.test.kotlin.flow

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.CommonMenuBean
import com.example.cb.test.view.CommonAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * @author: bincao2
 * @date: 2021/12/13 15:32
 * @desc: flow冷流热流
 * @updateUser: 更新者
 * @updateDate: 2021/12/13 15:32
 * @updateRemark: 更新说明
 */
class FlowHotClodActivity : BaseActivity() {

    private val mRecyclerView by lazyNone { findViewById<RecyclerView>(R.id.mRecyclerView) }

    companion object {
        const val TAG = "FlowHotClodActivityTAG"
    }

    private lateinit var mAdapter: CommonAdapter
    private var mList: ArrayList<CommonMenuBean> = ArrayList()

    private val viewModel by viewModels<FlowHotClodViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_flow_hot_clod

    override fun initUI() {
        setHeaderTitle("冷流热流")
        mAdapter = CommonAdapter(mRecyclerView)
        mRecyclerView.adapter = mAdapter

        mList.add(CommonMenuBean("冷流", null))
        mList.add(CommonMenuBean("StateFlow", null))
        mList.add(CommonMenuBean("SharedFlow", null))
        mList.add(CommonMenuBean("liveData防抖", null))
        mList.add(CommonMenuBean("SharedFlow多次订阅", null))
        mList.add(CommonMenuBean("生产>消费", null))

        mAdapter.dataLists = mList


        stateFlowTest()
        sharedFlowTest()

        //distinctUntilChanged防抖 当前值和上一次值相同不会在发送
        viewModel.liveData.distinctUntilChanged().observe(this, Observer {
            XLogUtils.d("$TAG distinctUntilChanged= $it")
        })
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { _, position ->
            when (position) {
                0 -> {
                    clodFlow()
                }

                1 -> go {
                    viewModel.uiNavigation.emit(
                        FlowHotClodViewModel.NavigationState.EmptyNavigation(
                            "222"
                        )
                    )
                    viewModel.uiNavigation.emit(
                        FlowHotClodViewModel.NavigationState.ViewNavigation(
                            "111"
                        )
                    )
                }

                2 -> go {
                    viewModel.intentEvent.emit(FlowHotClodViewModel.NavigationTarget.Go2Main("Go2Main"))
                    viewModel.intentEvent.emit(FlowHotClodViewModel.NavigationTarget.Go2User("Go2User"))
                    viewModel.intentEvent.emit(FlowHotClodViewModel.NavigationTarget.Go2Main("Go2Main"))
                    viewModel.intentEvent.emit(FlowHotClodViewModel.NavigationTarget.Go2User("Go2User"))
                    viewModel.intentEvent.emit(FlowHotClodViewModel.NavigationTarget.Go2Main("Go2Main"))
                    viewModel.intentEvent.emit(FlowHotClodViewModel.NavigationTarget.Go2User("Go2User"))
                    viewModel.intentEvent.emit(FlowHotClodViewModel.NavigationTarget.Go2Main("Go2Main"))
                    viewModel.intentEvent.emit(FlowHotClodViewModel.NavigationTarget.Go2User("Go2User"))
                }

                3 -> go {
                    viewModel.liveData.value = "1"
                    viewModel.liveData.value = "1"
                    viewModel.liveData.value = "2"
                    viewModel.liveData.value = "1"
                    viewModel.liveData.value = "2"
                    viewModel.liveData.value = "2"
                    viewModel.liveData.value = "3"
                }

                4 -> dome1()
                5 -> dome2()
            }
        }
    }

    private fun dome1() {
        val flow = MutableSharedFlow<String>(replay = 1)
        lifecycleScope.launch {
            //第一个订阅者
            launch {
                flow.collect {
                    XLogUtils.d("$TAG collect1 $it")
                }
            }
            //生产数据
            launch {
                (1..10).forEach {
                    flow.emit("第${it}个")
                }
            }
        }
        //模拟在生产者产生数据后才订阅
        lifecycleScope.launch {
            delay(3000)
            //第二个订阅者
            flow.collect {
                XLogUtils.d("$TAG collect2 $it")
            }
        }
    }


    private fun dome2() {
        val flow = MutableSharedFlow<String>(replay = 2, extraBufferCapacity = 1)
        lifecycleScope.launch {
            //第一个订阅者
            launch {
                flow.collect {
                    delay(2000)
                    XLogUtils.d("$TAG 消费 $it")
                }
            }
            //生产数据
            launch {
                (1..10).forEach {
                    val v = "第${it}个"
                    XLogUtils.e("$TAG 生产 $v")
                    flow.emit(v)
                    delay(100)
                }
            }
        }
    }


    private fun clodFlow() {
        lifecycleScope.launchWhenStarted {
            val flow = flow<String> {
                val data = viewModel.getData(1)
                emit(data)
            }
            //冷流指的是  有消费者消费的时候才会生产数据
            //并且 emit必须要在flow生产
            flow.collect {
                XLogUtils.d("flow.collect= $it")
            }
        }
    }

    private fun stateFlowTest() {
        lifecycleScope.launchWhenStarted {
            //StateFlow是热流 也就是说 emit方法可以在流之外产生
            //当旋转屏幕后 会在次再次打印上一次数据跟LiveData类似的[粘性事件]
            viewModel.uiNavigation.onEach {
                //有点像rxjava的map
                it.state = "${it.state} onEach"
            }
                .collect {
                    when (it) {
                        is FlowHotClodViewModel.NavigationState.EmptyNavigation -> {
                            XLogUtils.d("$TAG EmptyNavigation ${it.state}")
                        }

                        is FlowHotClodViewModel.NavigationState.ViewNavigation -> {
                            XLogUtils.d("$TAG ViewNavigation  ${it.state}")
                        }
                    }
                }
        }
    }

    private fun sharedFlowTest() {
        lifecycleScope.launchWhenStarted {
            viewModel.intentEvent.collect {
                when (it) {
                    is FlowHotClodViewModel.NavigationTarget.Go2Main -> {
                        XLogUtils.d("$TAG Go2Main  ${it.value}")
                    }

                    is FlowHotClodViewModel.NavigationTarget.Go2User -> {
                        XLogUtils.d("$TAG Go2User  ${it.value}")
                    }
                }
            }
        }
    }


    private inline fun go(crossinline black: suspend () -> Unit) {
        this.lifecycleScope.launchWhenStarted {
            black()
        }
    }

}