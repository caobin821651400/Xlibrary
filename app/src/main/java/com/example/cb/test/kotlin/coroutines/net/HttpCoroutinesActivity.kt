package com.example.cb.test.kotlin.coroutines.net

import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import cn.sccl.xlibrary.adapter.XViewHolder
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.widget.XSwipeRefreshLayout
import com.example.cb.test.R
import com.example.cb.test.base.BaseViewModuleActivity
import com.example.cb.test.kotlin.coroutines.loadListData
import com.example.cb.test.kotlin.coroutines.loadServiceInit
import com.example.cb.test.kotlin.coroutines.showLoading
import com.kingja.loadsir.core.LoadService

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/14 16:12
 * @Desc :协程结合ViewModule请求
 * ====================================================
 */
class HttpCoroutinesActivity : BaseViewModuleActivity<NetViewModule>() {

    private lateinit var mAdapter: Adapter
    private lateinit var loadsir: LoadService<Any>

    private val mRecyclerView by lazyNone { findViewById<RecyclerView>(R.id.mRecyclerView) }
    private val mRefreshLayout by lazyNone { findViewById<XSwipeRefreshLayout>(R.id.mRefreshLayout) }
    override fun getLayoutId() = R.layout.activity_http

    override fun createViewModel() = ViewModelProvider(this).get(NetViewModule::class.java)

    override fun initUI() {
        setHeaderTitle("协程请求")
        loadsir = loadServiceInit(mRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            mViewModule.getData(true)
        }

//        supportFragmentManager.beginTransaction().add(R.id.content, HttpCoroutinesFragment()).commit()

        mAdapter = Adapter(mRecyclerView)
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )
        mRecyclerView.adapter = mAdapter


        loadsir.showLoading()
        mViewModule.getData(true)


        mViewModule.dataModule.observe(this, Observer {
            loadListData(it, mAdapter, loadsir, mRefreshLayout)
        })
    }

    override fun initEvent() {
        mRefreshLayout.setOnRefreshListener {
            mViewModule.getData(true)
        }

        mAdapter.setOnLoadMoreListener {
            mViewModule.getData(false)
        }

        mAdapter.setOnLoadMoreErrorListener {
            mViewModule.getData(false)
        }
    }


    inner class Adapter(rv: RecyclerView) :
        XRecyclerViewAdapter<WanAndroidBean>(rv, R.layout.item_list_zns) {

        override fun bindData(holder: XViewHolder, data: WanAndroidBean, position: Int) {
            (holder.convertView as TextView).text = data.title
        }
    }
}