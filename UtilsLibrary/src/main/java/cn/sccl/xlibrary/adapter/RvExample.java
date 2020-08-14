package cn.sccl.xlibrary.adapter;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/30 15:55
 * @Desc :使用示例
 * ====================================================
 */
public class RvExample {
//    private lateinit var mAdapter: MAdapter
//    private val mList = ArrayList<String>()
//
//    override fun createPresenter(): Main2Presenter {
//        return Main2Presenter(this)
//    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.activity_test
//    }
//
//    override fun initUI() {
//        for (i in 0..20) {
//            mList.add("第${i}条数据")
//        }
//
//        mRecyclerView.layoutManager = LinearLayoutManager(this)
//        mRecyclerView.isNestedScrollingEnabled = false
//        mAdapter = MAdapter(mRecyclerView)
//        mRecyclerView.adapter = mAdapter
//        mAdapter.dataLists = mList
//        mAdapter.isLoadMore(true)
//    }
//
//    override fun initEvent() {
//        mAdapter.setOnLoadMoreListener { mPresenter.fetchData() }
//        mAdapter.setOnLoadMoreErrorListener { mPresenter.fetchData() }
//        //
//        mAdapter.setOnItemClickListener { v, positon -> toast("$positon") }
//        //
//        mRefreshLayout.setOnRefreshListener {
//            mList.clear()
//            for (i in 0..20) {
//                mList.add("第${i}条数据")
//            }
//            mAdapter.dataLists = mList
//            mRefreshLayout.finishRefresh()
//        }
//    }
//
//
//    override fun onSuccess(list: List<String>) {
//        mList.addAll(list)
//        mAdapter.dataLists = mList
//        if (mList.size >= 80) {
//            mAdapter.isLoadMore(false)
////            mAdapter.showLoadError()//加载更多时出错调用
//            mAdapter.showLoadComplete()
//        }
//    }
//
//    override fun onError() {
//    }
//
//    class MAdapter(rv: RecyclerView) : XRecyclerViewAdapter<String>(rv, R.layout.item_list_test) {
//        override fun bindData(holder: XViewHolder, data: String, position: Int) {
//            (holder.convertView as TextView).text = data
//        }
//    }
}
