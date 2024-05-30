package com.example.cb.test.jetpack.paging

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseViewModuleActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PagingActivity : BaseViewModuleActivity<PagingViewModel>() {

    private val mRecyclerView by lazyNone { findViewById<RecyclerView>(R.id.mRecyclerView) }
    private val mAdapter = PagingAdapter()
//    private lateinit var loadsir: LoadService<Any>

    override fun createViewModel(): PagingViewModel = viewModels<PagingViewModel>().value

    override fun getLayoutId(): Int {
        return R.layout.activity_paging
    }

    override fun initEvent() {
    }

    override fun initUI() {
        setHeaderTitle("paging")
//        loadsir = loadServiceInit(mRecyclerView) {
//            //点击重试时触发的操作
//            loadsir.showLoading()
//            mAdapter.retry()
//        }

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter.withLoadStateFooter(PagingFooterAdapter {
            mAdapter.retry()
        })

        lifecycleScope.launch {
            mViewModule.getPagingData()
                .collect { pagingData ->
                    mAdapter.submitData(pagingData)
                }
        }
        mAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    XLogUtils.d("NotLoading")
//                    loadsir.showSuccess()
                }

                is LoadState.Error -> XLogUtils.e("Error")
                is LoadState.Loading -> {
                    XLogUtils.e("Loading")
//                    loadsir.showLoading()
                }
            }
        }
    }
}
