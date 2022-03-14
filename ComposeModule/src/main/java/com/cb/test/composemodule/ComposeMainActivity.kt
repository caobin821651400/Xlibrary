package com.cb.test.composemodule

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import cn.sccl.xlibrary.adapter.XViewHolder
import kotlinx.android.synthetic.main.activity_compose_main.*

/**
 * @author bin
 * @desc 首页
 * @time 2019年7月19日17:04:09
 */
class ComposeMainActivity : ComposeBaseActivity() {

    private val mList: ArrayList<MenuBean> = ArrayList()
    lateinit var mAdapter: MAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_compose_main
    }

    override fun initUI() {
        setHeaderTitle("compose")
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MAdapter(mRecyclerView)
        mRecyclerView.adapter = mAdapter


        mList.add(MenuBean("RxJava使用", null))
        mAdapter.dataLists = mList
    }


    override fun initEvent() {
        mAdapter.setOnItemClickListener { v: View?, position: Int ->
            val bean = mList[position]
            bean.clz?.let {
                launchActivity(it, null)
            } ?: toast("暂未开放")
        }
    }

    /****
     *
     */
    inner class MAdapter(mRecyclerView: RecyclerView) :
        XRecyclerViewAdapter<MenuBean>(mRecyclerView, R.layout.item_compose_main) {

        protected override fun bindData(holder: XViewHolder, data: MenuBean, position: Int) {
            val textView = holder.itemView as TextView
            textView.text = data.name
        }
    }


    data class MenuBean(val name: String, val clz: Class<*>?)

}