package com.example.cb.test.kotlin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cb.xlibrary.adapter.XRecyclerViewAdapter
import cb.xlibrary.adapter.XViewHolder
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.CommonMenuBean
import com.example.cb.test.jetpack.lifecycles.LifeCyclesActivity
import com.example.cb.test.jetpack.livedata.LiveDataActivity
import com.example.cb.test.jetpack.navigation.NavigationActivity
import com.example.cb.test.jetpack.room.RoomActivity
import com.example.cb.test.jetpack.viewmodule.ViewModuleActivity
import com.example.cb.test.jetpack.workmanager.WorkManagerActivity
import com.example.cb.test.kotlin.coroutines.CoroutinesMainActivity
import kotlinx.android.synthetic.main.activity_jet_pack.*
import java.util.ArrayList
/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 11:26
 * @Desc :Kotlin
 * ====================================================
 */
class KotlinMainActivity : BaseActivity() {

    private lateinit var mAdapter: MAdapter
    private var mList: ArrayList<CommonMenuBean> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin_main
    }

    override fun initUI() {
        setHeaderTitle("Kotlin使用")
        mAdapter = MAdapter(mRecyclerView)
        mRecyclerView.adapter = mAdapter

        mList.add(CommonMenuBean("Kotlin集合", KotlinSetActivity::class.java))
        mList.add(CommonMenuBean("Kotlin测试", KotlinTestActivity::class.java))
        mList.add(CommonMenuBean("协程", CoroutinesMainActivity::class.java))

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
