package com.example.cb.test.jetpack

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cb.xlibrary.adapter.XRecyclerViewAdapter
import cb.xlibrary.adapter.XViewHolder
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.CommonMenuBean
import com.example.cb.test.jetpack.hilt.HiltMainActivity
import com.example.cb.test.jetpack.lifecycles.LifeCyclesActivity
import com.example.cb.test.jetpack.livedata.LiveDataActivity
import com.example.cb.test.jetpack.navigation.NavigationActivity
import com.example.cb.test.jetpack.room.RoomActivity
import com.example.cb.test.jetpack.viewmodule.ViewModuleActivity
import com.example.cb.test.jetpack.workmanager.WorkManagerActivity
import kotlinx.android.synthetic.main.activity_jet_pack.*
import java.util.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 10:36
 * @Desc :JetPack
 * ====================================================
 */
class JetPackActivity : BaseActivity() {

    private lateinit var mAdapter: MAdapter
    private var mList: ArrayList<CommonMenuBean> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.activity_jet_pack
    }

    override fun initUI() {
        setHeaderTitle("JetPack使用")
        mAdapter = MAdapter(mRecyclerView)
        mRecyclerView.adapter = mAdapter

        mList.add(CommonMenuBean("LifeCycles", LifeCyclesActivity::class.java))
        mList.add(CommonMenuBean("LiveData", LiveDataActivity::class.java))
        mList.add(CommonMenuBean("ViewModule", ViewModuleActivity::class.java))
        mList.add(CommonMenuBean("Room", RoomActivity::class.java))
        mList.add(CommonMenuBean("Navigation", NavigationActivity::class.java))
        mList.add(CommonMenuBean("WorkManager", WorkManagerActivity::class.java))
        mList.add(CommonMenuBean("Hilt使用", HiltMainActivity::class.java))

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
    inner class MAdapter(mRecyclerView: RecyclerView) : XRecyclerViewAdapter<CommonMenuBean>(mRecyclerView, R.layout.item_main) {
        override fun bindData(holder: XViewHolder, data: CommonMenuBean, position: Int) {
            val textView = holder.itemView as TextView
            textView.text = data.name
        }
    }
}
