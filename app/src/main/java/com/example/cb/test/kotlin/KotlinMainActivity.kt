package com.example.cb.test.kotlin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import cn.sccl.xlibrary.adapter.XViewHolder
import cn.sccl.xlibrary.kotlin.lazyNone
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.CommonMenuBean
import com.example.cb.test.jetpack.paging.PagingActivity
import com.example.cb.test.kotlin.coroutines.CoroutinesMainActivity
import com.example.cb.test.kotlin.flow.FlowHotClodActivity
import com.example.cb.test.kotlin.flow.KotlinFlowActivity
import com.example.cb.test.kotlin.function.FunctionInlineActivity
import java.util.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 11:26
 * @Desc :Kotlin
 * ====================================================
 */
class KotlinMainActivity : BaseActivity() {

    private val mRecyclerView: RecyclerView by lazyNone { findViewById(R.id.mRecyclerView) }
    private lateinit var mAdapter: MAdapter
    private var mList: ArrayList<CommonMenuBean> = ArrayList()

    override fun getLayoutId() = R.layout.activity_kotlin_main

    override fun initUI() {
        setHeaderTitle("Kotlin使用")
        mAdapter = MAdapter(mRecyclerView)
        mRecyclerView.adapter = mAdapter

        mList.add(CommonMenuBean("Kotlin集合", KotlinSetActivity::class.java))
        mList.add(CommonMenuBean("Kotlin测试", KotlinTestActivity::class.java))
        mList.add(CommonMenuBean("协程", CoroutinesMainActivity::class.java))
        mList.add(CommonMenuBean("Flow", KotlinFlowActivity::class.java))
        mList.add(CommonMenuBean("函数相关", FunctionInlineActivity::class.java))
        mList.add(CommonMenuBean("Paging相关", PagingActivity::class.java))
        mList.add(CommonMenuBean("冷热流", FlowHotClodActivity::class.java))


        mAdapter.dataLists = mList
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { v: View?, position: Int ->
            val bean: CommonMenuBean = mList.get(position)
            if (bean.getaClass() != null) {
                launchActivity(bean.getaClass(), null)
            }
        }
    }

    /****
     *
     */
    inner class MAdapter(mRecyclerView: RecyclerView) :
        XRecyclerViewAdapter<CommonMenuBean>(mRecyclerView, R.layout.item_main) {
        override fun bindData(holder: XViewHolder, data: CommonMenuBean, position: Int) {
            val textView = holder.itemView as TextView
            textView.text = data.name
        }
    }
}
