package com.example.cb.test.view

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import cn.sccl.xlibrary.adapter.XViewHolder
import com.example.cb.test.R
import com.example.cb.test.bean.CommonMenuBean

/**
 * @author: bincao2
 * @date: 2021/12/15 14:12
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/12/15 14:12
 * @updateRemark: 更新说明
 */
 class CommonAdapter (mRecyclerView: RecyclerView) :
    XRecyclerViewAdapter<CommonMenuBean>(mRecyclerView, R.layout.item_main) {
    override fun bindData(holder: XViewHolder, data: CommonMenuBean, position: Int) {
        val textView = holder.itemView as TextView
        textView.text = data.name
    }
}