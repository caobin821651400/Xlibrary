package com.example.cb.test.mvp

import androidx.fragment.app.FragmentManager
import com.example.cb.test.R
import com.example.cb.test.base.BaseMvpActivity
import com.example.cb.test.mvp.presenter.NewListPresenter
import com.example.cb.test.mvp.view.INewListView
import com.example.cb.test.rx.http.NewsResp

/**
 * 练习MVP
 */
class MvpActivity : BaseMvpActivity<NewListPresenter>(), INewListView {


    private var fragmentManager: FragmentManager? = null
    private var mvpFragment: MvpFragment? = null


    override fun createPresenter(): NewListPresenter {
        return NewListPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_mvp
    }

    override fun initUI() {
        if (fragmentManager == null) {
            fragmentManager = supportFragmentManager
        }
        //创建mvpFragment对象
        mvpFragment = MvpFragment()

        val transaction = fragmentManager!!.beginTransaction()
        transaction.add(R.id.content, mvpFragment!!, "MvpFragment")
        transaction.commit()

        mPresenter!!.fetchData()
    }

    override fun initEvent() {
    }

    override fun onRequestSuccess(data: NewsResp) {
        toast(data.result.data[0].author_name)
    }

    override fun onError(error: String) {
        toast(error)
    }
}