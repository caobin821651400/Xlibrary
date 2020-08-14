package cn.sccl.xlibrary.adapter;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/5 11:23
 * @Desc :自己封装的BaseAdapter和刷新控件的用法
 * ====================================================
 */
public class AdapterDemo {
//    mRecyclerView.layoutManager = LinearLayoutManager(this)
//    mRecyclerView.isNestedScrollingEnabled = false
//    mAdapter = MAdapter(mRecyclerView)
//    mRecyclerView.adapter = mAdapter
//
//    val mList = ArrayList<String>()
//        for (i in 0..20) {
//        mList.add("第${i}条数据")
//    }
//    mAdapter.dataLists = mList
//        mAdapter.isLoadMore(true)
//                mAdapter.setOnLoadMoreListener(object : XRecyclerViewAdapter.OnLoadMoreListener {
//        override fun onRetry() {
//        }
//
//        override fun onLoadMore() {
//            Handler().postDelayed({
//            for (i in 0..15) {
//                mList.add("第${20 + i}条数据")
//            }
//            mAdapter.dataLists = mList
//            if (mList.size >= 80) {
//                mAdapter.isLoadMore(false)
//                mAdapter.showLoadComplete()
//            }
//                }, 1000)
//        }
//    })
//
//    //
//    mRefreshLayout.setOnRefreshListener {
//        mList.clear()
////            for (i in 0..20) {
////                mList.add("第${i}条数据")
////            }
//        mAdapter.dataLists = mList
//        mRefreshLayout.finishRefresh()
//    }
//
//
//    /**
//     *
//     */
//    class MAdapter(rv: RecyclerView) : XRecyclerViewAdapter<String>(rv, R.layout.item_list_test) {
//
//        override fun bindData(holder: XViewHolder, data: String, position: Int) {
//            (holder.convertView as TextView).text = data
//
//        }
//    }
}
