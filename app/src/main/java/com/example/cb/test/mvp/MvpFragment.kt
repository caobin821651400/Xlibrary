package com.example.cb.test.mvp

import android.view.View
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseMvpFragment
import com.example.cb.test.mvp.presenter.MvpFragmentPresenter
import com.example.cb.test.mvp.view.IMvpFragmentView
import com.example.cb.test.rx.http.NewsResp
import kotlinx.android.synthetic.main.fragment_mvp.*

class MvpFragment : BaseMvpFragment<MvpFragmentPresenter>(), IMvpFragmentView {
    override fun initUI(v: View?) {
        mPresenter?.fetchData()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mvp
    }

    override fun initEvent(view: View?) {
    }


    override fun createPresenter(): MvpFragmentPresenter {
        return MvpFragmentPresenter(this)
    }


    override fun onNewsError(error: String) {
        XLogUtils.e(error)
    }

    override fun onNewsSuccess(newsResp: NewsResp) {
        XLogUtils.d(newsResp.result.data[0].title)
        aa.text = newsResp.result.data[0].title
    }
}